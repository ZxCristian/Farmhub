package com.example.agrionion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private List<ScheduleItem> scheduleList;
    private FloatingActionButton fabAddPlant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewSchedules);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Floating Action Button
        fabAddPlant = view.findViewById(R.id.fabAddPlant);
        fabAddPlant.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSchedule.class);
            startActivityForResult(intent, 1);
        });

        // Initialize schedule list
        scheduleList = new ArrayList<>();
        loadScheduleData();

        // Set up the adapter
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            String plantId = data.getStringExtra("plantId");
            String plantName = data.getStringExtra("plantName");
            String waterDate = data.getStringExtra("waterDate");
            String fertilizerDate = data.getStringExtra("fertilizerDate");
            String insecticideDate = data.getStringExtra("insecticideDate");
            String harvestDate = data.getStringExtra("harvestDate");
            String imageUri = data.getStringExtra("imageUri");

            ScheduleItem newItem = new ScheduleItem(
                    plantId,
                    plantName,
                    formatDate(waterDate),
                    formatDate(fertilizerDate),
                    formatDate(insecticideDate),
                    formatDate(harvestDate),
                    imageUri
            );
            scheduleList.add(newItem);
            scheduleAdapter.notifyDataSetChanged();
        }
    }

    private void loadScheduleData() {
        // Sample data with dates in "yyyy-MM-dd" format
        scheduleList.add(new ScheduleItem(
                "123456789012345",
                "Eggplant",
                formatDate("2025-06-05"),
                formatDate("2025-06-07"),
                formatDate("2025-06-10"),
                formatDate("2025-06-12"),
                Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.eggplant).toString()
        ));
        scheduleList.add(new ScheduleItem(
                "123456789012346",
                "Chinese Cabbage",
                formatDate("2025-12-06"),
                formatDate("2025-06-08"),
                formatDate("2025-06-12"),
                formatDate("2025-06-15"),
                Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.petchay).toString()
        ));
        scheduleList.add(new ScheduleItem(
                "123456789012347",
                "String Beans",
                formatDate("2025-06-07"),
                formatDate("2025-06-09"),
                formatDate("2025-06-15"),
                formatDate("2025-06-20"),
                Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.string_beans).toString()
        ));
    }

    private String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Return original input if parsing fails
        }
    }
}