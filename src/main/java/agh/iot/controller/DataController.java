package agh.iot.controller;

import agh.iot.entities.Device;
import agh.iot.entities.Module;
import agh.iot.entities.ModuleData;
import agh.iot.restmodels.*;
import agh.iot.services.DeviceService;
import agh.iot.services.JwtUserDetailsService;
import agh.iot.services.ModuleDataService;
import agh.iot.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ModuleDataService moduleDataService;
    @Autowired
    JwtUserDetailsService userService;

    @PostMapping(path ="/addDevice")
    public ResponseEntity<?> addDevice(@RequestBody AddDeviceRequest addDeviceRequest) throws Exception {
        String deviceName = addDeviceRequest.getName();
        long userId = addDeviceRequest.getUserId();
        Device device = deviceService.save(deviceName, userId);

        return ResponseEntity.ok(device.getId());
    }

    @DeleteMapping(path="/removeDevice")
    public ResponseEntity<?> removeDevice(@RequestBody Long deviceId) throws Exception {
        deviceService.delete(deviceId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path ="/addModule")
    public ResponseEntity<?> addModule(@RequestBody AddModuleRequest payload) throws Exception {
        Module module = moduleService.save(payload.getName(), payload.getDeviceId());

        return ResponseEntity.ok(module.getId());
    }

    @PutMapping(path ="/updateModule")
    public ResponseEntity<?> updateModule(@RequestBody UpdateModuleRequest updateModuleRequest) throws Exception {
        int moduleId = updateModuleRequest.getModuleId();
        boolean isActive = updateModuleRequest.isActive();

        Module updateModule = moduleService.update(moduleId, isActive);

        return ResponseEntity.ok(updateModule);
    }

    @GetMapping(path ="/getModuleData")
    public ResponseEntity<?> getModuleData(@RequestBody GetModuleDataRequest moduleDataRequest) throws Exception {

        long moduleId = moduleDataRequest.getModuleId();
        int numberOfLastDataUpdates = moduleDataRequest.getNumberOfLastDataUpdates();
        List<ModuleData> data = moduleDataService.getModuleData(moduleId, numberOfLastDataUpdates);

        return ResponseEntity.ok(data);
    }

    @PostMapping(path ="/insertModuleData")
    public ResponseEntity<?> insertModuleData(@RequestBody InsertModuleDataRequest payload) throws Exception {

        moduleDataService.insertModuleData(payload);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path ="/getUserDevices")
    ResponseEntity<?> getUserDevices(@RequestBody long userId) throws Exception {

        List<Device> userDevices = deviceService.getUserDevices(userId);
        return ResponseEntity.ok(userDevices);
    }
}
