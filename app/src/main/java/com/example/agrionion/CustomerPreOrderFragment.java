package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomerPreOrderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment (pre-order marketplace)
        View view = inflater.inflate(R.layout.customer_fragment_preorder, container, false);

        // Find the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);

        // Set the layout manager (vertical list)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Or use GridLayoutManager for a grid:
        // recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Sample pre-order product data
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("541821","Eggplant", "123 Farm Rd, Village", "FarmFresh", "1 kg", R.drawable.eggplant));
        productList.add(new Product("514526","Chinese Cabbage", "456 Green St, Town", "GreenGrow", "500 g", R.drawable.petchay));
        // Add more pre-order products as needed

        // Set the adapter
        CustomerProductAdapter adapter = new CustomerProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}