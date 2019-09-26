package com.example.droneapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.droneapp.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {

    private int imageID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        imageID = getIntent().getExtras().getInt("imageID");

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

        photoView.setImageResource(imageID);
    }
}
