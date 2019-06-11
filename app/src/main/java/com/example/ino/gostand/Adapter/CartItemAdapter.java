package com.example.ino.gostand.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.R;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder> {

    private Context context;
    private int jumlah=1;

    List<Food> foodList;

    public CartItemAdapter(List<Food> foodList, Context context){
        this.foodList = foodList;
        this.context = context;
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        //Views
        public ImageView imageView;
        private TextView textViewName,textViewId,textViewPrice,textViewCount,textViewStand;




        //Initializing Views
        private CartViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            textViewName = (TextView) itemView.findViewById(R.id.product_name);
            textViewId = (TextView)itemView.findViewById(R.id.product_id);
            textViewPrice = (TextView) itemView.findViewById(R.id.product_price);
            textViewStand = (TextView) itemView.findViewById(R.id.product_stand);
            textViewCount = (TextView) itemView.findViewById(R.id.product_count);
        }
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        CartViewHolder viewHolder = new CartViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int postition) {
        Food food = foodList.get(postition);
        if (foodList.get(postition).getGambar().equals("http://dinusheroes.com/newgostand/asset/images/product/")){
            Glide.with(context).load(R.drawable.mascot_1)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }else {
            Glide.with(context).load(foodList.get(postition).getGambar())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }
        holder.textViewName.setText(food.getName());
        holder.textViewId.setText(food.getId());
        holder.textViewPrice.setText(food.getPrice());
        holder.textViewStand.setText(food.getStand());
        holder.textViewCount.setVisibility(View.VISIBLE);
        holder.textViewCount.setText(food.getCount());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
