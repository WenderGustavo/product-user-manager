package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO;

import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRoles;

public record UserResponseDTO(Long id, String username, UserRoles role) {
}