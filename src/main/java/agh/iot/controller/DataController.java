package agh.iot.controller;

import agh.iot.models.Device;
import agh.iot.models.Module;
import agh.iot.models.ModuleData;
import agh.iot.restmodels.InsertModuleDataRequest;
import agh.iot.restmodels.UpdateModuleRequest;
import agh.iot.services.DeviceService;
import agh.iot.services.ModuleDataService;
import agh.iot.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(path ="/addDevice")
    public ResponseEntity<?> addDevice(@RequestBody String name) throws Exception {
        Device device = deviceService.save(name);

        return ResponseEntity.ok(device.getId());
    }

    @DeleteMapping(path="/removeDevice")
    public ResponseEntity<?> removeDevice(@RequestBody Long deviceId) throws Exception {
        deviceService.delete(deviceId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path ="/addModule")
    public ResponseEntity<?> addModule(@RequestBody String name) throws Exception {
        Module module = moduleService.save(name);

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
    public ResponseEntity<?> getModuleDataModule(@RequestBody Long moduleId) throws Exception {

        List<ModuleData> data = moduleService.getModuleData(moduleId);

        return ResponseEntity.ok(data);
    }

    @PostMapping(path ="/insertModuleData")
    public ResponseEntity<?> insertModuleData(@RequestBody InsertModuleDataRequest payload) throws Exception {

        moduleDataService.insertModuleData(payload);

        return ResponseEntity.ok().build();
    }
}
