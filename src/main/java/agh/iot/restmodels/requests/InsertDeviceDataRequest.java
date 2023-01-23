package agh.iot.restmodels.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class InsertDeviceDataRequest {
    private String guid;
    private Integer dataInt;
    private String dataString;
    private Boolean dataBool;
    private Float dataFloat;
}
