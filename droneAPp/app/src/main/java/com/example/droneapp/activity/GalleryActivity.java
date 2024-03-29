package com.example.droneapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.droneapp.ulity.Constant;
import com.example.droneapp.adapter.ImageAdapter;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageAdapter imageAdapter;
    private DroneApi jsonPlaceHoldeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        recyclerView = findViewById(R.id.imageRecyclerView);
        layoutManager = new GridLayoutManager(this,4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        String markerID = getIntent().getExtras().getString("markerID");
        String flightID = getIntent().getExtras().getString("flightID");
        setImageAdater(markerID,flightID);


    }

    private void setImageAdater (String markerID,String flightID){

        SharedPreferences sp = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHoldeApi = retrofit.create((DroneApi.class));
        Call<List<String>> call = jsonPlaceHoldeApi.getImageUrls("Bearer "+token,markerID,flightID);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){

                }
                List<String>  imageUrls = response.body();
                imageAdapter = new ImageAdapter(imageUrls);
                recyclerView.setAdapter(imageAdapter);


            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }



}
