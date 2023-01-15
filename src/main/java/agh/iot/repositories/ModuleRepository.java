package agh.iot.repositories;

import agh.iot.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModuleRepository extends JpaRepository<Module, Long> {
    Module findById(long id);

}
