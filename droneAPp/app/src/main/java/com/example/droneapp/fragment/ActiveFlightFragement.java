package com.example.droneapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droneapp.R;
import com.example.droneapp.adapter.ActiveFlightAdapter;
import com.example.droneapp.adapter.ImageAdapter;
import com.example.droneapp.model.Flight;
import com.example.droneapp.model.FlightInfo;
import com.example.droneapp.ulity.API;
import com.example.droneapp.ulity.DroneApi;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ActiveFlightFragement extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DroneApi droneApi;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flight_active, container, false);
        AndroidThreeTen.init(getContext());


        recyclerView = (RecyclerView) v.findViewById(R.id.rv_active_flight);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ActiveFlightAdapter(getFlightInfoList());
        recyclerView.setAdapter(adapter);


        return v;
    }

    public List<FlightInfo> getFlightInfoList(){
        List<FlightInfo> flightInfoList = Arrays.asList(new FlightInfo("f1","d1", LocalDateTime.now())
                                                        ,new FlightInfo("f2","d1",LocalDateTime.now()));
        return flightInfoList;
    }

    /*
    private void setImageAdater (String userID,String markerID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<List<String>> call = droneApi.getImageUrls(userID,markerID);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){

                }
                List<String>  imageUrls = response.body();
                adapter = new ActiveFlightAdapter();
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }*/


}
