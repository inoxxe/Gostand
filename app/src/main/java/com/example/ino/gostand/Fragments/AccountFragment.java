package com.example.ino.gostand.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ino.gostand.Model.User;
import com.example.ino.gostand.OrderHistoryActivity;
import com.example.ino.gostand.R;
import com.example.ino.gostand.SharedPrefManager;

public class AccountFragment extends Fragment {

    TextView textViewname,textViewnumber;
    Button btnlogout,btnhistory;
    View view;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        textViewname = view.findViewById(R.id.account_name);
        textViewnumber = view.findViewById(R.id.account_number);
        btnlogout = view.findViewById(R.id.btnlogout);
        btnhistory = view.findViewById(R.id.btnorderhistory);
        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        final String username = user.getName();
        final String number = user.getNumber();
        final String id = user.getId();

        textViewname.setText(username);
        textViewnumber.setText(number);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
            }
        });
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), OrderHistoryActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });


        return view;

    }
}
