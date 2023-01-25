package agh.iot.restmodels.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class InsertDeviceDataRequest {
    private String guid;
    private Integer dataInt;
    private long epochDate;
}
