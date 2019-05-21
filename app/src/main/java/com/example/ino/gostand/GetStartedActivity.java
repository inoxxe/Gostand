package com.example.ino.gostand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ino.gostand.Fragments.AccountFragment;
import com.example.ino.gostand.Fragments.CartFragment;
import com.example.ino.gostand.Fragments.ExploreFragment;
import com.example.ino.gostand.Model.User;
import com.example.ino.gostand.SharedPrefManager;

import java.util.HashMap;

public class GetStartedActivity extends AppCompatActivity {

    private EditText editTextnumber;
    private Button buttongetstarted;
    private PrefManager prefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started_activity);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        editTextnumber = (EditText)findViewById(R.id.edittextnumber);
        buttongetstarted = (Button)findViewById(R.id.btngetstarted);





        buttongetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getstarted();
            }
        });

    }

    private void getstarted(){
        User user = SharedPrefManager.getInstance(this).getUser();
        final String id = user.getUsername();
        final String number = editTextnumber.getText().toString();


        if (TextUtils.isEmpty(number)) {
            editTextnumber.setError("Please enter your NISN");
            editTextnumber.requestFocus();
            return;
        }


        class getstarted extends AsyncTask<Void,Void,String> {



            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GetStartedActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(GetStartedActivity.this,s,Toast.LENGTH_LONG).show();
                prefManager.setFirstTimeLaunch(false);
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("number",number);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_GET_STARTED, params);
                return res;
            }
        }

        getstarted ae = new getstarted();
        ae.execute();

    }

    private void launchHomeScreen() {
        startActivity(new Intent(GetStartedActivity.this, MainActivity.class));
        finish();
    }


}