package com.example.ino.gostand.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ino.gostand.Adapter.StandAdapter;
import com.example.ino.gostand.Model.Stand;
import com.example.ino.gostand.R;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private List<Stand> lstStand;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    View view;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore, container, false);

        lstStand = new ArrayList<>();


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerstand);

        int[] covers = new int[]{
                R.drawable.mascot_1};

        //News Post================================================================================
        lstStand.add(new Stand(
                "Ayam Geprek Enak",
                "08122504729",
                "1",
                covers[0]
        ));
        lstStand.add(new Stand(
                "Ayam Geprek mantab",
                "08122504729",
                "2",
                covers[0]
        ));
        lstStand.add(new Stand(
                "Ayam Geprek yahut",
                "08122504729",
                "1",
                covers[0]
        ));
        lstStand.add(new Stand(
                "Ayam Geprek sip",
                "08122504729",
                "1",
                covers[0]
        ));
        lstStand.add(new Stand(
                "Ayam Geprek jos",
                "08122504729",
                "1",
                covers[0]
        ));
        lstStand.add(new Stand(
                "Ayam Geprek terus",
                "08122504729",
                "1",
                covers[0]
        ));



        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapter = new StandAdapter(lstStand, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        return view;

    }
}
