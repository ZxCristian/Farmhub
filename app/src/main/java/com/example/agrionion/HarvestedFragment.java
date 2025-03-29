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

public class HarvestedFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlantHistoryAdapter adapter;
    private List<PlantHistoryModel> harvestedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample Data
        harvestedList = new ArrayList<>();
        harvestedList.add(new PlantHistoryModel("Eggplant Batch 1", "March 10, 2024", "10 kg", R.drawable.eggplant));
        harvestedList.add(new PlantHistoryModel("Petchay Batch 2", "April 5, 2024", "15 kg", R.drawable.petchay));

        adapter = new PlantHistoryAdapter(harvestedList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
