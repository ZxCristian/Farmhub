package com.example.agrionion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Notification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationModel> notificationList;
    private ImageView btnBack;
    private static final String TAG = "NotificationActivity";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
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

        fetchNationalPestAdvisories();
    }

    private void fetchNationalPestAdvisories() {
        String currentTime = new SimpleDateFormat("hh:mm a").format(new Date());
        executor.execute(() -> {
            List<NotificationModel> result = scrapeNationalPestAdvisories(currentTime);
            mainHandler.post(() -> updateUI(result));
        });
    }

    private List<NotificationModel> scrapeNationalPestAdvisories(String currentTime) {
        List<NotificationModel> scrapedNotifications = new ArrayList<>();
        try {
            // Optimize Jsoup with timeout and minimal parsing
            Document doc = Jsoup.connect("https://cpmd.buplant.da.gov.ph/shvw-dgylvrub/")
                    .timeout(5000) // 5-second timeout
                    .get();
            Log.d(TAG, "Page fetched in " + doc.title());

            Element myDiv = doc.selectFirst("#myDIV");
            if (myDiv == null) {
                Log.d(TAG, "No div with id='myDIV' found");
                return scrapedNotifications;
            }

            Log.d(TAG, "Found myDIV: " + myDiv.text().substring(0, Math.min(100, myDiv.text().length())) + "...");

            // Extract title
            String title = myDiv.selectFirst("h4") != null ? myDiv.selectFirst("h4").text() : "National Pest Advisory";
            Log.d(TAG, "Title: " + title);

            // Extract content efficiently
            StringBuilder contentBuilder = new StringBuilder();
            for (Element p : myDiv.select("p")) {
                String pText = p.text().trim();
                if (!pText.isEmpty() && !pText.equals("Download PDF") && !pText.contains("Author:")) {
                    contentBuilder.append(pText).append(" ");
                }
            }
            String content = contentBuilder.toString().trim();
            if (content.isEmpty()) {
                Log.d(TAG, "No meaningful content found in myDIV");
                return scrapedNotifications;
            }
            Log.d(TAG, "Content: " + content);

            // Extract PDF link
            Element pdfLinkElement = myDiv.selectFirst("a");
            String pdfUrl = "";
            if (pdfLinkElement != null) {
                pdfUrl = pdfLinkElement.absUrl("href");
                if (pdfUrl.isEmpty()) pdfUrl = "https://cpmd.buplant.da.gov.ph/" + pdfLinkElement.attr("href");
                Log.d(TAG, "PDF URL: " + pdfUrl);
            }

            // Filter for national pest advisory
            if ((title.toLowerCase().contains("national") || content.toLowerCase().contains("national")) &&
                    (title.toLowerCase().contains("pest") || content.toLowerCase().contains("pest"))) {
                scrapedNotifications.add(new NotificationModel(
                        "National Pest Alert",
                        content,
                        currentTime,
                        pdfUrl
                ));
            }
        } catch (IOException e) {
            Log.e(TAG, "Scraping error: " + e.getMessage());
            return null;
        }
        return scrapedNotifications;
    }

    private void updateUI(List<NotificationModel> result) {
        if (result != null && !result.isEmpty()) {
            for (NotificationModel notification : result) {
                adapter.addNotification(notification);
            }
            recyclerView.smoothScrollToPosition(0);
        } else {
            Toast.makeText(this, "No national pest advisories found in myDIV", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "No national pest advisories matched the criteria in myDIV");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown(); // Clean up executor
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}