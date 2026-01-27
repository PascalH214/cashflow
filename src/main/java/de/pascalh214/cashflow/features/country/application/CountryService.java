package de.pascalh214.cashflow.features.country.application;

import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public CountryResult getCountry(String alpha2Code) {
        if (alpha2Code == null || alpha2Code.isBlank()) {
            return new CountryResult.Failure("Country code is required");
        }
        CountryId id = new CountryId(alpha2Code.toUpperCase());
        Optional<Country> country = countryRepository.findById(id);
        return country.<CountryResult>map(CountryResult.Success::new)
                .orElseGet(() -> new CountryResult.Failure("Country not found"));
    }

    @Transactional(readOnly = true)
    public List<Country> listCountries() {
        return countryRepository.findAll();
    }
}
