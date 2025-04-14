package com.example.agrionion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreBuyAdapter extends RecyclerView.Adapter<PreBuyAdapter.ViewHolder> {

    private List<PreBuy> preBuyList;

    public PreBuyAdapter(List<PreBuy> preBuyList) {
        this.preBuyList = preBuyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prebuyhistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreBuy item = preBuyList.get(position);

        holder.preBuyID.setText(item.getId());
        holder.preBuyName.setText(item.getName());
        holder.preBuyDate.setText(item.getDate());
        holder.preBuyPrice.setText("₱" + item.getPrice());
        holder.preBuyBuyer.setText(item.getBuyer());
    }

    @Override
    public int getItemCount() {
        return preBuyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView preBuyID, preBuyName, preBuyDate, preBuyPrice, preBuyBuyer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preBuyID = itemView.findViewById(R.id.PreBuyID);
            preBuyName = itemView.findViewById(R.id.PreBuyName);
            preBuyDate = itemView.findViewById(R.id.PreBuyDate);
            preBuyPrice = itemView.findViewById(R.id.PreBuyPrice);
            preBuyBuyer = itemView.findViewById(R.id.PreBuyBuyer);
        }
    }
}
