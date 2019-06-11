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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.CustomVolleyRequest;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.R;
import com.example.ino.gostand.RecyclerTouchListener;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private int jumlah=1;

    List<Food> foodList;

    public FoodAdapter(List<Food> foodList, Context context){
        this.foodList = foodList;
        this.context = context;
    }


    class FoodViewHolder extends RecyclerView.ViewHolder {
        //Views
        public ImageView imageView;
        private TextView textViewName,textViewId,textViewPrice,textViewCount,textViewStand;




        //Initializing Views
        private FoodViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            textViewName = (TextView) itemView.findViewById(R.id.product_name);
            textViewId = (TextView)itemView.findViewById(R.id.product_id);
            textViewPrice = (TextView) itemView.findViewById(R.id.product_price);
            textViewStand = (TextView) itemView.findViewById(R.id.product_stand);

        }
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        FoodViewHolder viewHolder = new FoodViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, int postition) {
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
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
