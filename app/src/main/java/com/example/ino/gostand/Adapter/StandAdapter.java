package com.example.ino.gostand.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.CustomVolleyRequest;
import com.example.ino.gostand.Model.Stand;
import com.example.ino.gostand.R;

import java.util.List;

public class StandAdapter extends RecyclerView.Adapter<StandAdapter.StandViewHolder> {

    private Context context;

    List<Stand> standList;

    public StandAdapter(List<Stand> standList, Context context){
        this.standList = standList;
        this.context = context;
    }


    class StandViewHolder extends RecyclerView.ViewHolder {
        //Views
        public ImageView imageView;
        private TextView textViewName,textViewId;



        //Initializing Views
        private StandViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.stand_image);
            textViewName = (TextView) itemView.findViewById(R.id.stand_name);
            textViewId = (TextView)itemView.findViewById(R.id.stand_id);

        }
    }

    @Override
    public StandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stand,parent,false);
        StandViewHolder viewHolder = new StandViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StandViewHolder holder, int postition) {
        Stand stand = standList.get(postition);
        if (standList.get(postition).getGambar().equals("http://dinusheroes.com/newgostand/asset/images/stand/")){
            Glide.with(context).load(R.drawable.mascot_1)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }else {
            Glide.with(context).load(standList.get(postition).getGambar())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }
        holder.textViewName.setText(stand.getName());
        holder.textViewId.setText(stand.getId());
    }

    @Override
    public int getItemCount() {
        return standList.size();
    }
}
