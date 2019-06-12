package com.example.ino.gostand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ino.gostand.Adapter.CartItemAdapter;
import com.example.ino.gostand.Adapter.OrderItemAdapter;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.Model.Order;
import com.example.ino.gostand.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<Food> lstFood;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;
    TextView textViewName,textViewid,textViewdate,textViewtime,textViewprice,textViewStatus,textViewpayment,textViewchange,textViewpaymenttitle,textViewchangetitle;
    Button btnorder;
    String id,name,date,time,price,status,payment,change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        User user = SharedPrefManager.getInstance(CartActivity.this).getUser();
        id = user.getId();
        lstFood = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btnorder = (Button)findViewById(R.id.btnorder);
        textViewprice = (TextView)findViewById(R.id.cart_price);
        recyclerView = (RecyclerView) findViewById(R.id.recyclercart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout(id);
                finish();
            }
        });

        getFood();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(CartActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Food food = lstFood.get(position);
                final String id_food = food.getId();
                final String name = food.getName();
                final String price = food.getPrice();
                final String picture = food.getGambar();
                final String status = "update";
                Intent intent = new Intent(CartActivity.this, DetailItemActivity.class);
                intent.putExtra("id_stand",id);
                intent.putExtra("name",name);
                intent.putExtra("id_food",id_food);
                intent.putExtra("picture",picture);
                intent.putExtra("price",price);
                intent.putExtra("status",status);
                startActivity(intent);
                finish();
                startActivity(getIntent());

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

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

                    textViewprice.setText(jsonObject.getString("total"));
                    JSONArray jsonArray =  jsonObject.getJSONArray("chart");

                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        lstFood.add(new Food(
                                productObject.getString("product_name"),
                                productObject.getString("chart_product_unique"),
                                productObject.getString("product_price"),
                                "",
                                productObject.getString("product_picture_link"),
                                productObject.getString("chart_product_unit")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final CartItemAdapter cartItemAdapter = new CartItemAdapter(lstFood, CartActivity.this);
                cartItemAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(cartItemAdapter);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest("http://dinusheroes.com/newgostand/api/order/chart?id="+id, params);
            }
        }

        getFood gf = new getFood();
        gf.execute();
    }

    private void Checkout(String id) {
        final String name = id;

        class Checkout extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    final String message = obj.getString("message");
                    Toast.makeText(CartActivity.this,message,Toast.LENGTH_LONG).show();
                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", name);

                return requestHandler.sendGetRequest("http://dinusheroes.com/newgostand/api/order/checkout?id="+name, params);
            }
        }

        Checkout cek = new Checkout();
        cek.execute();
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
