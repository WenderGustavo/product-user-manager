package com.wendergustavo.SpringBootCleanarchApplication.domain.entity;

import com.wendergustavo.SpringBootCleanarchApplication.exceptions.InvalidArgumentDomainException;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRoles;

public record User(Long id, String login, String password, UserRoles role) {

    public static final String LOGIN_INVALIDO = "Campo 'login' não pode ser nulo ou vazio.";
    public static final String SENHA_INVALIDA = "A senha deve ter pelo menos 8 caracteres.";
    public static final String ROLE_INVALIDO = "Campo 'role' é inválido.";

    public User {
        validate(login, password, role);
    }

    public static User withoutId(String login, String password, UserRoles role) {
        return new User(null, login, password, role);
    }

    public User withPassword(String newPassword) {
        validatePassword(newPassword);
        return new User(id, login, newPassword, role);
    }

    public User updateWith(String newLogin, String newPassword, UserRoles newRole) {
        String finalLogin = (newLogin != null && !newLogin.isBlank()) ? newLogin : this.login;
        String finalPassword = (newPassword != null && !newPassword.isBlank()) ? newPassword : this.password;
        UserRoles finalRole = (newRole != null) ? newRole : this.role;

        return new User(this.id, finalLogin, finalPassword, finalRole);
    }

    private void validate(String login, String password, UserRoles role) {
        if (login == null || login.isBlank()) {
            throw new InvalidArgumentDomainException(LOGIN_INVALIDO);
        }
        validatePassword(password);
        if (role == null) {
            throw new InvalidArgumentDomainException(ROLE_INVALIDO);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank() || password.length() < 8) {
            throw new InvalidArgumentDomainException(SENHA_INVALIDA);
        }
    }
}
