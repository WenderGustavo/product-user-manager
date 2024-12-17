package com.wendergustavo.SpringBootCleanarchApplication.application.usecases;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.PasswordEncoderGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.UserGateway;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.UserAlreadyExistsException;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.UserNotFoundException;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.UserUpdateException;

import java.util.List;

public class  UserInteractor {

    private final UserGateway userGateway;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public UserInteractor(UserGateway userGateway, PasswordEncoderGateway passwordEncoderGateway) {
        this.userGateway = userGateway;
        this.passwordEncoderGateway = passwordEncoderGateway;
    }

    public User createUser(User user) {
        if (userGateway.findByLogin(user.login()).isPresent()) {
            throw new UserAlreadyExistsException("Já existe um usuário com este login.");
        }

        String encodedPassword = passwordEncoderGateway.encode(user.password());
        User userToSave = user.withPassword(encodedPassword);

        return userGateway.createUser(userToSave);
    }

    public void deleteUser(long id) {
        User user = userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com ID " + id + " não encontrado."));

        userGateway.deleteUser(user.id());
    }

    public User updateUser(long id, User updatedUser) {
        User existingUser = userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com ID " + id + " não encontrado."));

        User userToSave = existingUser.updateWith(
                updatedUser.login(),
                updatedUser.password() != null ? passwordEncoderGateway.encode(updatedUser.password()) : null,
                updatedUser.role()
        );

        return userGateway.updateUser(id, userToSave)
                .orElseThrow(() -> new UserUpdateException("Erro ao salvar o usuário no banco de dados."));
    }

    public User findById(long id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com ID " + id + " não encontrado."));
    }

    public List<User> findAll() {
        List<User> users = userGateway.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Nenhum usuário encontrado.");
        }
        return users;
    }

}
