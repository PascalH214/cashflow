package de.pascalh214.cashflow.features.country.infrastructure.persistence;

import de.pascalh214.cashflow.features.country.application.CountryRepository;
import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import de.pascalh214.cashflow.features.country.domain.CountryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
    public Page<Country> findAll(Pageable pageable) {
        return countryJpaRepository.findAll(pageable)
                .map(CountryMapper::toCountry);
    }
}
