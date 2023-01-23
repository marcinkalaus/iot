package agh.iot.restmodels.requests;

import lombok.Data;

@Data
public class AddDeviceRequest {
    String name;
    String guid;
}
