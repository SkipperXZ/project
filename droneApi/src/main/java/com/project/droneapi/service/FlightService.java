package com.project.droneapi.service;

import com.project.droneapi.model.Device;
import com.project.droneapi.model.DroneRoute;
import com.project.droneapi.model.Flight;
import com.project.droneapi.payload.FlightInfoResponse;
import com.project.droneapi.payload.FlightRespond;
import com.project.droneapi.repository.DroneRouteRopository;
import com.project.droneapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    DeviceService deviceService;

    public Flight createNewFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public Flight getFlight(String flightName,String userID){
        return flightRepository.findFirstByFlightNameAndUserID(flightName,userID);
    }

    public List<Flight> getAllFlight(String userID){
        return flightRepository.findAllByUserID(userID);
    }

    public List<FlightInfoResponse> getAllActiveFlight(String userID) {

        List<Flight> flightList = flightRepository.findAllByUserIDAndStatusCode(userID,1);
        List<FlightInfoResponse> flightInfoResponseList = new ArrayList<>();

        if (flightList != null) {
            for (Flight flight : flightList) {
                Device device = deviceService.getDeviceByDeviceID(flight.getDeviceID());
                String deviceName;
                if (device != null) {
                    deviceName = device.getDeviceName();
                } else {
                    deviceName = "REMOVED";
                }
                flightInfoResponseList.add(new FlightInfoResponse(flight.getFlightID(), flight.getFlightName(), deviceName, flight.getStatusCode(), flight.getDeviceID(), flight.getTimeStamp()));
            }
            return flightInfoResponseList;
        }else{
            return null;
        }

    }

    public boolean isInFlightDevice(String deviceID){
        if(flightRepository.findByDeviceIDAndStatusCode(deviceID,1) == null){
            return false;
        }
        return true;
    }

    public boolean setFinishFlight(String flightID){

        Flight flight = flightRepository.findByFlightID(flightID);
        if(flight!=null) {
            flight.setStatusCode(0);
            flightRepository.save(flight);
            return true;
        }
        return false;
    }

    public List<FlightInfoResponse> getAllFlightByDate(String userID,String date){

            List<Flight> flightList  = flightRepository.findAllByUserID(userID); ;
            List<FlightInfoResponse> flightInfoResponseList = new ArrayList<>();
            LocalDateTime selectedDate = LocalDateTime.parse(date);

        if (flightList != null) {
            for (Flight flight : flightList) {
                LocalDateTime flightTimeStamp = LocalDateTime.parse(flight.getTimeStamp());
                Device device = deviceService.getDeviceByDeviceID(flight.getDeviceID());
                String deviceName;
                if (device != null) {
                    deviceName = device.getDeviceName();
                } else {
                    deviceName = "REMOVED";
                }
                if (selectedDate.toLocalDate().isEqual(flightTimeStamp.toLocalDate())) {
                    flightInfoResponseList.add(new FlightInfoResponse(flight.getFlightID(), flight.getFlightName(), deviceName, flight.getStatusCode(), flight.getDeviceID(), flight.getTimeStamp()));
                }
            }



        }

        return flightInfoResponseList;


    }

}
