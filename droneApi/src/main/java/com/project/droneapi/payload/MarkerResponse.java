package com.project.droneapi.payload;

public class MarkerResponse {

    private String id;
    private String userID;
    private double markerLon;
    private double markerLat;
    private String icon;

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

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }


}
