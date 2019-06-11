package com.example.ino.gostand.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.Model.Order;
import com.example.ino.gostand.R;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private Context context;

    List<Order> orderList;

    public OrderItemAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.context = context;
    }


    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        //Views
        private TextView textViewName,textViewCount;


        private OrderItemViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.item_name);
            textViewCount = (TextView) itemView.findViewById(R.id.item_count);
        }
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        OrderItemViewHolder viewHolder = new OrderItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderItemViewHolder holder, int postition) {
        Order order = orderList.get(postition);
        holder.textViewName.setText(order.getItemname());
        holder.textViewCount.setText(order.getItemcount());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
