package com.example.droneapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.droneapp.R;
import com.example.droneapp.model.Device;
import com.example.droneapp.model.Flight;
import com.example.droneapp.ulity.Constant;
import com.example.droneapp.ulity.DroneApi;
import com.google.android.gms.maps.model.LatLng;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class NewFlightActivity extends AppCompatActivity {

    private EditText et_flight_name;
    private Spinner spn_device_name;
    private Button btn_set_route;
    private Button btn_confirm_new_flight;
    private ImageView iv_snap_map_routes;
    private List<LatLng> routes;
    private DroneApi droneApi;
    private List<Device> deviceList;
    final private int ROUTES_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);
        AndroidThreeTen.init(this);
        et_flight_name = findViewById(R.id.et_flight_name);
        spn_device_name = findViewById(R.id.spn_device_name);
        btn_set_route = findViewById(R.id.btn_set_route);
        btn_confirm_new_flight = findViewById(R.id.btn_confirm_new_flight);
        iv_snap_map_routes = findViewById(R.id.iv_snap_map_routes);

        btn_set_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    Intent returnIntent = new Intent();
                newRouteActivity();
            }
        });

        setDeviceSpinnerItem();

        btn_confirm_new_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device selectedDevice =(Device)spn_device_name.getSelectedItem();
                Flight flight = createNewFlight(et_flight_name.getText().toString(),selectedDevice.getDeviceID(),routes, LocalDateTime.now().toString());
                uploadNewFlight(flight);
            }
        });
    }

    private void newRouteActivity(){
        Intent intent = new Intent(this,RouteActivity.class);
        startActivityForResult(intent,ROUTES_REQUEST_CODE);
    }
    private void setDeviceSpinnerItem(){
        SharedPreferences sp = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<List<Device>> call = droneApi.getAvailableDevice("Bearer "+token);
        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if(!response.isSuccessful()){
                }else {
                    deviceList = response.body();
                    if(!deviceList.isEmpty() ){
                        ArrayAdapter<Device> adapter = new ArrayAdapter<>(NewFlightActivity.this, android.R.layout.simple_dropdown_item_1line, deviceList);
                        spn_device_name.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
            }
        });
    }

    private void uploadNewFlight(Flight flight){
        SharedPreferences sp = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<Flight> call = droneApi.createNewFlight("Bearer " +token,flight.getFlightName(),flight.getDeviceID(),flight.getLatitudeList(),flight.getLongitudeList(),flight.getTimeStamp());
        call.enqueue(new Callback<Flight>() {
            @Override
            public void onResponse(Call<Flight> call, Response<Flight> response) {
                if(!response.isSuccessful()){
                    Log.d("Constant", response.toString());
                    return;
                }
                Log.d("Constant", response.toString());
                finish();
            }

            @Override
            public void onFailure(Call<Flight> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ROUTES_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                this.routes = bundle.getParcelableArrayList("routes");
                loadImageFromStorage(bundle.getString("mapCaptured"));

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "capture.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            iv_snap_map_routes.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public Flight createNewFlight(String flightName, String deviceID,List<LatLng> routeLatLng,String timeStamp){
        List<Double> latitudeList = new ArrayList<>();
        List<Double> longitudeList= new ArrayList<>();;

        for (LatLng e:
                routeLatLng) {
            latitudeList.add(e.latitude);
            longitudeList.add(e.longitude);
        }

        return new Flight(flightName,deviceID,latitudeList,longitudeList,timeStamp);
    }
}
