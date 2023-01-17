package agh.iot.dto;

import agh.iot.entities.Module;
import lombok.Data;

import java.util.Set;

@Data
public class DeviceDto {
    private long id;
    private String name;
    private Set<Module> modules;
}
