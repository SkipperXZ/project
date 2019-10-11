package com.example.droneapp.activity;

import android.os.Bundle;

import com.example.droneapp.ulity.API;
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
        setImageAdater("joe",markerID);


    }

    private void setImageAdater (String userID,String markerID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHoldeApi = retrofit.create((DroneApi.class));
        Call<List<String>> call = jsonPlaceHoldeApi.getImageUrls(userID,markerID);
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
