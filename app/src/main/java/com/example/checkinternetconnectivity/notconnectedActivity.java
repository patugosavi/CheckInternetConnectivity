package com.example.checkinternetconnectivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class notconnectedActivity extends AppCompatActivity {

    boolean isConnected;
    Button try_again;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notconnected);

        try_again = findViewById(R.id.try_again_btn);
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected){

                    Intent i = new Intent(notconnectedActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();


                }else   {

                    Toast.makeText(notconnectedActivity.this,"Not Connected",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerNetworkCallback(){


        try {

            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){

                @Override
                public void onAvailable(@NonNull Network network) {
                    isConnected = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    isConnected = false;
                }
            });




        }catch (Exception e){

            isConnected = false;

        }

    }

    private void unregisterNetworkCallback(){
        try {

            connectivityManager.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkCallback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkCallback();
    }

}