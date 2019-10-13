package com.example.droneapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.droneapp.R;
import com.example.droneapp.adapter.SettingAdapter;
import com.example.droneapp.model.Marker;
import com.example.droneapp.ulity.API;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.ulity.TEMP;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewDeviceActivity extends AppCompatActivity {
    private EditText et_add_device_name;
    private EditText et_add_device_key;
    private Button btn_add_new_device ;
    private TextView tv_error;
    private DroneApi droneApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        et_add_device_name = findViewById(R.id.et_device_name);
        et_add_device_key = findViewById(R.id.et_device_key);
        tv_error = findViewById(R.id.tv_add_device_error);
        btn_add_new_device =findViewById(R.id.btn_add_device);

        btn_add_new_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = et_add_device_name.getText().toString();
                String deviceKey = et_add_device_key.getText().toString();
                if(deviceName != null && deviceKey != null){
                    addNewDevice(deviceName,deviceKey, TEMP.USER);
                }else{

                }

            }
        });


    }

    private void addNewDevice(String deviceName,String deviceKey,String userID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<Boolean> call = droneApi.createNewDevice(userID,deviceName,deviceKey);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    tv_error.setText("failed to connect api");
                }else{
                    if(response.body()){
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("success",true);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }else{
                        tv_error.setText("device key is exist");
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

}
