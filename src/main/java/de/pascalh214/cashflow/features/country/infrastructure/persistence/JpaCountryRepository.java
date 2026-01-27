package de.pascalh214.cashflow.features.country.infrastructure.persistence;

import de.pascalh214.cashflow.features.country.application.CountryRepository;
import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import de.pascalh214.cashflow.features.country.domain.CountryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaCountryRepository implements CountryRepository {

    private final CountryJpaRepository countryJpaRepository;

    @Override
    public Optional<Country> findById(CountryId id) {
        return countryJpaRepository.findById(id.value())
                .map(CountryMapper::toCountry);
    }

    @Override
    public List<Country> findAll() {
        return countryJpaRepository.findAll().stream()
                .map(CountryMapper::toCountry)
                .toList();
    }
}
