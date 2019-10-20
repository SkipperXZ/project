package com.example.droneapp.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import com.example.droneapp.model.FlightInfo;
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
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.google.maps.android.clustering.algo.PreCachingAlgorithmDecorator;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap gmap;
    private DroneApi droneApi;
    private FusedLocationProviderClient fusedLocationClient;
    private Button btn_filter_date;
    private EditText edt_filter_date;
    private Spinner spn_filter_flight;
    private ClusterManager<Marker> clusterManager;
    private boolean isMapSet = false;
    final Calendar myCalendar = Calendar.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        btn_filter_date = v.findViewById(R.id.btn_date_picker);
        edt_filter_date = v.findViewById(R.id.edt_filter_flight_date);
        spn_filter_flight = v.findViewById(R.id.spn_filter_flight);
        edt_filter_date.setEnabled(false);
        AndroidThreeTen.init(getContext());
        final SharedPreferences sp = getActivity().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        Log.d("shared",sp.getString("filter_date",null));


        if(sp.getString("filter_date",null) == null) {
            myCalendar.setTime(new Date());
            edt_filter_date.setText(myCalendar.getTime().toString());
        }else {
            try {
                DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                Date date = isoFormat.parse(sp.getString("filter_date",null));
                myCalendar.setTime(date);
                edt_filter_date.setText(myCalendar.getTime().toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                Date oldTime = myCalendar.getTime();
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edt_filter_date.setText(myCalendar.getTime().toString());

                if(oldTime.compareTo(myCalendar.getTime()) != 0) {
                    sp.edit().putString("filter_date", isoFormat.format(myCalendar.getTime())).commit();
                    setFlightSpinnerItem();
                }

            }

        };
        btn_filter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        spn_filter_flight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!isMapSet) {
                    clusterManager.clearItems();
                    setMarker(clusterManager);
                }else {
                    isMapSet = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return v;
    }


    private void setFlightSpinnerItem(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        droneApi = retrofit.create((DroneApi.class));
        LocalDateTime selectedTime = Instant.ofEpochMilli(myCalendar.getTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        Call<List<FlightInfo>> call = droneApi.getAllFlightByDate(TEMP.USER,selectedTime.toString());
        call.enqueue(new Callback<List<FlightInfo>>() {
            @Override
            public void onResponse(Call<List<FlightInfo>> call, Response<List<FlightInfo>> response) {
                if(!response.isSuccessful()){
                }else {

                    List<FlightInfo> flightInfoList= response.body();
                    ArrayAdapter<FlightInfo> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line,flightInfoList);
                    spn_filter_flight.setAdapter(adapter);
                    if(!flightInfoList.isEmpty()) {
                        gmap.clear();
                        clusterManager.clearItems();
                        setMarker(clusterManager);
                    }else {
                        gmap.clear();
                        clusterManager.clearItems();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<FlightInfo>> call, Throwable t) {
            }
        });
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

    private void setMarker( final ClusterManager clusterManager) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<List<Marker>> call = droneApi.getMarkerByFlightID(((FlightInfo)spn_filter_flight.getSelectedItem()).getFlightID());

        call.enqueue(new Callback<List<Marker>>() {
            @Override
            public void onResponse(Call<List<Marker>> call, Response<List<Marker>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast toast;
                if(getActivity() != null)
                    toast = Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG);


                List<Marker> markers = response.body();
                /*for (Marker m:
                     markers) {
                    Log.d("marker",m.getId());

                }
                Log.d("marker","--------------------------");*/
                clusterManager.addItems(markers);
                clusterManager.cluster();
            }

            @Override
            public void onFailure(Call<List<Marker>> call, Throwable t) {
            }
        });
    }

    private void setUpClusterManager(GoogleMap googleMap) {
        clusterManager = new ClusterManager(this.getContext(), googleMap);  // 3
        ClusterRenderer clusterRenderer = new ClusterRenderer(getActivity(), googleMap, clusterManager);
        clusterManager.setOnClusterItemClickListener(clusterItemClickListener);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(2);
        setCameraToCurrentLocation(gmap);
        enableMyLocation(gmap);
        setGridMap(TEMP.FLIGHT_NAME,TEMP.USER);
        setUpClusterManager(gmap);
        Log.d("marker","map");
        isMapSet = true;
        setFlightSpinnerItem();



    }

    public ClusterManager.OnClusterItemClickListener<Marker> clusterItemClickListener = new ClusterManager.OnClusterItemClickListener<Marker>() {

        @Override
        public boolean onClusterItemClick(Marker item) {
            Intent imageAcitivity = new Intent(getActivity(), GalleryActivity.class);
            imageAcitivity.putExtra("markerID", item.getId());
            imageAcitivity.putExtra("userID", item.getUserID());
            imageAcitivity.putExtra("flightID", ((FlightInfo)spn_filter_flight.getSelectedItem()).getFlightID());
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

    private void setCameraToCurrentLocation(final GoogleMap gmap) {

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
    public void onDestroy() {
        super.onDestroy();


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