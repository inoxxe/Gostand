package com.example.ino.gostand.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ino.gostand.Adapter.FoodAdapter;
import com.example.ino.gostand.Adapter.OrderAdapter;
import com.example.ino.gostand.Adapter.StandAdapter;
import com.example.ino.gostand.Config;
import com.example.ino.gostand.DetailOrderActivity;
import com.example.ino.gostand.DetailStandActivity;
import com.example.ino.gostand.DrinkActivity;
import com.example.ino.gostand.FoodActivity;
import com.example.ino.gostand.Model.Food;
import com.example.ino.gostand.Model.Order;
import com.example.ino.gostand.Model.Stand;
import com.example.ino.gostand.Model.User;
import com.example.ino.gostand.R;
import com.example.ino.gostand.RecyclerTouchListener;
import com.example.ino.gostand.RequestHandler;
import com.example.ino.gostand.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment {

    private List<Order> lstOrder;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    ProgressDialog mDialog;



    View view;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        lstOrder = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(view.getContext());


        recyclerView = (RecyclerView) view.findViewById(R.id.recycleroreder);

        getData();

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Order order = lstOrder.get(position);
                final String id = order.getId();
                final String name = order.getName();
                final String date = order.getDate();
                final String time = order.getTime();
                final String totalprice = order.getTotalprice();
                final String status = "Pesanan Sedang Diproses";
                Intent intent = new Intent(getContext(), DetailOrderActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("price",totalprice);
                intent.putExtra("status",status);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));


        return view;

    }

    //This method will get data from the web api
    private void getData() {


        class getData extends AsyncTask<Void, Void, String> {

            private User user = SharedPrefManager.getInstance(getActivity()).getUser();
            final String id = user.getId();

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
                                null
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final OrderAdapter orderAdapter = new OrderAdapter(lstOrder, getContext());
                orderAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest("http://dinusheroes.com/newgostand/api/order/running?id="+id, params);
            }
        }

        getData gd = new getData();
        gd.execute();
    }
}