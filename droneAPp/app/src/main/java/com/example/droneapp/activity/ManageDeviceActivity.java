package com.example.droneapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.droneapp.R;
import com.example.droneapp.adapter.ManageDeviceAdapter;
import com.example.droneapp.adapter.SettingAdapter;
import com.example.droneapp.model.Device;
import com.example.droneapp.ulity.API;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.ulity.TEMP;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageDeviceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private Button btn_add_new_device ;
    private DroneApi droneApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_device);
        recyclerView = (RecyclerView) findViewById(R.id.rv_manage_device);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        btn_add_new_device = findViewById(R.id.btn_add_new_device);
        btn_add_new_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAddDeviceActivity();
            }
        });

    }

    private void newAddDeviceActivity(){
        Intent intent = new Intent(this,AddNewDeviceActivity.class);
        startActivityForResult(intent,1);
    }

    private void setAdapter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<List<Device>> call = droneApi.getDeviceByUser(TEMP.USER);
        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if(!response.isSuccessful()){
                }else {
                    RecyclerView.Adapter adapter = new ManageDeviceAdapter(response.body());
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if( data.getBooleanExtra("success",false)){
                    setAdapter();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
