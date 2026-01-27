package de.pascalh214.cashflow.features.country.application;

import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    Optional<Country> findById(CountryId id);
    List<Country> findAll();
}
