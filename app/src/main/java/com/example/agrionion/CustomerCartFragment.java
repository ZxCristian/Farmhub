package com.example.agrionion;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class CustomerCartFragment extends Fragment implements CartAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data (replace with actual data from your app's data source)
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Product 1", "2", "$10", "Location A", "1234567890", R.drawable.petchay));
        cartItems.add(new CartItem("Product 2", "1", "$5", "Location B", "0987654321", R.drawable.eggplant));
        cartItems.add(new CartItem("Product 1", "2", "$10", "Location A", "1234567890", R.drawable.petchay));
        cartItems.add(new CartItem("Product 2", "1", "$5", "Location B", "0987654321", R.drawable.eggplant));
        cartItems.add(new CartItem("Product 1", "2", "$10", "Location A", "1234567890", R.drawable.petchay));
        cartItems.add(new CartItem("Product 2", "1", "$5", "Location B", "0987654321", R.drawable.eggplant));
        cartItems.add(new CartItem("Product 1", "2", "$10", "Location A", "1234567890", R.drawable.petchay));
        cartItems.add(new CartItem("Product 2", "1", "$5", "Location B", "0987654321", R.drawable.eggplant));
        cartItems.add(new CartItem("Product 1", "2", "$10", "Location A", "1234567890", R.drawable.petchay));
        cartItems.add(new CartItem("Product 2", "1", "$5", "Location B", "0987654321", R.drawable.eggplant));



        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);

        return view;
    }

    @Override
    public void onConfirmClick(int position) {
        // Handle confirm action (e.g., mark the item as confirmed, update database, etc.)
        // For now, let's just remove the item as a placeholder action
        cartItems.remove(position);
        cartAdapter.notifyItemRemoved(position);
        cartAdapter.notifyItemRangeChanged(position, cartItems.size());
    }

    @Override
    public void onCancelClick(int position) {
        // Handle cancel action (e.g., remove the item from the cart)
        cartItems.remove(position);
        cartAdapter.notifyItemRemoved(position);
        cartAdapter.notifyItemRangeChanged(position, cartItems.size());
    }
}