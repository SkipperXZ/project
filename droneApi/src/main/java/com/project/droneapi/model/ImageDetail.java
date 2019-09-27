package com.project.droneapi.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "image_detail")
public class ImageDetail implements Serializable {

    @Id
    @Column(name = "ImageID")
    private String uuid;
    @Column(name = "UserID")
    private String userID;
    @Column(name = "Lat")
    private double latitude;
    @Column(name = "Lon")
    private double longitude;
    @Column(name = "image_time_stamp")
    private String timeStamp;


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
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
