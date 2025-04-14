package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PendingOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomerPendingOrdersAdapter adapter;
    private List<CustomerOrder> orderList;

    private ImageView imageView;
    private TextView noteTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment_pending_orders, container, false);

        recyclerView = view.findViewById(R.id.rvPendingOrders);
        imageView = view.findViewById(R.id.imageView3);
        noteTextView = view.findViewById(R.id.note);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();
        adapter = new CustomerPendingOrdersAdapter(orderList);
        recyclerView.setAdapter(adapter);

        // Load orders
        loadCompletedOrders();

        return view;
    }

    private void loadCompletedOrders() {
        // Clear and load sample orders (replace with your actual data fetching)
        orderList.clear();

        // Uncomment this block to simulate empty list
        /*
        // Simulating no data
        toggleEmptyState(true);
        return;
        */

        

        adapter.notifyDataSetChanged();

        // Toggle empty state based on list size
        toggleEmptyState(orderList.isEmpty());
    }

    private void toggleEmptyState(boolean isEmpty) {
        if (isEmpty) {
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            noteTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            noteTextView.setVisibility(View.GONE);
        }
    }
}
