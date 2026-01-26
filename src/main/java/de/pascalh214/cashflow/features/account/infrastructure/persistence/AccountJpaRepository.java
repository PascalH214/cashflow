package de.pascalh214.cashflow.features.account.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, java.util.UUID> {
}
