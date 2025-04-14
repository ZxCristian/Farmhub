package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomerProductAdapter extends RecyclerView.Adapter<CustomerProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public CustomerProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pre_order_customer, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productWeight.setText("Weight: " + product.getWeight());
        holder.productSeller.setText("Seller: " + product.getSeller());
        holder.productImage.setImageResource(product.getImageResId());

        // Pre-Order button click listener
        holder.preOrderButton.setOnClickListener(v -> {
            // Handle pre-order action here (e.g., add to cart, show dialog, etc.)
            Toast.makeText(v.getContext(), "Pre-ordered: " + product.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productWeight;
        TextView productSeller;
        Button preOrderButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productWeight = itemView.findViewById(R.id.productWeight);
            productSeller = itemView.findViewById(R.id.productSeller);
            preOrderButton = itemView.findViewById(R.id.preOrderButton);
        }
    }
}