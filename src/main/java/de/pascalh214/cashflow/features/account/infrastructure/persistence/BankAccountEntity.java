package de.pascalh214.cashflow.features.account.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("BANK")
@Data
public class BankAccountEntity extends AccountEntity {

    @Column
    private String countryCode;

    @Column
    private Integer checkDigits;

    @Column
    private String basicBankAccountNumber;

}
