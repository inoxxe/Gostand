package com.example.ino.gostand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.ino.gostand.Model.User;


import java.util.HashMap;
import java.util.List;

public class DetailItemActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView textViewName,textViewid,textViewdesc,textViewcount,textViewidstand,textViewprice;
    ImageButton btnplus,btnmin;
    Button btncart,btndelete,btnupdate;
    ImageView imageView;
    String id,name,stand,picture,price,status;
    RelativeLayout relativeLayout;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);

        Intent intent = getIntent();
        id = intent.getStringExtra("id_food");
        name = intent.getStringExtra("name");
        stand = intent.getStringExtra("id_stand");
        picture = intent.getStringExtra("picture");
        price = intent.getStringExtra("price");
        status = intent.getStringExtra("status");
        requestQueue = Volley.newRequestQueue(DetailItemActivity.this);

        relativeLayout = (RelativeLayout)findViewById(R.id.layoutbtn);
        textViewName = (TextView)findViewById(R.id.detail_item_name);
        textViewid = (TextView)findViewById(R.id.detail_item_id);
        textViewdesc = (TextView)findViewById(R.id.detail_item_desc);
        textViewidstand = (TextView)findViewById(R.id.detail_item_stand);
        textViewcount = (TextView)findViewById(R.id.detail_item_count);
        textViewprice = (TextView)findViewById(R.id.detail_item_price);
        btnmin = (ImageButton)findViewById(R.id.btnmin);
        btnplus =(ImageButton)findViewById(R.id.btnplus);
        imageView = (ImageView)findViewById(R.id.detail_item_image);
        btncart = (Button)findViewById(R.id.btncart);
        btnupdate = (Button)findViewById(R.id.btnupdate);
        btndelete = (Button)findViewById(R.id.btndelete);

        if(status.equals("baru")){
            btncart.setVisibility(View.VISIBLE);
        }else if(status.equals("update")){
            relativeLayout.setVisibility(View.VISIBLE);
        }

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count+=1;
                textViewcount.setText(String.valueOf(count));
            }
        });

        btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count >= 1){
                    count-=1;
                    textViewcount.setText(String.valueOf(count));
                }
            }
        });

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem(name,count);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteitem(name);

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem(name,count);

            }
        });

        if (picture.equals("http://dinusheroes.com/newgostand/asset/images/product/")){
            Glide.with(DetailItemActivity.this).load(R.drawable.mascot_1)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }else {
            Glide.with(DetailItemActivity.this).load(picture)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }



        textViewName.setText(name);
        textViewid.setText(id);
        textViewidstand.setText(stand);
        textViewcount.setText(String.valueOf(count));
        textViewprice.setText(price);

    }

    private void additem(String name, final int count){
        User user = SharedPrefManager.getInstance(this).getUser();
        final String student  = user.getId();
        final String nama = name;
        final String jumlah = String.valueOf(count);
        class additem extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailItemActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailItemActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("product_unique",nama);
                params.put("student_unique",student);
                params.put("product_count",jumlah);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://dinusheroes.com/newgostand/api/order/add", params);
                return res;
            }
        }

        additem ai = new additem();
        ai.execute();
    }


    private void deleteitem(String name){
        User user = SharedPrefManager.getInstance(this).getUser();
        final String student  = user.getId();
        final String nama = name;
        class deleteitem extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailItemActivity.this,"Menghapus...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailItemActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("product_unique",nama);
                params.put("student_unique",student);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://dinusheroes.com/newgostand/api/order/add", params);
                return res;
            }
        }

        deleteitem di = new deleteitem();
        di.execute();
    }



}
