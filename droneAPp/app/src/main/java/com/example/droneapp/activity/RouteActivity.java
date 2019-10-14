package com.example.droneapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.droneapp.ulity.API;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.R;
import com.example.droneapp.ulity.TEMP;
import com.example.droneapp.model.Flight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback  {
    private GoogleMap gmap;

    private Button btn_lookframe;
    private Button btn_resetpoint;
    private Button btn_go;
    private Button btn_cancel;
    private DroneApi droneApi;
    double maxLat=-0;
    double minLat=0;
    double maxLon=0;
    double minLon=0;

    private ArrayList<LatLng> routePoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        SupportMapFragment mapFragment = (SupportMapFragment)  getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        initButton();




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        gmap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                routePoints.add(point);
                gmap.addMarker(new MarkerOptions().position(point));
                Log.d("addpoint","=====================================");
                Log.d("addpoint",routePoints.toString());
            }
        });
        enableMyLocation(gmap);
        setCameraToCurrnetLocation(gmap);

    }

    private void clearMarker(GoogleMap gmap){
        gmap.clear();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initButton(){
        btn_lookframe = findViewById(R.id.btn_lookFrame);
        btn_lookframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int i =0;
            for (LatLng e:routePoints) {
                if(i==0){
                    maxLat = e.latitude;
                    minLat = e.latitude;
                    maxLon = e.longitude;
                    minLon = e.longitude;
                }else{
                    if(maxLat <= e.latitude)
                        maxLat = e.latitude;
                    if(maxLon <= e.longitude)
                        maxLon = e.longitude;
                    if(minLat >= e.latitude)
                        minLat = e.latitude;
                    if(minLon >= e.longitude)
                        minLon = e.longitude;
                }
                i++;
            }
                Log.d("point",String.valueOf(maxLat));
                Log.d("point",String.valueOf(maxLon));
                Log.d("point",String.valueOf(minLat));
                Log.d("point",String.valueOf(minLon));

                PolylineOptions rectLine = new PolylineOptions()
                        .add(new LatLng(maxLat, minLon))
                        .add(new LatLng(minLat, minLon))
                        .add(new LatLng(minLat, maxLon))
                        .add(new LatLng(maxLat, maxLon))
                        .add(new LatLng(maxLat, minLon))
                        .width(3).zIndex(17)
                        .color(Color.rgb(0x23, 0x92, 0x99));

            gmap.addPolyline(rectLine);

            btn_lookframe.setVisibility(View.GONE);
            btn_resetpoint.setVisibility(View.GONE);
            btn_go.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
            }
        });
        btn_resetpoint = findViewById(R.id.btn_resetPoint);
        btn_resetpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMarker(gmap);
                routePoints = new ArrayList<>();
                minLon = 0;
                minLat = 0;
                maxLat = 0;
                maxLon = 0;
            }
        });

        btn_go = findViewById(R.id.btn_Go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureScreen();
            }
        });
        btn_cancel = findViewById(R.id.btn_Cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_lookframe.setVisibility(View.VISIBLE);
                btn_resetpoint.setVisibility(View.VISIBLE);
                btn_go.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
            }
        });
        btn_go.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);

    }

    private void enableMyLocation(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
    }
    private void setCameraToCurrnetLocation(final GoogleMap gmap) {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("GPS", location.getLatitude() + " " + location.getLatitude());
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                        }
                    }
                });

    }

    private void captureScreen()
    {

        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                // TODO Auto-generated method stub
                bitmap = snapshot;
                String path = saveToInternalStorage(bitmap);
                sendDataBack(path);
            }
        };
        gmap.snapshot(callback);
    }

    private void sendDataBack(String imagePath){

        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("routes",routePoints);
        bundle.putString("mapCaptured",imagePath);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }





    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"capture.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }



}
