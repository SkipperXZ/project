package com.example.droneapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.droneapp.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {

    private byte[] imageByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        imageByte = getIntent().getExtras().getByteArray("imageByteArray");
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

        photoView.setImageBitmap(bmp);
    }
}
