package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = new ArrayList<>(productList);
    }

    public void setProductList(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productAddress.setText("Address: " + product.getAddress());
        holder.productSeller.setText("Seller: " + product.getSeller());
        holder.productId.setText(product.getId());
        holder.productImage.setImageResource(product.getImageResId());

        String weight = product.getWeight();
        if (weight != null && weight.contains("₱")) {
            holder.productPrice.setText("Price: " + weight);
        } else {
            holder.productPrice.setText("Price: N/A");
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productId;
        TextView productAddress;
        TextView productSeller;
        TextView productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productId = itemView.findViewById(R.id.productId);
            productAddress = itemView.findViewById(R.id.productAddress);
            productSeller = itemView.findViewById(R.id.productSeller);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}