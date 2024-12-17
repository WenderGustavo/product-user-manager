package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.CreateUserRequest;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.CreateUserResponse;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRoles;

public class UserDTOMapper {

    public CreateUserResponse toResponse(User user) {
        return new CreateUserResponse(user.login(), user.role().name());
    }

    public User toUser(CreateUserRequest request) {
        UserRoles role = request.role() != null ? UserRoles.valueOf(request.role()) : UserRoles.USER;
        return new User(0L, request.login(), request.password(), role);
    }
    public UserDTO toDTO(User user) {
        return new UserDTO(user.id(), user.login(), user.role());
    }
}

