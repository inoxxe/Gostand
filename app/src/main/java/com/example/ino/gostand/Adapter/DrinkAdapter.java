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
import com.example.ino.gostand.Model.Drink;

import com.example.ino.gostand.R;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    private int jumlah=1;

    List<Drink> drinkList;

    public DrinkAdapter(List<Drink> drinkList, Context context){
        this.drinkList = drinkList;
        this.context = context;
    }


    class DrinkViewHolder extends RecyclerView.ViewHolder {
        //Views
        public ImageView imageView;
        private TextView textViewName,textViewId,textViewPrice,textViewCount,textViewStand;
        private Button btnadd;
        private ImageButton btnplus,btnmin;



        //Initializing Views
        private DrinkViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            textViewName = (TextView) itemView.findViewById(R.id.product_name);
            textViewId = (TextView)itemView.findViewById(R.id.product_id);
            textViewPrice = (TextView) itemView.findViewById(R.id.product_price);
            textViewStand = (TextView) itemView.findViewById(R.id.product_stand);

        }
    }

    @Override
    public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        DrinkViewHolder viewHolder = new DrinkViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrinkViewHolder holder, int postition) {
        Drink drink = drinkList.get(postition);
        if (drinkList.get(postition).getGambar().equals("http://dinusheroes.com/newgostand/asset/images/product/")){
            Glide.with(context).load(R.drawable.mascot_1)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }else {
            Glide.with(context).load(drinkList.get(postition).getGambar())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }
        holder.textViewName.setText(drink.getName());
        holder.textViewId.setText(drink.getId());
        holder.textViewPrice.setText(drink.getPrice());
        holder.textViewStand.setText(drink.getStand());

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
