package com.example.droneapp.model;

import org.threeten.bp.LocalDateTime;

public class FlightInfo {
    private String flightName;
    private String deviceName;
    private LocalDateTime startTime;

    public FlightInfo(String flightName, String deviceName, LocalDateTime startTime) {
        this.flightName = flightName;
        this.deviceName = deviceName;
        this.startTime = startTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
