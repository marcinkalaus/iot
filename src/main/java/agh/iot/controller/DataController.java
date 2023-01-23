package agh.iot.controller;

import agh.iot.entities.Device;
import agh.iot.entities.DeviceData;
import agh.iot.entities.User;
import agh.iot.restmodels.requests.*;
import agh.iot.restmodels.responses.UserDataResponse;
import agh.iot.security.JwtTokenUtil;
import agh.iot.services.DeviceDataService;
import agh.iot.services.DeviceService;
import agh.iot.services.JwtUserDetailsService;
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
    DeviceDataService deviceDataService;
    @Autowired
    JwtUserDetailsService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(path ="/addDevice")
    public ResponseEntity<?> addDevice(@RequestBody AddDeviceRequest addDeviceRequest,
                                       @RequestHeader (name="Authorization") String tokenHeader) throws Exception {
        Device device = null;
        String deviceName = addDeviceRequest.getName();
        String guid = addDeviceRequest.getGuid();

        String username = jwtTokenUtil.getUsernameFromRequestHeader(tokenHeader);

        List<Device> allDevices = deviceService.getAllDevices();
        Device deviceWithRequestedGuid = allDevices.stream()
                .filter(d -> d.getGuid().equals(guid))
                .findFirst()
                .orElse(null);

        User user = userService.getUser(username);
        if (deviceWithRequestedGuid == null) {
            device = deviceService.save(deviceName, guid, user.getId());
        } else {
            device = deviceService.addUserToDevice(deviceWithRequestedGuid, user);
        }

        return ResponseEntity.ok(device.getId());
    }

    @DeleteMapping(path="/removeDevice")
    public ResponseEntity<?> removeDevice(@RequestBody Long deviceId) throws Exception {
        deviceService.delete(deviceId);

        return ResponseEntity.ok().build();
    }

//    @PostMapping(path ="/addModule")
//    public ResponseEntity<?> addModule(@RequestBody AddModuleRequest payload) throws Exception {
//        Module module = moduleService.save(payload.getName(), payload.getDeviceId());
//
//        return ResponseEntity.ok(module.getId());
//    }

//    @PutMapping(path ="/updateModule")
//    public ResponseEntity<?> updateModule(@RequestBody UpdateModuleRequest updateModuleRequest) throws Exception {
//        int moduleId = updateModuleRequest.getModuleId();
//        boolean isActive = updateModuleRequest.isActive();
//
//        Module updateModule = moduleService.update(moduleId, isActive);
//
//        return ResponseEntity.ok(updateModule);
//    }
//
    @GetMapping(path ="/getDeviceData", produces={"application/json"})
    public ResponseEntity<?> getDeviceData(@RequestBody GetDeviceDataRequest deviceDataRequest) throws Exception {

        long deviceId = deviceDataRequest.getDeviceId();
        int numberOfLastDataUpdates = deviceDataRequest.getNumberOfLastDataUpdates();
        List<DeviceData> data = deviceDataService.getDeviceData(deviceId, numberOfLastDataUpdates);

        return ResponseEntity.ok().body(data);
    }

    @PostMapping(path ="/insertDeviceData")
    public ResponseEntity<?> insertDeviceData(@RequestBody InsertDeviceDataRequest payload) throws Exception {

        deviceDataService.insertData(payload);

        return ResponseEntity.ok().body("Data inserted!");
    }

    @GetMapping(path ="/getUserDevices")
    ResponseEntity<?> getUserDevices(@RequestHeader (name="Authorization") String tokenHeader) throws Exception {

        String username = jwtTokenUtil.getUsernameFromRequestHeader(tokenHeader);
        List<Device> userDevices = deviceService.getUserDevices(username);
        return ResponseEntity.ok(userDevices);
    }

    @GetMapping(path ="/getUserData", produces={"application/json"})
    ResponseEntity<?> getUserData(@RequestHeader (name="Authorization") String tokenHeader) throws Exception {

        String username = jwtTokenUtil.getUsernameFromRequestHeader(tokenHeader);

        List<Device> userDevices = deviceService.getUserDevices(username);
        User user = userService.getUser(username);

        UserDataResponse response = new UserDataResponse();
        response.setUsername(username);
        response.setEmail(user.getEmail());
        response.setUserDevices(userDevices);

        return ResponseEntity.ok().body(response);
    }
}
