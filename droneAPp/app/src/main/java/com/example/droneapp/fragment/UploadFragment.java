package com.example.droneapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import com.example.droneapp.API;
import com.example.droneapp.DroneApi;
import com.example.droneapp.R;
import com.example.droneapp.model.ImageUploadForm;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;

public class UploadFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button takePhotoButton;
    private ImageView imagePreview;
    private TextView latText, lonText;
    private Button uploadButton;
    private byte[] imageByte;
    private double lat;
    private double lon;
    private final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 99;
    private DroneApi jsonPlaceHoldeApi;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);

        takePhotoButton = (Button) v.findViewById(R.id.takePhotoButton);
        imagePreview = (ImageView) v.findViewById(R.id.perviewImage);
        latText = (TextView) v.findViewById(R.id.tv_lat);
        lonText = (TextView) v.findViewById(R.id.tv_long);
        uploadButton = (Button)v.findViewById(R.id.uploadButton);
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
                    ImageUploadForm imageUploadForm = new ImageUploadForm("joe",lat,lon,"today",imageByte);
                    uploadPhoto(imageUploadForm);
                }
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHoldeApi = retrofit.create((DroneApi.class));

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);






        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
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
                                Log.d("GPS",location.getLatitude() +" " +location.getLatitude());
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                                if(location != null){
                                    latText.setText("Your Latitude : "+lat);
                                    lonText.setText("Your Longitude : "+lon);
                                }
                            }else{
                                latText.setText("please turn on GPS");
                                lonText.setText("please turn on GPS");
                            }
                        }
                    });
        }



    }




    private void uploadPhoto(ImageUploadForm ap){
        Call<ImageUploadForm> call = jsonPlaceHoldeApi.createImageUploadForm(ap.getUserID(),ap.getLatitude(),ap.getLongitude(),ap.getTimeStamp(),ap.getImageFile());
        call.enqueue(new Callback<ImageUploadForm>() {
            @Override
            public void onResponse(Call<ImageUploadForm> call, Response<ImageUploadForm> response) {
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