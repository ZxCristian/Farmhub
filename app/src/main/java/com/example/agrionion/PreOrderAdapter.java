package com.example.agrionion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.ViewHolder> {
    private Context context;
    private List<PreOrderItem> productList;

    public PreOrderAdapter(Context context, List<PreOrderItem> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pre_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreOrderItem item = productList.get(position);

        holder.productName.setText(item.getProductName());
        holder.productWeight.setText(item.getProductWeight());
        holder.productSeller.setText(item.getProductSeller());
        holder.productImage.setImageResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productWeight, productSeller;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.PreOrderproductName);
            productWeight = itemView.findViewById(R.id.PreOrderproductWeight);
            productSeller = itemView.findViewById(R.id.PreOrderproductSeller);
            productImage = itemView.findViewById(R.id.PreOrderImage);
        }
    }
}
