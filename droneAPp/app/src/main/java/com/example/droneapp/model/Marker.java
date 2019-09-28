package com.example.droneapp.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import androidx.annotation.NonNull;

public class Marker implements ClusterItem {
    private String id;
    private String userID;
    private double markerLon;
    private double markerLat;

    public Marker(String id, String userID, double markerLon, double markerLat) {
        this.id = id;
        this.userID = userID;
        this.markerLon = markerLon;
        this.markerLat = markerLat;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getMarkerLon() {
        return markerLon;
    }
    public void setMarkerLon(double markerLon) {
        this.markerLon = markerLon;
    }
    public double getMarkerLat() {
        return markerLat;
    }
    public void setMarkerLat(double markerLat) {
        this.markerLat = markerLat;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(markerLat,markerLon);
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getSnippet() {
        return "";
    }



}
