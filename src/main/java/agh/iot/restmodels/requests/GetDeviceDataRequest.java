package agh.iot.restmodels.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetDeviceDataRequest {
    long deviceId;
    int numberOfLastDataUpdates;
}
