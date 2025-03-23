package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentListingFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private ClProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_listing, container, false);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Initialize ViewModel
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        // Observe product list changes
        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            productAdapter = new ClProductAdapter(products);
            productRecyclerView.setAdapter(productAdapter);
        });

        return view;
    }
}
