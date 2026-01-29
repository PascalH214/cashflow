package de.pascalh214.cashflow.features.country.api;

import de.pascalh214.cashflow.features.country.api.dtos.CountryResponse;
import de.pascalh214.cashflow.features.country.application.CountryResult;
import de.pascalh214.cashflow.features.country.application.CountryService;
import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.Currency;
import de.pascalh214.cashflow.features.country.domain.Flag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<Page<CountryResponse>> index(Pageable pageable) {
        Page<CountryResponse> response = countryService.listCountries(pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{alpha2Code}")
    public ResponseEntity<CountryResponse> show(@PathVariable String alpha2Code) {
        CountryResult result = countryService.getCountry(alpha2Code);
        if (result instanceof CountryResult.Failure) {
            return ResponseEntity.notFound().build();
        }
        Country country = ((CountryResult.Success) result).country();
        return ResponseEntity.ok(toResponse(country));
    }

    private CountryResponse toResponse(Country country) {
        List<CountryResponse.FlagResponse> flags = country.getFlags().stream()
                .map(this::toFlagResponse)
                .toList();
        List<CountryResponse.CurrencyResponse> currencies = country.getCurrencies().stream()
                .map(this::toCurrencyResponse)
                .toList();
        return new CountryResponse(
                country.getId().value(),
                country.getName(),
                flags,
                currencies
        );
    }

    private CountryResponse.FlagResponse toFlagResponse(Flag flag) {
        return new CountryResponse.FlagResponse(flag.svg(), flag.png());
    }

    private CountryResponse.CurrencyResponse toCurrencyResponse(Currency currency) {
        return new CountryResponse.CurrencyResponse(currency.code(), currency.name(), currency.symbol());
    }
}
