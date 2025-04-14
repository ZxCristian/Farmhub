package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerPreOrderHistoryAdapter extends RecyclerView.Adapter<CustomerPreOrderHistoryAdapter.PreOrderViewHolder> {

    private List<CustomerPreOrderModel> preOrderList;

    public CustomerPreOrderHistoryAdapter(List<CustomerPreOrderModel> preOrderList) {
        this.preOrderList = preOrderList;
    }

    @NonNull
    @Override
    public PreOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_item_pre_order_history, parent, false);
        return new PreOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreOrderViewHolder holder, int position) {
        CustomerPreOrderModel preOrder = preOrderList.get(position);
        holder.tvOrderId.setText(preOrder.getOrderId());
        holder.tvProductName.setText(preOrder.getProductName());
        holder.tvDate.setText(preOrder.getDate());
        holder.tvQuantity.setText(String.valueOf(preOrder.getQuantity()+ " kg"));
        holder.tvAmount.setText(String.format("₱%.2f", preOrder.getAmount()));
    }

    @Override
    public int getItemCount() {
        return preOrderList.size();
    }

    static class PreOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvProductName, tvDate, tvQuantity, tvAmount;

        public PreOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}