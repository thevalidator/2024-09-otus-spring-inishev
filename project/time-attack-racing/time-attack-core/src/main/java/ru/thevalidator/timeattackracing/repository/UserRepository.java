package ru.thevalidator.timeattackracing.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.thevalidator.timeattackracing.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @EntityGraph(value = "users-roles")
    Optional<UserEntity> findByEmailIgnoreCase(@Size(max = 255) @NotNull String email);

    Optional<UserEntity> findById(UUID userId);

}