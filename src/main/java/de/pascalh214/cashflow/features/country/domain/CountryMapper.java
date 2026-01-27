package de.pascalh214.cashflow.features.country.domain;

import de.pascalh214.cashflow.features.country.infrastructure.persistence.CountryEntity;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.CurrencyEntity;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.FlagEntity;

import java.util.List;

public class CountryMapper {

    public static Country toCountry(CountryEntity entity) {
        List<Flag> flags = entity.getFlags() == null
                ? List.of()
                : entity.getFlags().stream()
                .map(CountryMapper::toFlag)
                .toList();
        List<Currency> currencies = entity.getCurrencies() == null
                ? List.of()
                : entity.getCurrencies().stream()
                .map(CountryMapper::toCurrency)
                .toList();
        return Country.rehydrate(
                new CountryId(entity.getAlpha2Code()),
                entity.getName(),
                flags,
                currencies
        );
    }

    public static CountryEntity toCountryEntity(Country country) {
        CountryEntity entity = new CountryEntity();
        entity.setAlpha2Code(country.getId().value());
        entity.setName(country.getName());
        List<FlagEntity> flags = country.getFlags() == null
                ? List.of()
                : country.getFlags().stream()
                .map(flag -> toFlagEntity(flag, entity))
                .toList();
        List<CurrencyEntity> currencies = country.getCurrencies() == null
                ? List.of()
                : country.getCurrencies().stream()
                .map(currency -> toCurrencyEntity(currency, entity))
                .toList();
        entity.getFlags().clear();
        entity.getFlags().addAll(flags);
        entity.getCurrencies().clear();
        entity.getCurrencies().addAll(currencies);
        return entity;
    }

    private static Flag toFlag(FlagEntity entity) {
        return new Flag(entity.getSvg(), entity.getPng());
    }

    private static Currency toCurrency(CurrencyEntity entity) {
        return new Currency(entity.getCode(), entity.getName(), entity.getSymbol());
    }

    private static FlagEntity toFlagEntity(Flag flag, CountryEntity country) {
        FlagEntity entity = new FlagEntity();
        entity.setSvg(flag.svg());
        entity.setPng(flag.png());
        entity.setCountry(country);
        return entity;
    }

    private static CurrencyEntity toCurrencyEntity(Currency currency, CountryEntity country) {
        CurrencyEntity entity = new CurrencyEntity();
        entity.setCode(currency.code());
        entity.setName(currency.name());
        entity.setSymbol(currency.symbol());
        entity.setCountry(country);
        return entity;
    }
}
