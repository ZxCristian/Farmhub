package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private final List<ForecastModel> forecastList;

    public ForecastAdapter(List<ForecastModel> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastModel forecast = forecastList.get(position);
        holder.dayText.setText(forecast.getDay());
        holder.tempText.setText(forecast.getTemp());
        holder.conditionText.setText(forecast.getCondition());
        holder.rainChanceText.setText(forecast.getRainChance());
        holder.weatherIcon.setImageResource(forecast.getIconRes());
        holder.weatherIcon.setContentDescription(forecast.getCondition() + " weather icon");
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, tempText, conditionText, rainChanceText;
        ImageView weatherIcon;

        ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            tempText = itemView.findViewById(R.id.tempText);
            conditionText = itemView.findViewById(R.id.conditionText);
            rainChanceText = itemView.findViewById(R.id.rainChanceText); // New field
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}