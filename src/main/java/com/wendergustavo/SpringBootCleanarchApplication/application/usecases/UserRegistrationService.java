package com.wendergustavo.SpringBootCleanarchApplication.application.usecases;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.UserGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.PasswordEncoderGateway;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.user.UserRoles;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.InvalidUserFieldException;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.InvalidUserRoleException;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.LoginAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserGateway userGateway;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public UserRegistrationService(UserGateway userGateway, PasswordEncoderGateway passwordEncoderGateway) {
        this.userGateway = userGateway;
        this.passwordEncoderGateway = passwordEncoderGateway;
    }

    public User registerUser(User user) {
        validateUser(user);

        UserRoles role = (user.role() == null) ? UserRoles.USER : user.role();
        if (!isValidRole(role)) {
            throw new InvalidUserRoleException("O papel '" + role + "' é inválido.");
        }

        user = user.withPassword(passwordEncoderGateway.encode(user.password()));

        return userGateway.createUser(user);
    }

    private void validateUser(User user) {
        if (userGateway.existsByLogin(user.login())) {
            throw new LoginAlreadyExistsException("O login já está em uso.");
        }
        if (user.login() == null || user.login().isEmpty()) {
            throw new InvalidUserFieldException("O login não pode ser nulo ou vazio.");
        }
        if (user.password() == null || user.password().isEmpty()) {
            throw new InvalidUserFieldException("A senha não pode ser nula ou vazia.");
        }
    }

    private boolean isValidRole(UserRoles role) {
        return role == UserRoles.ADMIN || role == UserRoles.USER;
    }
}
