package com.example.agrionion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private static final String API_KEY = "93fb25098cd2e3ce7f2b8e7229598ac6";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TAG = "HomeFragment";

    private TextView weatherDayCity, weatherTemp, weatherCondition, weatherWindSpeed, weatherRainChance;
    private ImageView weatherGif, notification;
    private RecyclerView forecastRecyclerView, transactionsRecyclerView;
    private ForecastAdapter forecastAdapter;
    private TransactionAdapter transactionAdapter;
    private List<ForecastModel> forecastList;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        executorService = Executors.newFixedThreadPool(2);
        mainHandler = new Handler(Looper.getMainLooper());

        setupUIElements(view);
        setupRecyclerViews(view);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        setupLocationCallback();
        requestAccurateLocation();

        return view;
    }

    private void setupUIElements(View view) {
        weatherGif = view.findViewById(R.id.weather_gif);
        weatherDayCity = view.findViewById(R.id.weather_day_city);
        weatherTemp = view.findViewById(R.id.weather_temp);
        weatherCondition = view.findViewById(R.id.weather_condition);
        weatherWindSpeed = view.findViewById(R.id.weather_wind_speed);
        weatherRainChance = view.findViewById(R.id.weather_rain_chance);
        notification = view.findViewById(R.id.notification);

        notification.setOnClickListener(v -> startActivity(new Intent(getActivity(), Notification.class)));
    }

    private void setupRecyclerViews(View view) {
        transactionsRecyclerView = view.findViewById(R.id.recent_transactions);
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        transactionAdapter = new TransactionAdapter(getSampleTransactions());
        transactionsRecyclerView.setAdapter(transactionAdapter);

        forecastRecyclerView = view.findViewById(R.id.forecastRecyclerView);
        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(forecastList);
        forecastRecyclerView.setAdapter(forecastAdapter);

        loadInitialForecastData();
    }

    private List<Transaction> getSampleTransactions() {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction("Eggplant", "March 18, 2025", "₱30,000", "Cristian"));
        list.add(new Transaction("Chili", "March 17, 2025", "₱5,000", "Nel"));
        list.add(new Transaction("String Beans", "March 16, 2025", "₱10,000", "Erwin"));
        list.add(new Transaction("Chinese Cabbage", "March 15, 2025", "₱40,000", "Harold"));
        return list;
    }

    private void loadInitialForecastData() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        for (int i = 0; i < 7; i++) {
            String dayName = dayFormat.format(calendar.getTime());
            forecastList.add(new ForecastModel(dayName, "25°C", "Sunny", R.drawable.clear_sky, "10% chance of rain"));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        forecastAdapter.notifyDataSetChanged();
    }

    private void setupLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    fetchWeatherData(location.getLatitude(), location.getLongitude());
                } else {
                    Log.w(TAG, "Location result was null");
                }
            }
        };
    }

    private void requestAccurateLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                .addOnFailureListener(e -> Log.e(TAG, "Failed to request location updates", e));

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        fetchWeatherData(location.getLatitude(), location.getLongitude());
                    } else {
                        Log.w(TAG, "Last known location unavailable");
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Failed to get last location", e));
    }

    private void fetchWeatherData(double latitude, double longitude) {
        executorService.execute(() -> {
            try {
                String currentWeatherUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric", latitude, longitude, API_KEY);
                String forecastUrl = String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s&units=metric", latitude, longitude, API_KEY);

                String currentWeatherResponse = fetchData(currentWeatherUrl);
                String forecastResponse = fetchData(forecastUrl);

                mainHandler.post(() -> {
                    updateCurrentWeatherUI(currentWeatherResponse, forecastResponse);
                    updateForecastUI(forecastResponse);
                });
            } catch (Exception e) {
                Log.e(TAG, "Error fetching weather data", e);
                mainHandler.post(this::showErrorState);
            }
        });
    }

    private String fetchData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP error code: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } finally {
            connection.disconnect();
        }
    }

    private void updateCurrentWeatherUI(String currentWeatherResult, String forecastResult) {
        if (!isAdded() || getActivity() == null) {
            Log.e("HomeFragment", "Fragment not attached, skipping UI update.");
            return;
        }

        if (isAdded() && weatherGif != null) {
            Glide.with(requireActivity())
                    .load(R.drawable.sunnyday)
                    .into(weatherGif);
        } else {
            Log.e("HomeFragment", "Fragment not attached or weatherImageView is null. Skipping UI update.");
        }
        try {
            JSONObject jsonObject = new JSONObject(currentWeatherResult);
            String city = jsonObject.getString("name");
            String weather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            double temp = jsonObject.getJSONObject("main").getDouble("temp");
            double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed") * 3.6;

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            String day = dayFormat.format(new Date());

            weatherDayCity.setText(String.format("%s - %s", day, city));
            weatherTemp.setText(String.format(Locale.getDefault(), "%.1f°C", temp));
            weatherCondition.setText(weather);
            weatherWindSpeed.setText(String.format(Locale.getDefault(), "Wind: %.1f km/h", windSpeed));

            int gifRes = getWeatherGifResource(weather.toLowerCase());
            Glide.with(this).load(gifRes).into(weatherGif);

            JSONObject forecastJson = new JSONObject(forecastResult);
            JSONArray forecastArray = forecastJson.getJSONArray("list");

            if (forecastArray.length() > 0) {
                JSONObject firstForecast = forecastArray.getJSONObject(0);
                double pop = firstForecast.optDouble("pop", 0.0) * 100;
                String rainChance = String.format(Locale.getDefault(), "%.0f%% chance of rain", pop);
                weatherRainChance.setText(rainChance);
            } else {
                weatherRainChance.setText("N/A");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating current weather UI", e);
            showErrorState();
        }
    }

    private void showErrorState() {
        weatherDayCity.setText("Unable to load weather");
        weatherTemp.setText("--°C");
        weatherCondition.setText("N/A");
        weatherWindSpeed.setText("Wind: N/A");
        weatherRainChance.setText("N/A");
        Glide.with(this).load(R.drawable.sunnyday).into(weatherGif);
        forecastList.clear();
        forecastAdapter.notifyDataSetChanged();
    }

    private int getWeatherGifResource(String condition) {
        if (condition.contains("sunny") || condition.contains("clear")) return R.drawable.sunnyday;
        if (condition.contains("cloud")) return R.drawable.cloudy;
        if (condition.contains("rain")) return R.drawable.raining;
        if (condition.contains("haze")) return R.drawable.haze;
        return R.drawable.sunnyday;
    }

    private void updateForecastUI(String response) {
        // Truncated - Can be added if needed
    }
}
