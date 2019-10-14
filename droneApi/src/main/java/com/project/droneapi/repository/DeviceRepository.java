package com.project.droneapi.repository;

import com.project.droneapi.model.Device;
import com.project.droneapi.model.DroneRoute;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device,String> {
    List<Device> findAllByUserID(String userID);

    boolean existsByDeviceKey(String deviceKey);
    Device findByDeviceID(String deviceID);

}
