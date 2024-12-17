package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO;

import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRoles;

public record UserDTO(long id, String login, UserRoles role) {
}
