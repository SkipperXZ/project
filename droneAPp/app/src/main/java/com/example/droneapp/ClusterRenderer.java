package com.example.droneapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.droneapp.activity.PhotoViewActivity;
import com.example.droneapp.model.Marker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClusterRenderer extends DefaultClusterRenderer<Marker> {
    private Context context;
    private final String BASE_DOWNLOAD_URL = API.BASE_API_URL+"downloadFile/";

    public ClusterRenderer(Context context, GoogleMap map, ClusterManager<Marker> clusterManager) {
        super(context, map, clusterManager);
        this.context =context;
        clusterManager.setRenderer(this);
    }


    @Override
    protected void onBeforeClusterItemRendered(Marker markerItem, MarkerOptions markerOptions) {
        /*if (markerItem.getIcon() != null) {
            markerOptions.icon(markerItem.getIcon()); //Here you retrieve BitmapDescriptor from ClusterItem and set it as marker icon
        }
        markerOptions.visible(true);*/
    }

    @Override
    protected void onClusterItemRendered(final Marker clusterItem, final com.google.android.gms.maps.model.Marker marker) {
        Glide.with(context)
                .asBitmap()
                .load(BASE_DOWNLOAD_URL+clusterItem.getIcon())
                .override(200, 200)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


}