package com.example.agrionion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.ImageView;

public class HomeFragment extends Fragment {

    private TextView weatherCity, weatherDay, weatherTemp, weatherCondition, weatherWindSpeed;
    private ImageView weatherGif, notification;
    private RecyclerView forecastRecyclerView;
    private ForecastAdapter forecastAdapter;
    private List<ForecastModel> forecastList;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private static final String API_KEY = "93fb25098cd2e3ce7f2b8e7229598ac6"; // Replace with your actual API Key

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);


        // Initialize RecyclerView for transactions
        RecyclerView recyclerView = view.findViewById(R.id.recent_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

// Sample transaction data
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction("Eggplant", "March 18, 2025", "₱30,000","Cristian"));
        transactionList.add(new Transaction("Chili", "March 17, 2025", "₱5,000","Nel"));
        transactionList.add(new Transaction("String Beans", "March 16, 2025", "₱10,000","Erwin"));
        transactionList.add(new Transaction("Chinese Cabbage", "March 15, 2025", "₱40,000","Harold"));

// Set adapter
        TransactionAdapter adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);


        //notification
        notification = view.findViewById(R.id.notification);

        // Set click listener
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notification.class);
                startActivity(intent);
            }
        });



        // Initialize UI Elements
        weatherGif = view.findViewById(R.id.weather_gif);
        weatherCity = view.findViewById(R.id.weather_city);
        weatherDay = view.findViewById(R.id.weather_day);
        weatherTemp = view.findViewById(R.id.weather_temp);
        weatherCondition = view.findViewById(R.id.weather_condition);
        weatherWindSpeed = view.findViewById(R.id.weather_wind_speed);
        forecastRecyclerView = view.findViewById(R.id.forecastRecyclerView);

        // Set up RecyclerView
        // Set up RecyclerView with GridLayoutManager (2 items per row)
        forecastRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(forecastList);
        forecastRecyclerView.setAdapter(forecastAdapter);


        // Dummy Data for RecyclerView
        forecastList.add(new ForecastModel("Mon", "25°C", "Sunny",R.drawable.clear_sky));
        forecastList.add(new ForecastModel("Tue", "22°C", "Cloudy",R.drawable.scattered_clouds));
        forecastList.add(new ForecastModel("Wed", "20°C", "shower rain",R.drawable.shower_rain));
        forecastList.add(new ForecastModel("Thu", "20°C", "thunderstorm",R.drawable.thurderstorm));
        forecastList.add(new ForecastModel("Fri", "20°C", "rain",R.drawable.rain));
        forecastAdapter.notifyDataSetChanged();

        // Initialize Location Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());



        // Set up location callback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            Log.d("LocationUpdate", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                            fetchWeatherData(location.getLatitude(), location.getLongitude());
                        }
                    }
                }
            }
        };

        // Request Location Updates
        requestAccurateLocation();

        return view;
    }

    @SuppressLint("MissingPermission")
    private void requestAccurateLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Log.d("LocationUpdate", "Last Known Location: Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                fetchWeatherData(location.getLatitude(), location.getLongitude());
            }
        });

        // Request fresh location updates
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void fetchWeatherData(double latitude, double longitude) {
        new Thread(() -> {
            try {
                // Current weather API
                String currentWeatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY + "&units=metric";
                String currentWeatherResponse = fetchData(currentWeatherUrl);

                // 5-day forecast API
                String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY + "&units=metric";
                String forecastResponse = fetchData(forecastUrl);

                requireActivity().runOnUiThread(() -> {
                    updateUI(currentWeatherResponse);
                    updateForecastUI(forecastResponse);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String fetchData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }


    private void updateUI(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String city = jsonObject.getString("name");
            String weather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            double temp = jsonObject.getJSONObject("main").getDouble("temp");
            double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");

            requireActivity().runOnUiThread(() -> {
                weatherCity.setText(city);
                weatherDay.setText("Today");
                weatherTemp.setText(temp + "°C");
                weatherCondition.setText(weather);
                weatherWindSpeed.setText("Wind Speed: " + windSpeed + " km/h");

                // Set Weather GIF
                int gifRes;
                String condition = weather.toLowerCase();
                if (condition.contains("sunny")) {
                    gifRes = R.drawable.sunnyday;
                } else if (condition.contains("cloud")) {
                    gifRes = R.drawable.cloudy;
                } else if (condition.contains("rain")) {
                    gifRes = R.drawable.raining;
                } else if (condition.contains("haze")) {
                    gifRes = R.drawable.haze;
                } else if (condition.contains("clear")) {
                    gifRes = R.drawable.clear;
                } else {
                    gifRes = R.drawable.sunnyday;
                }
                Glide.with(requireContext()).load(gifRes).into(weatherGif);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Corrected Placement of updateForecastUI
    private void updateForecastUI(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray forecastArray = jsonObject.getJSONArray("list");

            forecastList.clear(); // Clear old data

            for (int i = 0; i < forecastArray.length(); i += 8) { // Get data for each day
                JSONObject dayData = forecastArray.getJSONObject(i);
                String dateString = dayData.getString("dt_txt").split(" ")[0]; // Extract date

                // Convert date to day name
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                Date date = inputFormat.parse(dateString);
                String dayName = outputFormat.format(date); // Get day name (e.g., Monday)

                double temp = dayData.getJSONObject("main").getDouble("temp");
                String condition = dayData.getJSONArray("weather").getJSONObject(0).getString("main");

                // Map condition to an icon
                int iconRes = R.drawable.clear_sky;
                if (condition.contains("Clear")) iconRes = R.drawable.clear_sky;
                else if (condition.contains("Cloud")) iconRes = R.drawable.scattered_clouds;
                else if (condition.contains("Rain")) iconRes = R.drawable.rain;
                else if (condition.contains("Thunderstorm")) iconRes = R.drawable.thurderstorm;
                else if (condition.contains("Snow")) iconRes = R.drawable.snow;

                forecastList.add(new ForecastModel(dayName, temp + "°C", condition, iconRes));
            }

            requireActivity().runOnUiThread(() -> forecastAdapter.notifyDataSetChanged());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestAccurateLocation();
        }

    }

}
