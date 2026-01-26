package de.pascalh214.cashflow.features.user.application;

import de.pascalh214.cashflow.features.user.domain.User;
import de.pascalh214.cashflow.features.user.domain.UserId;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UserId id);
}
