package de.pascalh214.cashflow.features.country.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
@Data
public class CountryEntity {

    @Id
    @Column(length = 2)
    private String alpha2Code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlagEntity> flags = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrencyEntity> currencies = new ArrayList<>();
}
