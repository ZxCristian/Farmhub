package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyProductHistoryAdapter extends RecyclerView.Adapter<MyProductHistoryAdapter.ViewHolder> {

    private List<ProductHistoryItem> productList;

    public MyProductHistoryAdapter(List<ProductHistoryItem> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductHistoryItem product = productList.get(position);
        holder.txtProductID.setText(product.getProductID());
        holder.txtProductName.setText(product.getProductName());
        holder.txtDate.setText(product.getDate());
        holder.txtPrice.setText("₱" + product.getPrice());
        holder.txtBuyer.setText(product.getBuyer());
        holder.txtQuantity.setText(product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductID, txtProductName, txtDate, txtPrice, txtBuyer, txtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductID = itemView.findViewById(R.id.productID);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtBuyer = itemView.findViewById(R.id.txtBuyer);

        }
    }
}
