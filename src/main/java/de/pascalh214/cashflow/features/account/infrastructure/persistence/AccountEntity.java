package de.pascalh214.cashflow.features.account.infrastructure.persistence;

import de.pascalh214.cashflow.features.account.domain.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AccountEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column
    private String countryCode;

    @Column
    private Integer checkDigits;

    @Column
    private String basicBankAccountNumber;

}
