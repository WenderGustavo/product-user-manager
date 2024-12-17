package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.UserGateway;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserEntity;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class UserRepositoryGateway implements UserGateway {

    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;

    public UserRepositoryGateway(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User createUser(User userDomainObj) {
        UserEntity userEntity = userEntityMapper.toEntity(userDomainObj);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityMapper.toDomainObj(savedEntity);
    }

    @Override
    public boolean deleteUser(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> updateUser(long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingEntity -> {
                    existingEntity.setLogin(updatedUser.login());
                    existingEntity.setPassword(updatedUser.password());
                    existingEntity.setRole(updatedUser.role());
                    UserEntity updatedEntity = userRepository.save(existingEntity);
                    return userEntityMapper.toDomainObj(updatedEntity);
                });
    }

    @Override
    public List<User> findAll() {
        Iterable<UserEntity> userEntities = userRepository.findAll();
        return StreamSupport.stream(userEntities.spliterator(), false)
                .map(userEntityMapper::toDomainObj)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toDomainObj);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login))
                .map(userEntityMapper::toDomainObj);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }
}
