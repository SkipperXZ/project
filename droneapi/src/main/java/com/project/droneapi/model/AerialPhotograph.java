package com.project.droneapi.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class AerialPhotograph {
    @Id
    private String imageID;
    @NotNull
    private String userID;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private LocalDateTime timeStamp;
    @NotNull
    private String image;

   /* public AerialPhotograph(String imageID, String userID, double latitude, double longitude, LocalDateTime timeStamp){
       this.imageID = imageID;
       this.userID = userID;
       this.latitude = latitude;
       this.longitude = longitude;
       this.timeStamp = timeStamp;
    }*/

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
