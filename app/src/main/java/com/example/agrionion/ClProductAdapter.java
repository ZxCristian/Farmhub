package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ClProductAdapter extends RecyclerView.Adapter<ClProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ClProductAdapter(List<Product> productList) {
        this.productList = new ArrayList<>(productList);
    }

    public void setProductList(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList != null ? newProductList : new ArrayList<>());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName() != null ? product.getName() : "N/A");
        holder.productSeller.setText(product.getSeller() != null ? product.getSeller() : "N/A");
        holder.productImage.setImageResource(product.getImageResId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productSeller;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productSeller = itemView.findViewById(R.id.productSeller);
        }
    }
}