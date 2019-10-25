package com.example.droneapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CheckLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);
        if(token == null){
            newLoginActivity();
        }else{
            newHomeActivity();
        }


    }

    private void newLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void newHomeActivity(){
        Intent homeIntent = new Intent(this,HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }


}
