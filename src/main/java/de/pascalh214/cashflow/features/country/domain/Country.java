package de.pascalh214.cashflow.features.country.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Country {

    private final CountryId id;
    private final String name;
    private final List<Flag> flags;
    private final List<Currency> currencies;

    private Country(
            CountryId id,
            String name,
            List<Flag> flags,
            List<Currency> currencies
    ) {
        this.id = id;
        this.name = name;
        this.flags = flags;
        this.currencies = currencies;
    }

    public static Country rehydrate(
            CountryId id,
            String name,
            List<Flag> flags,
            List<Currency> currencies
    ) {
        return new Country(id, name, flags, currencies);
    }
}
