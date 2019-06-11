package com.example.ino.gostand.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ino.gostand.Adapter.StandAdapter;
import com.example.ino.gostand.Config;
import com.example.ino.gostand.DetailStandActivity;
import com.example.ino.gostand.DrinkActivity;
import com.example.ino.gostand.FoodActivity;
import com.example.ino.gostand.Model.Stand;
import com.example.ino.gostand.R;
import com.example.ino.gostand.RecyclerTouchListener;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private List<Stand> lstStand;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private ImageButton btnfood,btndrink;



    View view;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore, container, false);
        btnfood = (ImageButton)view.findViewById(R.id.btnfood);
        btndrink = (ImageButton)view.findViewById(R.id.btndrink);
        lstStand = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(view.getContext());


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerstand);

        getData();

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapter = new StandAdapter(lstStand, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        btnfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FoodActivity.class);
                startActivity(i);
            }
        });

        btndrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DrinkActivity.class);
                startActivity(i);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Stand stand = lstStand.get(position);
                final String id = stand.getId();
                final String name = stand.getName();
                final String phone = stand.getNumber();
                final String gambar = stand.getGambar();
                Intent intent = new Intent(getContext(), DetailStandActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("gambar",gambar);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));


        return view;

    }

    private JsonArrayRequest getDataFromServer() {

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);


        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.GET_STAND, new Response.Listener<JSONArray>() {
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
                        Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
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
            Stand stand = new Stand();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                stand.setGambar(json.getString("gambar"));
                stand.setName(json.getString("name"));
                stand.setId(json.getString("id"));
                stand.setNumber(json.getString("number"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            lstStand.add(stand);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }
}
