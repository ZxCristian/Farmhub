package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantHistoryAdapter extends RecyclerView.Adapter<PlantHistoryAdapter.ViewHolder> {

    private List<PlantHistoryModel> plantHistoryList;

    public PlantHistoryAdapter(List<PlantHistoryModel> plantHistoryList) {
        this.plantHistoryList = plantHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantHistoryModel model = plantHistoryList.get(position);
        holder.batchName.setText(model.getBatchName());
        holder.date.setText(model.getDate());
        holder.quantity.setText(model.getQuantity());
        holder.plantImage.setImageResource(model.getImageResId()); // Set image
    }

    @Override
    public int getItemCount() {
        return plantHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView batchName, date, quantity;
        ImageView plantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batchName = itemView.findViewById(R.id.batchName);
            date = itemView.findViewById(R.id.date);
            quantity = itemView.findViewById(R.id.quantity);
            plantImage = itemView.findViewById(R.id.plantImage); // Reference ImageView
        }
    }
}
