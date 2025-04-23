package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MpCancelledFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyProductHistoryAdapter adapter;
    private ProductViewModel productViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mp_cancelled, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        adapter = new MyProductHistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        productViewModel.getCancelledProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProductList(products);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}