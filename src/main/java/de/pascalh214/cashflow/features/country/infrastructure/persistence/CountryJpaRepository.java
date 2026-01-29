package de.pascalh214.cashflow.features.country.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryJpaRepository extends JpaRepository<CountryEntity, String> {
    @Override
    @EntityGraph(attributePaths = {"flags"})
    java.util.Optional<CountryEntity> findById(String id);
    @Override
    @EntityGraph(attributePaths = {"flags"})
    Page<CountryEntity> findAll(Pageable pageable);
}
