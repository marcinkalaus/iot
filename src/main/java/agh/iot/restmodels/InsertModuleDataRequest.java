package agh.iot.restmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class InsertModuleDataRequest {
    private long moduleId;
    private Integer dataInt;
    private String dataString;
    private Boolean dataBool;
    private Float dataFloat;
}
