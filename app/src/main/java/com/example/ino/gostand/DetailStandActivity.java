package com.example.ino.gostand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.Adapter.DrinkAdapter;
import com.example.ino.gostand.Adapter.FoodAdapter;
import com.example.ino.gostand.Model.Drink;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.Model.Order;
import com.example.ino.gostand.Model.Stand;
import com.example.ino.gostand.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailStandActivity extends AppCompatActivity {

    private List<Food> lstFood;
    private List<Drink> lstDrink;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewFood,recyclerViewDrink;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager1,layoutManager2;

    private TextView textViewName,textViewPhone;
    private ImageView imageView;
    private FloatingActionButton fab;
    private String id,name,phone,gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stand);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        gambar = intent.getStringExtra("gambar");

        lstFood = new ArrayList<>();
        lstDrink = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        fab = (FloatingActionButton)findViewById(R.id.fabcart);
        recyclerViewFood = (RecyclerView) findViewById(R.id.recyclerdetail_food);
        recyclerViewDrink = (RecyclerView) findViewById(R.id.recycler_detail_drink);
        layoutManager1 = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerViewFood.setLayoutManager(layoutManager1);
        recyclerViewFood.setNestedScrollingEnabled(false);
        recyclerViewDrink.setLayoutManager(layoutManager2);
        recyclerViewDrink.setNestedScrollingEnabled(false);

        getItem();
        //getDrink();

        recyclerViewFood.addOnItemTouchListener(new RecyclerTouchListener(DetailStandActivity.this, recyclerViewFood, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Food food = lstFood.get(position);
                final String id_food = food.getId();
                final String name = food.getName();
                final String price = food.getPrice();
                final String picture = food.getGambar();
                final String status = "baru";
                Intent intent = new Intent(DetailStandActivity.this, DetailItemActivity.class);
                intent.putExtra("id_stand",id);
                intent.putExtra("name",name);
                intent.putExtra("id_food",id_food);
                intent.putExtra("picture",picture);
                intent.putExtra("price",price);
                intent.putExtra("status",status);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = SharedPrefManager.getInstance(DetailStandActivity.this).getUser();
                final String id = user.getId();
                Intent i = new Intent(DetailStandActivity.this,CartActivity.class);
                i.putExtra("student_id",id);
                startActivity(i);
            }
        });

        imageView = (ImageView)findViewById(R.id.detail_standimage);
        textViewName = (TextView)findViewById(R.id.detail_standname);
        textViewPhone = (TextView)findViewById(R.id.detail_standphone);

        textViewName.setText(name);
        textViewPhone.setText(phone);

        if (gambar.equals("http://dinusheroes.com/newgostand/asset/images/stand/")){
            Glide.with(DetailStandActivity.this).load(R.drawable.mascot_1)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }else {
            Glide.with(DetailStandActivity.this).load(gambar)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

    }

    private void getItem() {


        class getItem extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressdetailfood);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray =  jsonObject.getJSONArray("food");

                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        lstFood.add(new Food(
                                productObject.getString("product_name"),
                                productObject.getString("product_unique"),
                                productObject.getString("product_price"),
                                id,
                                productObject.getString("product_picture_link"),
                                null
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               final FoodAdapter foodadapter = new FoodAdapter(lstFood, DetailStandActivity.this);
                foodadapter.notifyDataSetChanged();
                recyclerViewFood.setAdapter(foodadapter);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray =  jsonObject.getJSONArray("beverage");

                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        lstDrink.add(new Drink(
                                productObject.getString("product_name"),
                                productObject.getString("product_unique"),
                                productObject.getString("product_price"),
                                id,
                                productObject.getString("product_picture_link"),
                                null
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final DrinkAdapter adapter = new DrinkAdapter(lstDrink, DetailStandActivity.this);
                adapter.notifyDataSetChanged();
                recyclerViewDrink.setAdapter(adapter);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest("https://dinusheroes.com/newgostand/api/stand/listfoodbeverage?id="+id, params);
            }
        }

        getItem gf = new getItem();
        gf.execute();
    }

}
