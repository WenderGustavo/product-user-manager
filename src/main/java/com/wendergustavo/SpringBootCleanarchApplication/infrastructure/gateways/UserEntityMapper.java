package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserEntity;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRoles;

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
