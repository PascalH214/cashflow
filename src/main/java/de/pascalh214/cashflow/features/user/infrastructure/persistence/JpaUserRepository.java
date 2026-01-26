package de.pascalh214.cashflow.features.user.infrastructure.persistence;

import de.pascalh214.cashflow.features.user.application.UserRepository;
import de.pascalh214.cashflow.features.user.domain.User;
import de.pascalh214.cashflow.features.user.domain.UserId;
import de.pascalh214.cashflow.features.user.domain.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity savedEntity = userJpaRepository.save(UserMapper.toUserEntity(user));
        return UserMapper.toUser(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findById(id.value())
                .map(UserMapper::toUser);
    }

}
