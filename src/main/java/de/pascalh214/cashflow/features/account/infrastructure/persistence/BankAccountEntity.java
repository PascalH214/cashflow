package de.pascalh214.cashflow.features.account.infrastructure.persistence;

import de.pascalh214.cashflow.features.country.infrastructure.persistence.CountryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@DiscriminatorValue("BANK")
@Data
public class BankAccountEntity extends AccountEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_alpha2", nullable = false)
    private CountryEntity country;

    @Column
    private Integer checkDigits;

    @Column
    private String basicBankAccountNumber;

    @Column(nullable = false)
    private String iban;

}
