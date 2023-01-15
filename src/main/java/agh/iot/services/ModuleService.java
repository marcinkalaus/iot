package agh.iot.services;

import agh.iot.models.Module;
import agh.iot.models.ModuleData;
import agh.iot.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleDao;

    public Module save(String name) {
        Module module = new Module();
        module.setName(name);
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

    public List<ModuleData> getModuleData(long moduleId) {
        Module module = moduleDao.findById(moduleId);
        if (module == null) {
            throw new EntityNotFoundException("Module not found, id: " + moduleId);
        }
        return module.getData();
    }
}
