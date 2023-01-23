package agh.iot.restmodels.responses;

import agh.iot.entities.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDataResponse {
    String username;
    String email;
    List<Device> userDevices;
}
