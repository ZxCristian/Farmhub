package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.timestamp.setText(notification.getTimestamp());

        // Set icon based on notification type
        if (notification.getTitle().toLowerCase().contains("pest")) {
            holder.icon.setImageResource(R.drawable.bug_entomology_svgrepo_com); // Add this drawable
        } else if (notification.getTitle().toLowerCase().contains("disease")) {
            holder.icon.setImageResource(R.drawable.medical_disease_sick_svgrepo_com);
            // Add this drawable
        }
        else if (notification.getTitle().toLowerCase().contains("water")) {
            holder.icon.setImageResource(R.drawable.water_drop_svgrepo_com);
            // Add this drawable
        }
        else if (notification.getTitle().toLowerCase().contains("harvest")) {
            holder.icon.setImageResource(R.drawable.harvest_svgrepo_com);
            // Add this drawable
        }
        else if (notification.getTitle().toLowerCase().contains("order")) {
            holder.icon.setImageResource(R.drawable.write_order_order_online_shop_svgrepo_com);
            // Add this drawable
        }else {
            holder.icon.setImageResource(R.drawable.harvest_svgrepo_com); // Default icon
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void addNotification(NotificationModel notification) {
        notificationList.add(0, notification);
        notifyItemInserted(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, message, timestamp;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            message = itemView.findViewById(R.id.textMessage);
            timestamp = itemView.findViewById(R.id.textTimestamp);
            icon = itemView.findViewById(R.id.iconNotification);
        }
    }
}