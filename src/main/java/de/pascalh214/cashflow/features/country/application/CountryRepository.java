package de.pascalh214.cashflow.features.country.application;

import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CountryRepository {
    Optional<Country> findById(CountryId id);
    Page<Country> findAll(Pageable pageable);
}
