package com.example.agrionion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PreOrderFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private TextView pbifEmpty;
    private FloatingActionButton fabAddPreOrder;
    private PreOrderAdapter preorderAdapter;
    private List<PreOrderItem> productList;  // Changed to PreOrderItem list

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pre_order, container, false);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        pbifEmpty = view.findViewById(R.id.pbifEmpty);
        fabAddPreOrder = view.findViewById(R.id.fabAddPreOrder);

        // Set up RecyclerView with GridLayoutManager (2 columns)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        productRecyclerView.setLayoutManager(gridLayoutManager);

        // Initialize product list
        productList = new ArrayList<>();

        // Set up Adapter
        preorderAdapter = new PreOrderAdapter(getContext(), productList); // Pass Context
        productRecyclerView.setAdapter(preorderAdapter);

        // Check if list is empty and update visibility
        updateEmptyView();

        // Add button click listener
        fabAddPreOrder.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddPreOrder.class);
            startActivity(intent);
        });
        return view;
    }

    private void updateEmptyView() {
        if (productList.isEmpty()) {
            pbifEmpty.setVisibility(View.VISIBLE);
            productRecyclerView.setVisibility(View.GONE);
        } else {
            pbifEmpty.setVisibility(View.GONE);
            productRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
