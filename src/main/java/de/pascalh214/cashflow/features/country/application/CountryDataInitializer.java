package de.pascalh214.cashflow.features.country.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CountryDataInitializer {

    private final CountryImportService countryImportService;

    @Bean
    ApplicationRunner importCountryData() {
        return args -> countryImportService.importCountriesIfEmpty();
    }
}
