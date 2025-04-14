package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onConfirmClick(int position);

        void onCancelClick(int position);
    }

    public CartAdapter(List<CartItem> cartItems, OnItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.nameTextView.setText("Name: " + item.getName());
        holder.quantityTextView.setText("Quantity: " + item.getQuantity());
        holder.priceTextView.setText("Price: " + item.getPrice());
        holder.locationTextView.setText("Location: " + item.getLocation());
        holder.contactTextView.setText("Contact Number: " + item.getContactNumber());
        holder.itemImage.setImageResource(item.getImageResId()); // Replace with actual image loading logic if needed

        holder.cancelButton.setOnClickListener(v -> listener.onCancelClick(position));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView nameTextView, quantityTextView, priceTextView, locationTextView, contactTextView;
        Button cancelButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            quantityTextView = itemView.findViewById(R.id.item_quantity);
            priceTextView = itemView.findViewById(R.id.item_price);
            locationTextView = itemView.findViewById(R.id.item_location);
            contactTextView = itemView.findViewById(R.id.item_contact);
            cancelButton = itemView.findViewById(R.id.cancel_button);
        }
    }
}