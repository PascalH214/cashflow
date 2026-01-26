package de.pascalh214.cashflow.features.account.infrastructure.persistence;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("CREDIT_CARD")
@Data
public class CreditCardEntity extends AccountEntity {

    private String cardNumber;

}
