package de.pascalh214.cashflow.features.country.api.dtos;

import java.util.List;

public record CountryResponse(
        String alpha2Code,
        String name,
        List<FlagResponse> flags,
        List<CurrencyResponse> currencies
) {
    public record FlagResponse(
            String svg,
            String png
    ) {}

    public record CurrencyResponse(
            String code,
            String name,
            String symbol
    ) {}
}
