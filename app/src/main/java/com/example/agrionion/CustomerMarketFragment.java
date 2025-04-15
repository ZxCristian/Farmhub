package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomerMarketFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.customer_fragment_market, container, false);

        // Find the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);

        // Set the layout manager (vertical list)

        // Or use GridLayoutManager for a grid:
         recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Sample product data
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Eggplant", "123 Farm Rd, Village", "FarmFresh", "1 kg", R.drawable.chili));
        productList.add(new Product("Eggplant", "123 Farm Rd, Village", "FarmFresh", "1 kg", R.drawable.petchay));
        // Add more products as needed

        // Set the adapter
        ProductAdapter adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}