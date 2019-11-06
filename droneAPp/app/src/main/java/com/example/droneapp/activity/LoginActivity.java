package com.example.droneapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.droneapp.R;
import com.example.droneapp.model.LoginForm;
import com.example.droneapp.ulity.Constant;
import com.example.droneapp.ulity.DroneApi;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText textUser,textPass;
    private Button loginButton;
    private DroneApi droneApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textUser = (EditText)findViewById(R.id.userName);
        textPass = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken(textUser.getText().toString(),textPass.getText().toString());
            }
        });
        requestPermission();

    }

    private void getToken(String username,String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginForm loginForm = new LoginForm(username,password);

        droneApi = retrofit.create((DroneApi.class));
        Call<JsonElement> call = droneApi.getToken(loginForm);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(!response.isSuccessful()){

                }else {
                    try {
                        JSONObject obj = new JSONObject(response.body().toString());
                        if(obj !=null){
                            String token =obj.optString("token");
                            if(token != null){
                                SharedPreferences sp = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
                                sp.edit().putString("token",token).commit();
                                newHomeActivity();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("api",t.toString());
            }
        });


    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    private void newHomeActivity(){
        Intent homeIntent = new Intent(this,HomeActivity.class);
        startActivity(homeIntent);
    }
}
