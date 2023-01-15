package agh.iot.services;

import agh.iot.models.ModuleData;
import agh.iot.repositories.ModuleDataRepository;
import agh.iot.restmodels.InsertModuleDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleDataService {

    @Autowired
    ModuleDataRepository moduleDataDao;

    public ModuleData insertModuleData(InsertModuleDataRequest payload) {
        ModuleData moduleData = new ModuleData();
        moduleData.setId(payload.getId());
        moduleData.setDataInt(payload.getDataInt());
        moduleData.setDataString(payload.getDataString());
        moduleData.setDataBool(payload.getDataBool());
        moduleData.setDataFloat(payload.getDataFloat());

        return moduleDataDao.save(moduleData);
    }
}
