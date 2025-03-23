package com.example.agrionion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
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
            // Handle adding a new schedule
            Intent intent = new Intent(getActivity(), AddSchedule.class);
            startActivity(intent);
        });


        // Load schedule data
        scheduleList = new ArrayList<>();
        loadScheduleData();

        // Set up the adapter
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);

        return view;
    }

    private void loadScheduleData() {
        scheduleList.add(new ScheduleItem("Eggplant", formatDate("05-06-2025"), formatDate("07-06-2025"), formatDate("10-06-2025"), formatDate("12-06-2025"), R.drawable.eggplant));
        scheduleList.add(new ScheduleItem("Chinese Cabbage", formatDate("06-12-2025"), formatDate("08-06-2025"), formatDate("12-06-2025"), formatDate("15-06-2025"), R.drawable.petchay));
        scheduleList.add(new ScheduleItem("String Beans", formatDate("07-06-2025"), formatDate("09-06-2025"), formatDate("15-06-2025"), formatDate("20-06-2025"), R.drawable.string_beans));
    }

    // Helper method to convert date format
    private String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Return the original date in case of an error
        }
    }
}
