package com.example.ino.gostand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ino.gostand.Adapter.DrinkAdapter;
import com.example.ino.gostand.Adapter.FoodAdapter;
import com.example.ino.gostand.Adapter.OrderItemAdapter;
import com.example.ino.gostand.Model.Drink;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.Model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailOrderActivity extends AppCompatActivity {

    private List<Order> lstOrder;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
     RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;
     TextView textViewName,textViewid,textViewdate,textViewtime,textViewprice,textViewStatus,textViewpayment,textViewchange,textViewpaymenttitle,textViewchangetitle;
     ImageView imageViewdot1,imageViewdot2;
    String id,name,date,time,price,status,payment,change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailorder_activity);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        price = intent.getStringExtra("price");
        status = intent.getStringExtra("status");
        payment = intent.getStringExtra("payment");
        lstOrder = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recycleritem);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        getFood();
        textViewName = (TextView)findViewById(R.id.detail_order_standname);
        textViewid = (TextView)findViewById(R.id.detail_orderid);
        textViewdate = (TextView)findViewById(R.id.detail_orderdate);
        textViewtime = (TextView)findViewById(R.id.detail_ordertime);
        textViewprice = (TextView)findViewById(R.id.detail_estimated);
        textViewStatus = (TextView)findViewById(R.id.detail_order_status);
        textViewpaymenttitle = (TextView)findViewById(R.id.detail_paymenttitle);
        textViewchangetitle = (TextView)findViewById(R.id.detail_changetitle);
        textViewpayment = (TextView)findViewById(R.id.detail_payment);
        textViewchange = (TextView)findViewById(R.id.detail_change);
        imageViewdot1 = (ImageView)findViewById(R.id.dot1);
        imageViewdot2 = (ImageView)findViewById(R.id.dot2);
        if(status.equals("1") ){
            status = "Pesanan Telah Selesai";
        }else if (status.equals("2")){
            status = "Pesanan Ditolak";
        }else{
            status = "Pesanan Sedang di Proses";
        }

        if(payment != null  && price != null){
            change = String.valueOf (Double.parseDouble(payment)-Double.parseDouble(price));
        }
        if(payment != null){
            imageViewdot1.setVisibility(View.VISIBLE);
            textViewpayment.setText(payment);
            textViewpaymenttitle.setVisibility(View.VISIBLE);
            textViewpayment.setVisibility(View.VISIBLE);
        }

        if(change != null){
            if(Double.parseDouble(change) != 0){
                imageViewdot2.setVisibility(View.VISIBLE);
                textViewchange.setText(change);
                textViewchangetitle.setVisibility(View.VISIBLE);
                textViewchange.setVisibility(View.VISIBLE);
            }
        }



        textViewName.setText(name);
        textViewid.setText(id);
        textViewdate.setText(date);
        textViewtime.setText(time);
        textViewprice.setText(price);
        textViewStatus.setText(status);

    }

    private void getFood() {


        class getFood extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray =  jsonObject.getJSONArray("detail");

                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        lstOrder.add(new Order(null,null,null,null,
                                null,
                                productObject.getString("product_name"),
                                productObject.getString("detail_order_unit"),
                                null,
                                null
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final OrderItemAdapter orderItemAdapter = new OrderItemAdapter(lstOrder, DetailOrderActivity.this);
                orderItemAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(orderItemAdapter);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest("http://dinusheroes.com/newgostand/api/order/detail?id="+id, params);
            }
        }

        getFood gf = new getFood();
        gf.execute();
    }




    /*private JsonArrayRequest getDataFromServer() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressdetailfood);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://dinusheroes.com/newgostand/api/stand/listfoodbeverage?id="+id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Calling method parseData to parse the json response
                parseData(response);
                //Hiding the progressbar
                progressBar.setVisibility(View.GONE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getApplicationContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());
        //Incrementing the request counter
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Food food = new Food();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                food.setGambar(json.getString("gambar"));
                food.setName(json.getString("name"));
                food.setId(json.getString("id"));
                food.setPrice(json.getString("price"));
                food.setStand(json.getString("stand"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            lstFood.add(food);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }*/

}
