package de.pascalh214.cashflow.features.user.application;

import de.pascalh214.cashflow.features.user.domain.User;
import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void ensureUserExists(String userId) {
        UserId id;
        try {
            id = new UserId(UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            throw e;
        }

        Optional<User> foundUser = this.userRepository.findById(id);
        if (foundUser.isEmpty()) {
            this.userRepository.save(User.createUser(Instant.now(), id));
        }
    }

    @Transactional
    public UserInformationResult getUserInformation(String userId) {
        UserId id;
        try {
            id = new UserId(UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            return new UserInformationResult.Failure("Invalid user id");
        }

        Optional<User> foundUser = this.userRepository.findById(id);
        User user = foundUser.orElseGet(()
                -> this.userRepository.save(User.createUser(Instant.now(), id)));

        this.userRepository.save(user);
        return new UserInformationResult.Success(id);
    }

}
