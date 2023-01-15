package agh.iot.restmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateModuleRequest {
    int moduleId;
    boolean isActive;
}
