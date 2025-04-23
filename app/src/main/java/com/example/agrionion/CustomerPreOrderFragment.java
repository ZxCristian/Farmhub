package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomerPreOrderFragment extends Fragment {

    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment_preorder, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        CustomerProductAdapter adapter = new CustomerProductAdapter(new ArrayList<>(), productViewModel);
        recyclerView.setAdapter(adapter);

        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            adapter.setProductList(products);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}