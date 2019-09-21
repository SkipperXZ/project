package com.project.droneapi.model;

import java.io.File;
import java.time.LocalDateTime;

public class AerialPhotograph {
    private String imageID;
    private String userID;
    private double latitude;
    private double longitude;
    private LocalDateTime timeStamp;

    public AerialPhotograph(String imageID, String userID, double latitude, double longitude, LocalDateTime timeStamp){
       this.imageID = imageID;
       this.userID = userID;
       this.latitude = latitude;
       this.longitude = longitude;
       this.timeStamp = timeStamp;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
