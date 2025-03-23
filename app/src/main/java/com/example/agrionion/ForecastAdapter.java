package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<ForecastModel> forecastList = new ArrayList<>(); // Ensure it is initialized

    public ForecastAdapter(List<ForecastModel> forecastList) {
        if (forecastList != null) {
            this.forecastList = forecastList;
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastModel forecast = forecastList.get(position);
        holder.dayText.setText(forecast.getDay());
        holder.tempText.setText(forecast.getTemperature());
        holder.conditionText.setText(forecast.getCondition());
        holder.weatherIcon.setImageResource(forecast.getIconResId()); // Set the icon
    }

    @Override
    public int getItemCount() {
        return (forecastList != null) ? forecastList.size() : 0;
    }

    // Method to update the forecast list and refresh the adapter
    public void setForecastList(List<ForecastModel> newForecastList) {
        if (newForecastList != null) {
            forecastList.clear();
            forecastList.addAll(newForecastList);
            notifyDataSetChanged(); // Ensure UI updates correctly
        }
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, tempText, conditionText;
        ImageView weatherIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            tempText = itemView.findViewById(R.id.tempText);
            conditionText = itemView.findViewById(R.id.conditionText);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}
