package agh.iot.repositories;

import agh.iot.models.ModuleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ModuleDataRepository extends JpaRepository<ModuleData, Long> {
}
