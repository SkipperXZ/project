package com.project.droneapi.controller;

import com.project.droneapi.model.Device;
import com.project.droneapi.payload.DeviceResponse;
import com.project.droneapi.payload.FlightRespond;
import com.project.droneapi.service.DeviceService;
import com.project.droneapi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    FlightService flightService;


    @PostMapping("/device/createNewDevice")
    @ResponseBody
    public boolean createNewDevice (@RequestParam String userID,
                                   @RequestParam String deviceName,
                                   @RequestParam String deviceKey){

        if(deviceService.existDeviceKey(deviceKey)){
            return false;
        }else{
            Device device = new Device();
            device.setUserID(userID);
            device.setDeviceName(deviceName);
            device.setDeviceKey(deviceKey);
            deviceService.createNewDevice(device);
            return true;
        }




    }

    @GetMapping("/device/getDeviceByUser")
    @ResponseBody
    public List<Device> getAllDeviceByUser(@RequestParam String userID){

        return deviceService.getAllDeviceByUser(userID);
    }

    @DeleteMapping("/device/removeDevice")
    @ResponseBody
    public boolean deleteDevice(@RequestParam String deviceID){
        System.out.println(deviceID);
        if(!flightService.isInFlightDevice(deviceID) && deviceService.isRemoveDevice(deviceID)){
            return true;
        }else{
            return false;
        }
    }

}
