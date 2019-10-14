package com.example.droneapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droneapp.R;
import com.example.droneapp.adapter.ActiveFlightAdapter;
import com.example.droneapp.model.Flight;
import com.example.droneapp.model.FlightInfo;
import com.example.droneapp.ulity.API;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.ulity.TEMP;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActiveFlightFragement extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DroneApi droneApi;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flight_active, container, false);
        AndroidThreeTen.init(getContext());


        recyclerView = (RecyclerView) v.findViewById(R.id.rv_active_flight);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setActiveFlightAdapter(TEMP.USER);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        return v;
    }




    private void setActiveFlightAdapter (String userID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<List<FlightInfo>> call = droneApi.getAllFlightInfo(userID);
        call.enqueue(new Callback<List<FlightInfo>>() {
            @Override
            public void onResponse(Call<List<FlightInfo>> call, Response<List<FlightInfo>> response) {
                if(!response.isSuccessful()){

                }else{
                    List<FlightInfo> flightInfoList = response.body();
                    Log.d("API",flightInfoList.get(0).getFlightName());
                    RecyclerView.Adapter adapter = new ActiveFlightAdapter(flightInfoList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<FlightInfo>> call, Throwable t) {

            }
        });
    }


}
