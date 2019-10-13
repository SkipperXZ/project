package com.project.droneapi.controller;

import com.project.droneapi.model.Device;
import com.project.droneapi.payload.DeviceResponse;
import com.project.droneapi.payload.FlightRespond;
import com.project.droneapi.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    DeviceService deviceService;


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
    public List<DeviceResponse> getAllDeviceByUser(@RequestParam String userID){
        List<Device> deviceList = deviceService.getAllDeviceByUser(userID);
        List<DeviceResponse> deviceResponseList = new ArrayList<>();
        for (Device d:deviceList) {
            deviceResponseList.add(new DeviceResponse(d.getUserID(),d.getDeviceName(),d.getDeviceKey()));
        }
        return deviceResponseList;
    }

}
