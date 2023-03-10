package agh.iot.services;

import agh.iot.entities.Device;
import agh.iot.entities.DeviceData;
import agh.iot.repositories.DeviceRepository;
import agh.iot.repositories.DeviceDataRepository;
import agh.iot.restmodels.requests.InsertDeviceDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceDataService {

    @Autowired
    DeviceDataRepository deviceDataDao;
    @Autowired
    DeviceRepository deviceDao;

    public DeviceData insertData(InsertDeviceDataRequest payload) {
        DeviceData deviceData = new DeviceData();
        deviceData.setDataInt(payload.getDataInt());
        deviceData.setEpochDate(payload.getEpochDate()*1000);
        Device device = null;
        try {
           device = deviceDao.findAll().stream()
                    .filter(d -> d.getGuid().equals(payload.getGuid()))
                    .findFirst()
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (device == null) {
            return null;
        }

        deviceData.setDevice(device);

        return deviceDataDao.save(deviceData);
    }

    public List<DeviceData> getDeviceData(long deviceId, int numberOfLastDataUpdates) {

        return deviceDataDao.findAll().stream()
                .filter(d -> d.getDevice().getId() == deviceId)
                .sorted(Comparator.comparingLong(DeviceData::getEpochDate)
                .reversed())
                .limit(numberOfLastDataUpdates)
                .collect(Collectors.toList());
    }
}
