package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.user.UserEntity;

public class UserEntityMapper {

    public UserEntity toEntity(User userDomainObj) {
        return new UserEntity(
                null,
                userDomainObj.login(),
                userDomainObj.password(),
                userDomainObj.role()
        );
    }

    public User toDomainObj(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getLogin(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }
}
