package agh.iot.services;

import agh.iot.entities.Module;
import agh.iot.entities.ModuleData;
import agh.iot.repositories.ModuleDataRepository;
import agh.iot.repositories.ModuleRepository;
import agh.iot.restmodels.requests.InsertModuleDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleDataService {

    @Autowired
    ModuleDataRepository moduleDataDao;
    @Autowired
    ModuleRepository moduleDao;

    public ModuleData insertModuleData(InsertModuleDataRequest payload) {
        ModuleData moduleData = new ModuleData();
        moduleData.setDataInt(payload.getDataInt());
        moduleData.setDataString(payload.getDataString());
        moduleData.setDataBool(payload.getDataBool());
        moduleData.setDataFloat(payload.getDataFloat());
        moduleData.setEpochDate(System.currentTimeMillis());

        Module module = moduleDao.findById(payload.getModuleId());
        moduleData.setModule(module);

        return moduleDataDao.save(moduleData);
    }

    public List<ModuleData> getModuleData(long moduleId, int numberOfLastDataUpdates) {

        return moduleDataDao.findAll().stream()
                .filter(d -> d.getModule().getId() == moduleId)
                .collect(Collectors.toList());
    }
}
