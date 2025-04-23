package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerProductAdapter extends RecyclerView.Adapter<CustomerProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private ProductViewModel productViewModel;

    public CustomerProductAdapter(List<Product> productList, ProductViewModel productViewModel) {
        this.productList = new ArrayList<>(productList);
        this.productViewModel = productViewModel;
    }

    public void setProductList(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList);
        notifyDataSetChanged();
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
        holder.productAddress.setText("Address: " + product.getAddress());
        holder.productSeller.setText("Seller: " + product.getSeller());
        holder.prebuyid.setText(product.getId());
        holder.productImage.setImageResource(product.getImageResId());

        String weight = product.getWeight();
        if (weight != null && weight.contains("₱")) {
            holder.productAddress.append(" | Price: " + weight);
        }

        holder.preOrderButton.setOnClickListener(v -> {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Product preOrderedProduct = new Product(
                    product.getName(),
                    product.getAddress(),
                    product.getSeller(),
                    product.getWeight(),
                    product.getImageResId(),
                    currentDate,
                    product.getPrice(),
                    "Current User", // Replace with actual user data
                    "1", // Default quantity, replace with user input
                    "PROCESSING"
            );
            productViewModel.addProcessingProduct(preOrderedProduct);
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
        TextView prebuyid;
        TextView productAddress;
        TextView productSeller;
        Button preOrderButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productAddress = itemView.findViewById(R.id.productAddress);
            productSeller = itemView.findViewById(R.id.productSeller);
            prebuyid = itemView.findViewById(R.id.PreBuyID);
            preOrderButton = itemView.findViewById(R.id.preOrderButton);
        }
    }
}