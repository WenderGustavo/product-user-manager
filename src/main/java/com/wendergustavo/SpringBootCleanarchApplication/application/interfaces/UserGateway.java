package com.wendergustavo.SpringBootCleanarchApplication.application.interfaces;


import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {
    User createUser(User user);
    boolean deleteUser(long id);
    Optional<User> updateUser(long id, User updatedUser);
    List<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}

