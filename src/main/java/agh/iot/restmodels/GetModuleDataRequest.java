package agh.iot.restmodels;

import lombok.Data;

@Data
public class GetModuleDataRequest {
    long moduleId;
    int numberOfLastDataUpdates;
}
