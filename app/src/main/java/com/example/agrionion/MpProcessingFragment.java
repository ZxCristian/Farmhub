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

public class MpProcessingFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyProductHistoryAdapter adapter;
    private List<ProductHistoryItem> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mp_processing, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPlaceholderData();

        return view;
    }

    private void loadPlaceholderData() {
        productList = new ArrayList<>();
        productList.add(new ProductHistoryItem("1290", "Onion A", "2025-03-20", 150.00, "John Doe"));
        productList.add(new ProductHistoryItem("1291", "Onion B", "2025-03-21", 180.50, "Jane Smith"));
        productList.add(new ProductHistoryItem("1292", "Onion C", "2025-03-22", 200.00, "Alice Johnson"));

        adapter = new MyProductHistoryAdapter(productList);
        recyclerView.setAdapter(adapter);
    }
}
