package com.example.ino.gostand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ino.gostand.Adapter.DrinkAdapter;
import com.example.ino.gostand.Model.Drink;
import com.example.ino.gostand.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {

    private List<Drink> lstDrink;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        getSupportActionBar().setTitle("Drink");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lstDrink = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recyclerdrink);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();


        adapter = new DrinkAdapter(lstDrink, this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(DrinkActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Drink drink = lstDrink.get(position);

                final String id = drink.getStand();
                Intent intent = new Intent(DrinkActivity.this, DetailStandActivity.class);
                intent.putExtra("id_stand",id);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

    }

    private JsonArrayRequest getDataFromServer() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.URL_GET_DRINK, new Response.Listener<JSONArray>() {
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
            Drink drink = new Drink();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                drink.setGambar(json.getString("gambar"));
                drink.setName(json.getString("name"));
                drink.setId(json.getString("id"));
                drink.setPrice(json.getString("price"));
                drink.setStand(json.getString("stand"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            lstDrink.add(drink);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }
}
