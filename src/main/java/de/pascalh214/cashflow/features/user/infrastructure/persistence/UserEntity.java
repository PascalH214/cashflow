package de.pascalh214.cashflow.features.user.infrastructure.persistence;

import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.Instant;
import java.util.*;

@Entity
@Data
public class UserEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    private List<AccountEntity> accounts = new ArrayList<>();

}
