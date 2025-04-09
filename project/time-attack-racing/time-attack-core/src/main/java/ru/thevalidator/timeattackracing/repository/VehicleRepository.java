package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.entity.VehicleEntity;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findAllByUser(UserEntity user);

}