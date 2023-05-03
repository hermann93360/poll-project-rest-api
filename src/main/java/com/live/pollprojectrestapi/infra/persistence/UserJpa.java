package com.live.pollprojectrestapi.infra.persistence;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;
import com.live.pollprojectrestapi.domain.port.persistence.UserPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.UserEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class UserJpa implements UserPersistence {

    private final UserRepository userRepository;

    @Override
    public void createUser(User user) {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.fromString(user.getId()))
                .email(user.getEmail().getValue())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public User findByUserId(UserId userId) {
        UserEntity userEntity = userRepository.findById(userId.getValue()).orElseThrow();

        return User.builder()
                .id(userEntity.getId().toString())
                .email(Email.of(userEntity.getEmail()))
                .nickname(userEntity.getNickname())
                .username(userEntity.getUsername())
                .build();
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email.getValue());

        return userEntity.map(entity -> User.builder()
                .id(entity.getId().toString())
                .email(Email.of(entity.getEmail()))
                .nickname(entity.getNickname())
                .username(entity.getUsername())
                .build());
    }
}
