package agh.iot.repositories;

import agh.iot.entities.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
}
