package agh.iot.Repository;

import agh.iot.Models.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
