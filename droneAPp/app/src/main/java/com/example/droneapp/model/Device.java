package com.example.droneapp.model;

public class Device  {
    private String userID;
    private String deviceName;
    private String deviceKey;

    public Device(String userID, String deviceName, String deviceKey) {
        this.userID = userID;
        this.deviceName = deviceName;
        this.deviceKey = deviceKey;
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
}
