package com.example.agrionion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomerMarketFragment extends Fragment {

    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment_market, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        ClProductAdapter adapter = new ClProductAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            adapter.setProductList(products);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}