package ru.thevalidator.timeattackracing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.RoleEntity;
import ru.thevalidator.timeattackracing.entity.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(RoleName name);

}