package de.pascalh214.cashflow.features.country.application;

import de.pascalh214.cashflow.features.country.domain.Country;

public sealed interface CountryResult permits CountryResult.Success, CountryResult.Failure {
    record Success(Country country) implements CountryResult {}
    record Failure(String message) implements CountryResult {}
}
