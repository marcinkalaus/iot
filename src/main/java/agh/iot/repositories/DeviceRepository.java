package agh.iot.repositories;

import agh.iot.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>  {
    Device findById(long id);
    void deleteById(long id);
}
