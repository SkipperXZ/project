package com.example.droneapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.droneapp.model.FlightInfo;
import com.example.droneapp.ulity.Constant;
import com.example.droneapp.ulity.DroneApi;
import com.example.droneapp.R;
import com.example.droneapp.model.ImageUploadForm;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button takePhotoButton;
    private ImageView imagePreview;
    private TextView latText, lonText;
    private Button uploadButton;
    private byte[] imageByte;
    private double lat;
    private double lon;
    private final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 99;
    private DroneApi droneApi;
    private Spinner spn_camera_flight;
    private EditText edt_camera_flight_date;
    private Button btn_date_picker;
    final Calendar myCalendar = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        AndroidThreeTen.init(getContext());

        takePhotoButton = (Button) v.findViewById(R.id.takePhotoButton);
        imagePreview = (ImageView) v.findViewById(R.id.perviewImage);
        latText = (TextView) v.findViewById(R.id.tv_lat);
        lonText = (TextView) v.findViewById(R.id.tv_long);
        uploadButton = (Button)v.findViewById(R.id.uploadButton);
        spn_camera_flight = (Spinner)v.findViewById(R.id.spn_camera_flight) ;
        edt_camera_flight_date = (EditText) v.findViewById(R.id.edt_camera_flight_date);
        btn_date_picker = (Button)v.findViewById(R.id.btn_date_picker);
        edt_camera_flight_date.setText("Not Use");
        //myCalendar.setTime(new Date());
        setFlightSpinnerItem();

       /* final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edt_camera_flight_date.setText(myCalendar.getTime().toString());
                setFlightSpinnerItem();
            }

        };*/

        btn_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });



        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageByte != null && lat !=0  && lon != 0) {
                    String flightID = ((FlightInfo) spn_camera_flight.getSelectedItem()).getFlightID();

                    //LocalDateTime selectedTime = Instant.ofEpochMilli(myCalendar.getTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    ImageUploadForm imageUploadForm = new ImageUploadForm(lat,lon,flightID, LocalDateTime.now().toString(),imageByte);
                    uploadPhoto(imageUploadForm);
                }
            }
        });


        return v;
    }


    private void setFlightSpinnerItem(){
        SharedPreferences sp = getActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        droneApi = retrofit.create((DroneApi.class));
        //LocalDateTime selectedTime = Instant.ofEpochMilli(myCalendar.getTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        Call<List<FlightInfo>> call = droneApi.getAllActiveFlightInfo("Bearer "+token);
        call.enqueue(new Callback<List<FlightInfo>>() {
            @Override
            public void onResponse(Call<List<FlightInfo>> call, Response<List<FlightInfo>> response) {

                //Log.d("Constant",response.toString());
                if(!response.isSuccessful()){
                }else {

                    List<FlightInfo> flightInfoList= response.body();
                    ArrayAdapter<FlightInfo> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line,flightInfoList);
                    spn_camera_flight.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<List<FlightInfo>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imagePreview.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageByte = stream.toByteArray();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    Log.d("GPS", location.getLatitude() + " " + location.getLatitude());
                                    lat = location.getLatitude();
                                    lon = location.getLongitude();
                                    if (location != null) {
                                        latText.setText("Your Latitude : " + lat);
                                        lonText.setText("Your Longitude : " + lon);
                                    }
                                } else {
                                    latText.setText("please turn on GPS");
                                    lonText.setText("please turn on GPS");
                                }
                            }
                        });
            }


        }
    }




    private void uploadPhoto(ImageUploadForm ap){


        SharedPreferences sp = getActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);


        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","Bearer "+ token);

        Call<ImageUploadForm> call = droneApi.createImageUploadForm(headers,ap.getLatitude(),ap.getLongitude(),ap.getFlightID(),ap.getTimeStamp(),ap.getImageFile());
        call.enqueue(new Callback<ImageUploadForm>() {
            @Override
            public void onResponse(Call<ImageUploadForm> call, Response<ImageUploadForm> response) {
                Log.d("upload",response.toString());

                Log.d("api",response.toString());
                if(!response.isSuccessful()){
                    //latLonText.setText(response.code());
                    Toast toast = Toast.makeText ( getActivity(), "Failed to upload", Toast.LENGTH_LONG );
                    toast.show ( );
                    return;
                }
                Toast toast = Toast.makeText ( getActivity(), "Upload successful", Toast.LENGTH_LONG );
                toast.show ( );
            }

            @Override
            public void onFailure(Call<ImageUploadForm> call, Throwable t) {
                Toast toast = Toast.makeText ( getActivity(), "Failed to connect api", Toast.LENGTH_LONG );
                toast.show ( );
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