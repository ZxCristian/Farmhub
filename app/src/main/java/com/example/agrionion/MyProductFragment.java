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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MyProductFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private TextView mpifEmpty;
    private FloatingActionButton fabAddOrder;
    private ClProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_product, container, false);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        mpifEmpty = view.findViewById(R.id.mpifEmpty);
        fabAddOrder = view.findViewById(R.id.fabAddOrder);

        // Initialize ViewModel
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        // Observe ONLY the "My Products" list
        productViewModel.getMyProductList().observe(getViewLifecycleOwner(), products -> {
            productAdapter = new ClProductAdapter(products);
            productRecyclerView.setAdapter(productAdapter);
            checkEmptyState(products);
        });

        productRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        fabAddOrder.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddMyProduct.class);
            startActivity(intent);
        });

        return view; // Ensure the view is returned
    }

    private void checkEmptyState(List<Product> products) {
        if (products == null || products.isEmpty()) {
            mpifEmpty.setVisibility(View.VISIBLE);
            productRecyclerView.setVisibility(View.GONE);
        } else {
            mpifEmpty.setVisibility(View.GONE);
            productRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
