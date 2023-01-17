package agh.iot.services;

import agh.iot.entities.Device;
import agh.iot.entities.Module;
import agh.iot.entities.ModuleData;
import agh.iot.repositories.DeviceRepository;
import agh.iot.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleDao;
    @Autowired
    private DeviceRepository deviceDao;

    public Module save(String name, long deviceId) {
        Module module = new Module();
        module.setName(name);

        Device device = deviceDao.findById(deviceId);
        module.setDevice(device);

        return moduleDao.save(module);
    }

    public Module update(int moduleId, boolean isActive) {
        Module updateModule = moduleDao.findById(moduleId);
        if (updateModule == null) {
            throw new EntityNotFoundException("Module not found, id: " + moduleId);
        }
        updateModule.setActive(isActive);

        moduleDao.save(updateModule);
        return updateModule;
    }

//    public List<ModuleData> getModuleData(long moduleId, int numberOfLastDataUpdates) {
//        Module module = moduleDao.findById(moduleId);
//        if (module == null) {
//            throw new EntityNotFoundException("Module not found, id: " + moduleId);
//        }
//        return module.getData();
//    }
}
