package com.example.droneapp.model;

import java.util.List;

public class Flight {
    private String flightID;
    private String flightName;
    private String deviceID;
    private List<Double> latitudeList;
    private List<Double> longitudeList;
    private String timeStamp;

    public Flight(String flightName, String deviceID, List<Double> latitudeList, List<Double> longitudeList, String timeStamp) {
        this.flightName = flightName;
        this.deviceID = deviceID;
        this.latitudeList = latitudeList;
        this.longitudeList = longitudeList;
        this.timeStamp = timeStamp;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public List<Double> getLatitudeList() {
        return latitudeList;
    }

    public void setLatitudeList(List<Double> latitudeList) {
        this.latitudeList = latitudeList;
    }

    public List<Double> getLongitudeList() {
        return longitudeList;
    }

    public void setLongitudeList(List<Double> longitudeList) {
        this.longitudeList = longitudeList;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
