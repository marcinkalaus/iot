package agh.iot.services;

import agh.iot.models.Device;
import agh.iot.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceDao;

    public Device save (String name) {
        Device device = new Device();
        device.setName(name);
        return deviceDao.save(device);
    }

    public void delete(Long id) {
        deviceDao.deleteById(id);
    }
}
