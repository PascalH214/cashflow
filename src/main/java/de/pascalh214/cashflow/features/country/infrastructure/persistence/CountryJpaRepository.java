package de.pascalh214.cashflow.features.country.infrastructure.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryJpaRepository extends JpaRepository<CountryEntity, String> {
    @Override
    @EntityGraph(attributePaths = {"flags", "currencies"})
    java.util.Optional<CountryEntity> findById(String id);
    @Override
    @EntityGraph(attributePaths = {"flags", "currencies"})
    List<CountryEntity> findAll();
}
