package agh.iot.services;

import agh.iot.entities.Device;
import agh.iot.entities.User;
import agh.iot.repositories.DeviceRepository;
import agh.iot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceDao;
    @Autowired
    private UserRepository userDao;

    public Device save (String name, String guid, long userId) {
        Device device = new Device();
        device.setName(name);
        device.setGuid(guid);

        User user = userDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        device.getUsers().add(user);
        return deviceDao.save(device);
    }

    public Device addUserToDeviceOrUpdateName(Device device, User user, String deviceName) {
        List<User> deviceUsers = device.getUsers();

        if (deviceUsers.contains(user)) {
            device.setName(deviceName);
            return deviceDao.save(device);
        }

        deviceUsers.add(user);
        device.setUsers(deviceUsers);
        return deviceDao.save(device);
    }

    public List<Device> getUserDevices(String username) {
        User user = userDao.findByUsername(username);
        return deviceDao.findAll().stream()
                .filter(device -> device.getUsers().contains(user))
                .collect(Collectors.toList());
    }

    public List<Device> getAllDevices() {
        return deviceDao.findAll();
    }

    public void forgetDevice(Long deviceId, String username) {
        User user = userDao.findByUsername(username);
        Device device = deviceDao.findAll().stream()
                .filter(d -> d.getId() == deviceId )
                .findFirst()
                .orElse(null);

        if(device != null) {
            List<User> deviceUsers = device.getUsers();
            deviceUsers.remove(user);
            device.setUsers(deviceUsers);
            deviceDao.save(device);
        }
    }
    public void delete(Long id) {
        deviceDao.deleteById(id);
    }
}
