package com.project.droneapi.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "droneroute")
public class DroneRoute {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "flightID")
    private String flightID;
    @Column(name = "routePointNumber")
    private int routePointNumber;
    @Column(name = "routePointLatitude")
    private double routePointLatitude;
    @Column(name = "routePointLongitude")
    private double routePointLongitude;


    public int getRoutePointNumber() {
        return routePointNumber;
    }
    public void setRoutePointNumber(int routePointNumber) {
        this.routePointNumber = routePointNumber;
    }
    public double getRoutePointLatitude() {
        return routePointLatitude;
    }
    public void setRoutePointLatitude(double routePointLatitude) {
        this.routePointLatitude = routePointLatitude;
    }
    public double getRoutePointLongitude() {
        return routePointLongitude;
    }
    public void setRoutePointLongitude(double routePointLongitude) {
        this.routePointLongitude = routePointLongitude;
    }
    public String getFlightID() {
        return flightID;
    }
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

}
