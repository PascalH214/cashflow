package de.pascalh214.cashflow.features.country.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "currencies")
@Data
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16)
    private String code;

    @Column
    private String name;

    @Column
    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CountryEntity country;
}
