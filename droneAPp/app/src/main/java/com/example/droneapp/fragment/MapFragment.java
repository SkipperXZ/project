package com.example.droneapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.droneapp.API;
import com.example.droneapp.JSonPlaceHoldeApi;
import com.example.droneapp.R;
import com.example.droneapp.User;
import com.example.droneapp.activity.GalleryActivity;
import com.example.droneapp.model.Marker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gmap;
    private JSonPlaceHoldeApi jsonPlaceHoldeApi;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private Location location;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (getActivity() != null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
            if (mapFragment == null) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                mapFragment = SupportMapFragment.newInstance();
                ft.replace(R.id.mapView, mapFragment).commit();
            }
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }

        }
    }

    private void setMarker(String userID, final ClusterManager clusterManager)  {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHoldeApi = retrofit.create((JSonPlaceHoldeApi.class));
        Call<List<Marker>> call = jsonPlaceHoldeApi.getMarker(userID);

        call.enqueue(new Callback<List<Marker>>() {
            @Override
            public void onResponse(Call<List<Marker>> call, Response<List<Marker>> response) {
                if(!response.isSuccessful()){
                     return;
                }

                Toast toast = Toast.makeText ( getActivity(), "Success", Toast.LENGTH_LONG );



                List<Marker> markers = response.body();
                clusterManager.addItems(markers);
                //Log.d("Retro","Before");
                clusterManager.cluster();
            }

            @Override
            public void onFailure(Call<List<Marker>> call, Throwable t) {
                // latLonText.setText(t.toString());
            }
        });
    }

    private void setUpClusterManager(GoogleMap googleMap) {
        ClusterManager<Marker> clusterManager = new ClusterManager(this.getContext(), googleMap);  // 3
        clusterManager.setOnClusterItemClickListener(clusterItemClickListener);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);
        setMarker("joe",clusterManager);
       /* List<User> items = getItems();
         clusterManager.addItems(items);
         clusterManager.cluster();*/



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(2);
        setCameraToCurrnetLocation(gmap);
        enableMyLocation(gmap);
        setUpClusterManager(gmap);

    }

    public ClusterManager.OnClusterItemClickListener<Marker> clusterItemClickListener = new ClusterManager.OnClusterItemClickListener<Marker>() {

        @Override
        public boolean onClusterItemClick(Marker item) {
            Intent imageAcitivity = new Intent(getActivity(), GalleryActivity.class);
            imageAcitivity.putExtra("markerID",item.getId());
            imageAcitivity.putExtra("userID",item.getUserID());
            startActivity(imageAcitivity);

            return true;
        }
    };

    private void enableMyLocation(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
    }
    private void setCameraToCurrnetLocation(GoogleMap gmap){
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria mCriteria = new Criteria();
        String bestProvider = String.valueOf(manager.getBestProvider(mCriteria, true));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location mLocation = manager.getLastKnownLocation(bestProvider);
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 15));
        }

    }

/*
    @Override
    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
        marker.getTag()
        Intent imageAcitivity = new Intent(getActivity(), GalleryActivity.class);
        startActivity(imageAcitivity);
        return true;
    }*/
}