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
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.List;

public class CustomerPreOrderProcessingHistory extends Fragment {

    private RecyclerView recyclerView;
    private CustomerPreOrderHistoryAdapter adapter;
    private List<CustomerPreOrderModel> preOrderList;
    private ConstraintLayout emptyViewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pre_order_processing_history, container, false);

        // Initialize RecyclerView and empty view
        recyclerView = view.findViewById(R.id.recyclerViewProcessing);
        emptyViewGroup = view.findViewById(R.id.emptyViewGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize data (replace with actual data source, e.g., API or database)
        preOrderList = new ArrayList<>();
        // Sample data (comment out or remove for testing empty state)
        preOrderList.add(new CustomerPreOrderModel("945474", "Carrots", "2025-04-05", 8, 20.00));
        preOrderList.add(new CustomerPreOrderModel("437841", "Onions", "2025-04-08", 15, 45.00));

        // Setup adapter
        adapter = new CustomerPreOrderHistoryAdapter(preOrderList);
        recyclerView.setAdapter(adapter);

        // Update visibility based on data
        updateEmptyState();

        return view;
    }

    private void updateEmptyState() {
        if (preOrderList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyViewGroup.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyViewGroup.setVisibility(View.GONE);
        }
    }
}