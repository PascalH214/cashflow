package de.pascalh214.cashflow.features.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = "accounts")
    Optional<UserEntity> findById(UUID id);

}
