package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WiltedFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlantHistoryAdapter adapter;
    private List<PlantHistoryModel> wiltedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample Data
        wiltedList = new ArrayList<>();
        wiltedList.add(new PlantHistoryModel("Wilted Batch A", "March 20, 2024", "5 plants", R.drawable.chili));
        wiltedList.add(new PlantHistoryModel("Wilted Batch B", "April 10, 2024", "8 plants", R.drawable.string_beans));

        adapter = new PlantHistoryAdapter(wiltedList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
