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
import androidx.recyclerview.widget.GridLayoutManager;
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

    // UI Elements
    private TextView weatherCity, weatherDay, weatherTemp, weatherCondition, weatherWindSpeed;
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

        // Initialize thread pool and handler
        executorService = Executors.newFixedThreadPool(2);
        mainHandler = new Handler(Looper.getMainLooper());

        // Setup UI elements
        setupUIElements(view);

        // Setup RecyclerViews
        setupRecyclerViews(view);

        // Setup location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        setupLocationCallback();

        // Request location updates
        requestAccurateLocation();

        return view;
    }

    private void setupUIElements(View view) {
        weatherGif = view.findViewById(R.id.weather_gif);
        weatherCity = view.findViewById(R.id.weather_city);
        weatherDay = view.findViewById(R.id.weather_day);
        weatherTemp = view.findViewById(R.id.weather_temp);
        weatherCondition = view.findViewById(R.id.weather_condition);
        weatherWindSpeed = view.findViewById(R.id.weather_wind_speed);

        notification = view.findViewById(R.id.notification);
        notification.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), Notification.class)));
    }

    private void setupRecyclerViews(View view) {
        // Transactions RecyclerView
        transactionsRecyclerView = view.findViewById(R.id.recent_transactions);
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Transaction> transactionList = getSampleTransactions();
        transactionAdapter = new TransactionAdapter(transactionList);
        transactionsRecyclerView.setAdapter(transactionAdapter);

        // Forecast RecyclerView
        forecastRecyclerView = view.findViewById(R.id.forecastRecyclerView);
        forecastRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(forecastList);
        forecastRecyclerView.setAdapter(forecastAdapter);

        // Load initial dummy data
        loadInitialForecastData();
    }

    private List<Transaction> getSampleTransactions() {
        List<Transaction> list = new ArrayList<>(4);
        list.add(new Transaction("Eggplant", "March 18, 2025", "₱30,000", "Cristian"));
        list.add(new Transaction("Chili", "March 17, 2025", "₱5,000", "Nel"));
        list.add(new Transaction("String Beans", "March 16, 2025", "₱10,000", "Erwin"));
        list.add(new Transaction("Chinese Cabbage", "March 15, 2025", "₱40,000", "Harold"));
        return list;
    }

    private void loadInitialForecastData() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());

        for (int i = 1; i <= 6; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String dayName = dayFormat.format(calendar.getTime());
            forecastList.add(new ForecastModel(dayName, "25°C", "Sunny", R.drawable.clear_sky));
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
                }
            }
        };
    }

    private void requestAccurateLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        fetchWeatherData(location.getLatitude(), location.getLongitude());
                    }
                });
    }

    private void fetchWeatherData(double latitude, double longitude) {
        executorService.execute(() -> {
            try {
                String currentWeatherUrl = String.format(
                        "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric",
                        latitude, longitude, API_KEY);
                String forecastUrl = String.format(
                        "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s&units=metric",
                        latitude, longitude, API_KEY);

                String currentWeatherResponse = fetchData(currentWeatherUrl);
                String forecastResponse = fetchData(forecastUrl);

                mainHandler.post(() -> {
                    updateUI(currentWeatherResponse);
                    updateForecastUI(forecastResponse);
                });
            } catch (Exception e) {
                Log.e("WeatherFetch", "Error fetching weather data", e);
            }
        });
    }

    private String fetchData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

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

    private void updateUI(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String city = jsonObject.getString("name");
            String weather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            double temp = jsonObject.getJSONObject("main").getDouble("temp");
            double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");

            weatherCity.setText(city);
            weatherDay.setText("Today");
            weatherTemp.setText(String.format(Locale.getDefault(), "%.1f°C", temp));
            weatherCondition.setText(weather);
            weatherWindSpeed.setText(String.format(Locale.getDefault(), "Wind: %.1f km/h", windSpeed));

            int gifRes = getWeatherGifResource(weather.toLowerCase());
            Glide.with(this).load(gifRes).into(weatherGif);
        } catch (Exception e) {
            Log.e("WeatherUpdate", "Error updating UI", e);
        }
    }

    private int getWeatherGifResource(String condition) {
        if (condition.contains("sunny")) return R.drawable.sunnyday;
        if (condition.contains("cloud")) return R.drawable.cloudy;
        if (condition.contains("rain")) return R.drawable.raining;
        if (condition.contains("haze")) return R.drawable.haze;
        if (condition.contains("clear")) return R.drawable.clear;
        return R.drawable.sunnyday;
    }

    private void updateForecastUI(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray forecastArray = jsonObject.getJSONArray("list");
            forecastList.clear();

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("E", Locale.getDefault());
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            List<ForecastModel> tempForecastList = new ArrayList<>();
            for (int i = 0; i < forecastArray.length(); i++) {
                JSONObject dayData = forecastArray.getJSONObject(i);
                String dateTimeString = dayData.getString("dt_txt");
                Date date = inputFormat.parse(dateTimeString);
                Calendar forecastDate = Calendar.getInstance();
                forecastDate.setTime(date);

                // Skip if it's today
                if (forecastDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        forecastDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                    continue;
                }

                // Add the first forecast of each day
                Calendar dayStart = (Calendar) forecastDate.clone();
                dayStart.set(Calendar.HOUR_OF_DAY, 0);
                dayStart.set(Calendar.MINUTE, 0);
                dayStart.set(Calendar.SECOND, 0);
                dayStart.set(Calendar.MILLISECOND, 0);

                boolean dayAlreadyAdded = tempForecastList.stream().anyMatch(f ->
                        f.getDay().equals(outputFormat.format(date)));

                if (!dayAlreadyAdded) {
                    String dayName = outputFormat.format(date);
                    double temp = dayData.getJSONObject("main").getDouble("temp");
                    String condition = dayData.getJSONArray("weather").getJSONObject(0).getString("main");

                    int iconRes = getForecastIconResource(condition);
                    tempForecastList.add(new ForecastModel(dayName, String.format("%.1f°C", temp), condition, iconRes));
                }

                if (tempForecastList.size() >= 6) break;
            }

            forecastList.addAll(tempForecastList);
            Log.d("ForecastUpdate", "Days added: " + forecastList.size());
            if (forecastList.size() < 6) {
                Log.w("ForecastUpdate", "API provided only " + forecastList.size() + " days. Expected 6.");
            }
            forecastAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("ForecastUpdate", "Error updating forecast", e);
        }
    }

    private int getForecastIconResource(String condition) {
        switch (condition) {
            case "Clear": return R.drawable.clear_sky;
            case "Clouds": return R.drawable.scattered_clouds;
            case "Rain": return R.drawable.rain;
            case "Thunderstorm": return R.drawable.thurderstorm;
            case "Snow": return R.drawable.snow;
            default: return R.drawable.clear_sky;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fusedLocationClient.removeLocationUpdates(locationCallback);
        executorService.shutdown();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestAccurateLocation();
        }
    }
}