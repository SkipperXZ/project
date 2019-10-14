package com.project.droneapi.payload;

public class FlightInfoResponse {
    private String flightID;
    private String flightName;
    private String deviceName;

    public FlightInfoResponse(String flightID, String flightName, String deviceName, String deviceID, String timeStamp) {
        this.flightID = flightID;
        this.flightName = flightName;
        this.deviceName = deviceName;
        this.deviceID = deviceID;
        this.timeStamp = timeStamp;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String deviceID;
    private String timeStamp;
}
