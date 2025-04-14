package com.example.agrionion;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerPendingOrdersAdapter extends RecyclerView.Adapter<CustomerPendingOrdersAdapter.OrderViewHolder> {

    private List<CustomerOrder> orderList;

    public CustomerPendingOrdersAdapter(List<CustomerOrder> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPendingOrdersAdapter.OrderViewHolder holder, int position) {
        CustomerOrder order = orderList.get(position);
        holder.tvUid.setText(order.getOrderId());
        holder.tvProductName.setText(order.getProductName());
        holder.tvPrice.setText(String.format("₱%.2f", order.getPrice()));
        holder.tvQuantity.setText(String.valueOf(order.getQuantity()));
        holder.tvDate.setText(order.getDate());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvUid, tvProductName, tvPrice, tvQuantity, tvDate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUid = itemView.findViewById(R.id.tvUid);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}