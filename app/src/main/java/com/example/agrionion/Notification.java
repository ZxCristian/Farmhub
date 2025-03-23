package com.example.agrionion;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationModel> notificationList;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        loadNotifications();

        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
    }

    private void loadNotifications() {
        // Dummy data for testing
        notificationList.add(new NotificationModel("New Order", "You have a new order!", "10:30 AM"));
        notificationList.add(new NotificationModel("Payment Received", "Your payment was successful.", "11:15 AM"));
        notificationList.add(new NotificationModel("Delivery Update", "Your package is on the way.", "1:45 PM"));
        notificationList.add(new NotificationModel("Watering", "Your Eggplant is schedule to water today.", "5:45 AM"));
        notificationList.add(new NotificationModel("Insecticide", "Your Chinese Cabbage is scheduled to be Insecticide today.", "6:00 AM"));
        notificationList.add(new NotificationModel("Harvest", "Your Chili's need to be harvest today", "7:45 AM"));
        notificationList.add(new NotificationModel("New Order", "You have a new order!", "10:30 AM"));
        notificationList.add(new NotificationModel("Payment Received", "Your payment was successful.", "11:15 AM"));
        notificationList.add(new NotificationModel("Delivery Update", "Your package is on the way.", "1:45 PM"));
        notificationList.add(new NotificationModel("Watering", "Your Eggplant is schedule to water today.", "5:45 AM"));
        notificationList.add(new NotificationModel("Insecticide", "Your Chinese Cabbage is scheduled to be Insecticide today.", "6:00 AM"));
        notificationList.add(new NotificationModel("Harvest", "Your Chili's need to be harvest today", "7:45 AM"));
    }
}
