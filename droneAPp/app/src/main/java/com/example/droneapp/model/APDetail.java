package com.example.droneapp.model;


import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class APDetail {
    private String uuid;
    private String userID;
    private double latitude;
    private double longitude;
    private String timeStamp;

    public APDetail(String uuid, String userID, double latitude, double longitude, String timeStamp) {
        this.uuid = uuid;
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @SerializedName("body")
    private String text;
}
