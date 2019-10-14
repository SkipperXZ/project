package com.project.droneapi.service;

import com.project.droneapi.model.Device;
import com.project.droneapi.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public List<Device> getAllDeviceByUser(String userID){
        return deviceRepository.findAllByUserID(userID);
    }

    public Device createNewDevice(Device device){
        return deviceRepository.save(device);
    }

    public boolean existDeviceKey(String deviceKey){
        return deviceRepository.existsByDeviceKey(deviceKey);

    }

    public Device getDeviceByDeviceID(String deviceID){
        return deviceRepository.findByDeviceID(deviceID);
    }
}
