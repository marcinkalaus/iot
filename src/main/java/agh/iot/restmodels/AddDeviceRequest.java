package agh.iot.restmodels;

import lombok.Data;

@Data
public class AddDeviceRequest {
    String name;
    long userId;
}
