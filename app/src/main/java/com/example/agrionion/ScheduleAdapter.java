package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleItem> scheduleList;

    public ScheduleAdapter(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem item = scheduleList.get(position);

        // Set plant image
        holder.imgPlant.setImageResource(item.getImageResId());

        // Set plant name
        holder.tvPlantName.setText(item.getPlantName());

        // Set schedule dates
        holder.tvDateWatering.setText(item.getWateringDate());
        holder.tvDateFertilizer.setText(item.getFertilizerDate());
        holder.tvDateInsecticide.setText(item.getInsecticideDate());
        holder.tvDateHarvest.setText(item.getHarvestDate());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPlant;
        TextView tvPlantName, tvDateWatering, tvDateFertilizer, tvDateInsecticide, tvDateHarvest;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlant = itemView.findViewById(R.id.imgPlant);
            tvPlantName = itemView.findViewById(R.id.tvPlantname);
            tvDateWatering = itemView.findViewById(R.id.tvDateWatering);
            tvDateFertilizer = itemView.findViewById(R.id.tvDateFertilizer);
            tvDateInsecticide = itemView.findViewById(R.id.tvDateInsecticide);
            tvDateHarvest = itemView.findViewById(R.id.tvDateHarvest);
        }
    }
}
