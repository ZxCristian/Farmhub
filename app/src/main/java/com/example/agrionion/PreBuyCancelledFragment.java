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
import java.util.ArrayList;
import java.util.List;

public class PreBuyCancelledFragment extends Fragment {

    private RecyclerView recyclerView;
    private PreBuyAdapter adapter;
    private List<PreBuy> orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prebuy_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();
        orderList.add(new PreBuy("1234", "Red Onion", "March 25, 2025", 150.00, "Alice Doe"));
        orderList.add(new PreBuy("5326", "Red Onion", "March 25, 2025", 150.00, "Alice Doe"));
        orderList.add(new PreBuy("8546", "Red Onion", "March 25, 2025", 150.00, "Alice Doe"));
        adapter = new PreBuyAdapter(orderList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
