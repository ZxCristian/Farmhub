package com.example.agrionion;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList != null ? notificationList : new ArrayList<>();
    }

    public void addNotification(NotificationModel notification) {
        notificationList.add(notification);
        notifyItemInserted(notificationList.size() - 1);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.textTitle.setText(notification.getTitle());
        String preview = notification.getMessage().length() > 50 ? notification.getMessage().substring(0, 50) + "..." : notification.getMessage();
        holder.textMessage.setText(preview);
        holder.textTimestamp.setText(notification.getTimestamp());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Details.class);
            intent.putExtra("title", notification.getTitle());
            intent.putExtra("message", notification.getMessage()); // Full message
            intent.putExtra("timestamp", notification.getTimestamp());
            intent.putExtra("pdfUrl", notification.getPdfUrl());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textMessage, textTimestamp;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textMessage = itemView.findViewById(R.id.textMessage);
            textTimestamp = itemView.findViewById(R.id.textTimestamp);
        }
    }
}