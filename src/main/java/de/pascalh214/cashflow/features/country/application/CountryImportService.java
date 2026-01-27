package de.pascalh214.cashflow.features.country.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.CountryEntity;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.CurrencyEntity;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.FlagEntity;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.CountryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryImportService {

    private static final String COUNTRY_DATA_PATH = "static/country-data.json";

    private final CountryJpaRepository countryRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public int importCountries(boolean replaceExisting) {
        if (replaceExisting) {
            countryRepository.deleteAllInBatch();
        }

        List<CountryEntry> entries = readCountryEntries();
        List<CountryEntity> entities = entries.stream()
                .map(this::toEntity)
                .toList();
        countryRepository.saveAll(entities);
        return entities.size();
    }

    @Transactional
    public int importCountriesIfEmpty() {
        if (countryRepository.count() > 0) {
            return 0;
        }
        return importCountries(false);
    }

    private List<CountryEntry> readCountryEntries() {
        ClassPathResource resource = new ClassPathResource(COUNTRY_DATA_PATH);
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read country data", e);
        }
    }

    private CountryEntity toEntity(CountryEntry entry) {
        CountryEntity entity = new CountryEntity();
        entity.setAlpha2Code(entry.alpha2Code());
        entity.setName(entry.name());
        List<FlagEntity> flagEntities = toFlagEntities(entry.flags(), entity);
        entity.getFlags().clear();
        entity.getFlags().addAll(flagEntities);
        List<CurrencyEntry> currencies = entry.currencies() == null ? List.of() : entry.currencies();
        List<CurrencyEntity> currencyEntities = currencies.stream()
                .map(currency -> toCurrencyEntity(currency, entity))
                .toList();
        entity.getCurrencies().clear();
        entity.getCurrencies().addAll(currencyEntities);
        return entity;
    }

    private List<FlagEntity> toFlagEntities(FlagsEntry flags, CountryEntity country) {
        if (flags == null) {
            return List.of();
        }
        FlagEntity entity = new FlagEntity();
        entity.setSvg(flags.svg());
        entity.setPng(flags.png());
        entity.setCountry(country);
        return List.of(entity);
    }

    private CurrencyEntity toCurrencyEntity(CurrencyEntry currency, CountryEntity country) {
        CurrencyEntity entity = new CurrencyEntity();
        entity.setCode(currency.code());
        entity.setName(currency.name());
        entity.setSymbol(currency.symbol());
        entity.setCountry(country);
        return entity;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CountryEntry(
            String name,
            String alpha2Code,
            FlagsEntry flags,
            List<CurrencyEntry> currencies
    ) {}

    public record FlagsEntry(
            String svg,
            String png
    ) {}

    public record CurrencyEntry(
            String code,
            String name,
            String symbol
    ) {}
}
