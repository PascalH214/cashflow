package de.pascalh214.cashflow.features.user.infrastructure.persistence;

import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.time.Instant;
import java.util.*;

@Entity
@Data
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "user_accounts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<AccountEntity> accounts = new ArrayList<>();

}
