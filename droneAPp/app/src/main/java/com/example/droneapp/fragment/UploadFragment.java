package com.example.droneapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.droneapp.JSonPlaceHoldeApi;
import com.example.droneapp.R;
import com.example.droneapp.model.ImageUploadForm;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class UploadFragment extends Fragment implements LocationListener {

    private Button takePhotoButton;
    private ImageView imagePreview;
    private TextView latLonText;
    private Button uploadButton;
    private byte[] imageByte;
    private double lat;
    private double lon;
    private final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 99;
    private JSonPlaceHoldeApi jsonPlaceHoldeApi;
    private boolean isFailed = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        takePhotoButton = (Button) v.findViewById(R.id.takePhotoButton);
        imagePreview = (ImageView) v.findViewById(R.id.perviewImage);
        latLonText = (TextView) v.findViewById(R.id.latlongText);
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
                if(imageByte != null) {
                    ImageUploadForm imageUploadForm = new ImageUploadForm("joe",lat,lon,"today",imageByte);
                    uploadPhoto(imageUploadForm);
                }
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHoldeApi = retrofit.create((JSonPlaceHoldeApi.class));
        /*
        if ( ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION );
        }else{
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            onLocationChanged(location);
        }*/
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,5,this);
            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            onLocationChanged(location);
        }





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


    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void uploadPhoto(ImageUploadForm ap){
        Call<ImageUploadForm> call = jsonPlaceHoldeApi.createImageUploadForm(ap.getUserID(),ap.getLatitude(),ap.getLongitude(),ap.getTimeStamp(),ap.getImageFile());
        call.enqueue(new Callback<ImageUploadForm>() {
            @Override
            public void onResponse(Call<ImageUploadForm> call, Response<ImageUploadForm> response) {
                if(!response.isSuccessful()){
                    isFailed = true;
                    //latLonText.setText(response.code());
                    return;
                }
                Toast toast = Toast.makeText ( getActivity(), "Success", Toast.LENGTH_LONG );
                toast.show ( );
            }

            @Override
            public void onFailure(Call<ImageUploadForm> call, Throwable t) {
               // latLonText.setText(t.toString());
            }
        });



    }

}