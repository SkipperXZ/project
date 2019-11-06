package com.project.droneapi.repository;

import com.project.droneapi.model.Device;
import com.project.droneapi.model.DroneRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device,String> {
    List<Device> findAllByUserID(String userID);

    boolean existsByDeviceKey(String deviceKey);
    Device findByDeviceID(String deviceID);
    @Transactional
    long removeByDeviceID(String deviceID);

}
