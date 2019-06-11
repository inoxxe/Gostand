package com.example.ino.gostand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ino.gostand.Adapter.OrderAdapter;
import com.example.ino.gostand.Adapter.OrderItemAdapter;
import com.example.ino.gostand.Model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private List<Order> lstOrder;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_activity);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        lstOrder = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recyclerorderhistory);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        getFood();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Order order = lstOrder.get(position);
                final String id = order.getId();
                final String name = order.getName();
                final String date = order.getDate();
                final String time = order.getTime();
                final String payment = order.getPrice();
                final String status = order.getStatus();
                final String totalprice = order.getTotalprice();
                Intent intent = new Intent(getApplicationContext(), DetailOrderActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("price",totalprice);
                intent.putExtra("payment",payment);
                intent.putExtra("status",status);
                startActivity(intent);

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
                    JSONArray jsonArray =  jsonObject.getJSONArray("order");

                    for(int i =0;i<jsonArray.length(); i++){
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        lstOrder.add(new Order(
                                productObject.getString("list_order_unique"),
                                productObject.getString("stand_name"),
                                productObject.getString("list_order_status"),
                                productObject.getString("list_order_date"),
                                productObject.getString("list_order_time"),
                                null,
                                null,
                                productObject.getString("list_order_total"),
                                productObject.getString("list_order_payment")
                                ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final OrderAdapter orderAdapter = new OrderAdapter(lstOrder, OrderHistoryActivity.this);
                orderAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest("http://dinusheroes.com/newgostand/api/order/history?id="+id, params);
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
