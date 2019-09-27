package com.project.droneapi.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class AerialPhotograph {
    @Id
    private String uuid;
    @NotNull
    private String userID;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private LocalDateTime timeStamp;
    @NotNull
    private String base64Image;


    public String getImageID() {
        return uuid;
    }

    public void setImageID(String imageID) {
        this.uuid = imageID;
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

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String image) {
        this.base64Image = image;
    }
}
