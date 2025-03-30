package com.example.agrionion;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        // Swipe to dismiss
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                notificationList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);

        fetchPestAndDiseaseUpdates();
    }

    private void loadNotifications() {
        notificationList.add(new NotificationModel("New Order", "You have a new order!", "10:30 AM"));
        notificationList.add(new NotificationModel("Watering", "Your Eggplant is scheduled to water today.", "5:45 AM"));
    }

    private void fetchPestAndDiseaseUpdates() {
        String currentTime = java.text.SimpleDateFormat.getTimeInstance().format(new java.util.Date());
        adapter.addNotification(new NotificationModel(
                "Pest Alert", "Fruit and Shoot Borer detected on Eggplant. Inspect shoots and fruits.", currentTime
        ));
        adapter.addNotification(new NotificationModel(
                "Disease Alert", "Bacterial Wilt risk on Eggplant. Check for wilting.", currentTime
        ));
        recyclerView.smoothScrollToPosition(0);
    }
}