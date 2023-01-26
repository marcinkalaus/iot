package agh.iot.controller;

import agh.iot.entities.Device;
import agh.iot.entities.DeviceData;
import agh.iot.entities.User;
import agh.iot.restmodels.requests.AddDeviceRequest;
import agh.iot.restmodels.requests.InsertDeviceDataRequest;
import agh.iot.restmodels.responses.UserDataResponse;
import agh.iot.security.JwtTokenUtil;
import agh.iot.services.DeviceDataService;
import agh.iot.services.DeviceService;
import agh.iot.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            device = deviceService.addUserToDeviceOrUpdateName(deviceWithRequestedGuid, user, deviceName);
        }

        return ResponseEntity.ok(device.getId());
    }

    @DeleteMapping(path="/removeDevice", produces={"application/json"})
    public ResponseEntity<?> removeDevice(@RequestBody Long deviceId) throws Exception {
        deviceService.delete(deviceId);

        return ResponseEntity.ok().body("Device removed successfully!");
    }

    @PutMapping(path="/forgetDevice", produces={"application/json"})
    public ResponseEntity<?> forgetDevice(@RequestParam long deviceId,
                                          @RequestHeader (name="Authorization") String tokenHeader) throws Exception {

        String username = jwtTokenUtil.getUsernameFromRequestHeader(tokenHeader);

        deviceService.forgetDevice(deviceId, username);

        return ResponseEntity.ok().body("Device has been forgotten!");
    }

    @GetMapping (path ="/getDeviceData", produces={"application/json"})
    public ResponseEntity<?> getDeviceData(@RequestParam long deviceId,
                                           @RequestParam int numberOfLastDataUpdates) throws Exception {

        List<DeviceData> data = deviceDataService.getDeviceData(deviceId, numberOfLastDataUpdates);

        return ResponseEntity.ok().body(data);
    }

    @PostMapping(path ="/insertDeviceData", produces={"application/json"})
    public ResponseEntity<?> insertDeviceData(@RequestBody InsertDeviceDataRequest payload) throws Exception {

        DeviceData data = deviceDataService.insertData(payload);

        if(data == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot update data!");
        }
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

    @GetMapping (path ="/syncDate", produces={"application/json"})
    public ResponseEntity<?> getServerData() throws Exception {

        return ResponseEntity.ok().body(System.currentTimeMillis()/1000);
    }
}
