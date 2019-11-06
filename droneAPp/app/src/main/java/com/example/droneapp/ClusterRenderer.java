package com.example.droneapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.droneapp.model.Marker;
import com.example.droneapp.ulity.Constant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClusterRenderer extends DefaultClusterRenderer<Marker> {
    private Context context;
    private final String BASE_DOWNLOAD_URL = Constant.BASE_API_URL+"downloadFile/";
    private final IconGenerator iconGenerator;
    private final IconGenerator clusterIconGenerator;
    private final ImageView clusterItemImageView;
    private final ImageView clusterImageView;

    public ClusterRenderer(Context context, GoogleMap map, ClusterManager<Marker> clusterManager) {
        super(context, map, clusterManager);
        this.context =context;
        clusterManager.setRenderer(this);
        iconGenerator = new IconGenerator(context);
        clusterIconGenerator = new IconGenerator(context);
        clusterItemImageView = new ImageView(context);
        clusterImageView = new ImageView(context);
        iconGenerator.setContentView(clusterItemImageView);
        clusterIconGenerator.setContentView(clusterImageView);


    }


    @Override
    protected void onBeforeClusterItemRendered(Marker markerItem, MarkerOptions markerOptions) {
        Bitmap icon = clusterIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onClusterItemRendered(final Marker clusterItem, final com.google.android.gms.maps.model.Marker marker) {
        String url = BASE_DOWNLOAD_URL+clusterItem.getIcon();
        SharedPreferences sp = context.getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);

        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .build());



        Glide.with(context)
                .asBitmap()
                .load(glideUrl)
                .override(200, 200)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        clusterItemImageView.setImageBitmap(resource);
                        Bitmap icon = iconGenerator.makeIcon();
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
                        //marker.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }


    @Override
    protected void onBeforeClusterRendered(Cluster<Marker> cluster, MarkerOptions markerOptions) {
        Bitmap icon = clusterIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onClusterRendered(Cluster<Marker> cluster,final com.google.android.gms.maps.model.Marker marker) {

        List<Marker> centerMarker = new ArrayList<>(cluster.getItems());
        String icon = centerMarker.get(centerMarker.size()/2).getIcon();

        Glide.with(context)
                .asBitmap()
                .load(BASE_DOWNLOAD_URL+icon)
                .override(400, 400)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        clusterImageView.setImageBitmap(resource);
                        Bitmap icon = clusterIconGenerator.makeIcon();
                        if(marker != null) {
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
                        }
                        //marker.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<Marker> cluster) {
        return super.shouldRenderAsCluster(cluster);
    }
}