package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyProductHistoryAdapter extends RecyclerView.Adapter<MyProductHistoryAdapter.ViewHolder> {

    private List<Product> productList;

    public MyProductHistoryAdapter(List<Product> productList) {
        this.productList = new ArrayList<>(productList);
    }

    public void setProductList(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList != null ? newProductList : new ArrayList<>());
        notifyDataSetChanged();
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
        Product product = productList.get(position);
        holder.txtProductID.setText(product.getId() != null ? product.getId() : "N/A");
        holder.txtProductName.setText(product.getName() != null ? product.getName() : "Unknown");
        holder.txtDate.setText(product.getDate() != null ? product.getDate() : "N/A");
        holder.txtPrice.setText(product.getPrice() > 0 ? "₱" + String.format("%.2f", product.getPrice()) : "₱0.00");
        holder.txtBuyer.setText(product.getBuyer() != null ? product.getBuyer() : "N/A");
        holder.txtQuantity.setText(product.getQuantity() != null ? product.getQuantity() : "N/A");
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