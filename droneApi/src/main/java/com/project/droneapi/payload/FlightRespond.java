package com.project.droneapi.payload;

import java.util.List;

public class FlightRespond {

    String flightID;
    String flightName;
    String deviceID;
    String userID;
    List<Double> latitudeList;
    List<Double> longitudeList;
    String timeStamp;

    public FlightRespond(String flightID,String flightName, String deviceID, String userID, List<Double> latitudeList, List<Double> longitudeList, String timeStamp) {
        this.flightName = flightName;
        this.deviceID = deviceID;
        this.userID = userID;
        this.latitudeList = latitudeList;
        this.longitudeList = longitudeList;
        this.timeStamp = timeStamp;
        this.flightID = flightID;
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

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
