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
    @Column(name = "flight_id")
    private String flightID;
    @Column(name = "image_time_stamp")
    private LocalDateTime timeStamp;
    @Column(name = "marker_id")
    private String markerID;

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getMarkerID() {
        return markerID;
    }
    public void setMarkerID(String markerID) {
        this.markerID = markerID;
    }
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

}
