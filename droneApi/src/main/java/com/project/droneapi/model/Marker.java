package com.project.droneapi.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "marker")
public class Marker {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "user_id")
    private String userID;
    @Column(name = "marker_lat")
    private double markerLon;
    @Column(name = "marker_lon")
    private double markerLat;

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
}
