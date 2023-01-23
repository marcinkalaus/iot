package agh.iot.restmodels.requests;

import lombok.Data;

@Data
public class GetModuleDataRequest {
    long moduleId;
    int numberOfLastDataUpdates;
}
