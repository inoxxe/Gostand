package com.example.ino.gostand.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.Model.Order;
import com.example.ino.gostand.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;

    List<Order> orderList;

    public OrderAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.context = context;
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {
        //Views
        private TextView textViewName,textViewId,textViewStatus,textViewDate,textViewTime;



        //Initializing Views
        private OrderViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.order_name);
            textViewId = (TextView)itemView.findViewById(R.id.order_id);
            textViewDate = (TextView) itemView.findViewById(R.id.order_date);
            textViewTime = (TextView) itemView.findViewById(R.id.order_time);
            textViewStatus = (TextView) itemView.findViewById(R.id.order_status);

        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        OrderViewHolder viewHolder = new OrderViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int postition) {
        Order order = orderList.get(postition);

        final String status = order.getStatus();
        if(status.equals("3")){
            holder.textViewStatus.setText("Pesanan Sedang Diproses");
        }else if(status.equals("1")){
            holder.textViewStatus.setText("Pesanan Berhasil");
        }else if(status.equals("2")){
            holder.textViewStatus.setText("Pesanan Gagal");
        }

        holder.textViewName.setText(order.getName());
        holder.textViewId.setText(order.getId());
        holder.textViewDate.setText(order.getDate());
        holder.textViewTime.setText(order.getTime());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
