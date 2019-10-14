package com.example.droneapp.model;

public class Device  {
    private String userID;
    private String deviceName;
    private String deviceKey;
    private String deviceID;

    public Device(String userID, String deviceName, String deviceKey,String deviceID) {
        this.userID = userID;
        this.deviceName = deviceName;
        this.deviceKey = deviceKey;
        this.deviceID = deviceID;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    @Override
    public String toString() {
        return this.deviceName;            // What to display in the Spinner list.
    }
}
