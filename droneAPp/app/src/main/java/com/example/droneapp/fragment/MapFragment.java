package com.example.droneapp.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.droneapp.ulity.API;
import com.example.droneapp.ClusterRenderer;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.R;
import com.example.droneapp.ulity.TEMP;
import com.example.droneapp.activity.GalleryActivity;
import com.example.droneapp.model.Flight;
import com.example.droneapp.model.Marker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap gmap;
    private DroneApi droneApi;
    private FusedLocationProviderClient fusedLocationClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
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
    private void setGridMap(String flightName,String userID){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<Flight> call = droneApi.getFlight(flightName,userID);

        call.enqueue(new Callback<Flight>() {
            @Override
            public void onResponse(Call<Flight> call, Response<Flight> response) {
                if(!response.isSuccessful()){
                    Log.d("API",response.toString());
                    return;
                }
                Log.d("API",response.toString());
                Flight flight = response.body();
                double maxLat=-0;
                double minLat=0;
                double maxLon=0;
                double minLon=0;
                if(flight != null) {
                    for (int i = 0; i < flight.getLatitudeList().size(); i++) {
                        if (i == 0) {
                            maxLat = flight.getLatitudeList().get(i);
                            minLat = flight.getLatitudeList().get(i);
                            maxLon = flight.getLongitudeList().get(i);
                            minLon =flight.getLongitudeList().get(i);
                        } else {
                            if (maxLat <= flight.getLatitudeList().get(i))
                                maxLat = flight.getLatitudeList().get(i);
                            if (maxLon <= flight.getLongitudeList().get(i))
                                maxLon = flight.getLongitudeList().get(i);
                            if (minLat >= flight.getLatitudeList().get(i))
                                minLat = flight.getLatitudeList().get(i);
                            if (minLon >= flight.getLongitudeList().get(i))
                                minLon =flight.getLongitudeList().get(i);
                        }
                    }

                    Log.d("point", String.valueOf(maxLat));
                    Log.d("point", String.valueOf(maxLon));
                    Log.d("point", String.valueOf(minLat));
                    Log.d("point", String.valueOf(minLon));

                    PolylineOptions rectLine = new PolylineOptions()
                            .add(new LatLng(maxLat, minLon))
                            .add(new LatLng(minLat, minLon))
                            .add(new LatLng(minLat, maxLon))
                            .add(new LatLng(maxLat, maxLon))
                            .add(new LatLng(maxLat, minLon))
                            .width(3).zIndex(17)
                            .color(Color.rgb(0x23, 0x92, 0x99));

                    gmap.addPolyline(rectLine);
                }
            }

            @Override
            public void onFailure(Call<Flight> call, Throwable t) {
                Log.d("API",t.toString());
            }
        });
    }

    private void setMarker(String userID, final ClusterManager clusterManager) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<List<Marker>> call = droneApi.getMarker(userID);

        call.enqueue(new Callback<List<Marker>>() {
            @Override
            public void onResponse(Call<List<Marker>> call, Response<List<Marker>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast toast = Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG);


                List<Marker> markers = response.body();
                clusterManager.addItems(markers);
                clusterManager.cluster();
            }

            @Override
            public void onFailure(Call<List<Marker>> call, Throwable t) {
            }
        });
    }

    private void setUpClusterManager(GoogleMap googleMap) {
        ClusterManager<Marker> clusterManager = new ClusterManager(this.getContext(), googleMap);  // 3
        ClusterRenderer clusterRenderer = new ClusterRenderer(getActivity(), googleMap, clusterManager);
        clusterManager.setOnClusterItemClickListener(clusterItemClickListener);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);
        setMarker("joe", clusterManager);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(2);
        setCameraToCurrnetLocation(gmap);
        enableMyLocation(gmap);
        setGridMap(TEMP.FLIGHT_NAME,TEMP.USER);
        setUpClusterManager(gmap);

    }

    public ClusterManager.OnClusterItemClickListener<Marker> clusterItemClickListener = new ClusterManager.OnClusterItemClickListener<Marker>() {

        @Override
        public boolean onClusterItemClick(Marker item) {
            Intent imageAcitivity = new Intent(getActivity(), GalleryActivity.class);
            imageAcitivity.putExtra("markerID", item.getId());
            imageAcitivity.putExtra("userID", item.getUserID());
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

    private void setCameraToCurrnetLocation(final GoogleMap gmap) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("GPS", location.getLatitude() + " " + location.getLatitude());
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                        }
                    }
                });



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}