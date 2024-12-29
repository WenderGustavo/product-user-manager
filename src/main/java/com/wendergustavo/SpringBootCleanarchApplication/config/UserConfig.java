package com.wendergustavo.SpringBootCleanarchApplication.config;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.PasswordEncoderGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.UserGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.UserInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.UserDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper.UserEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.impl.UserRepositoryGateway;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    UserInteractor createUserCase(UserGateway userGateway, PasswordEncoderGateway passwordEncoder) {
        return new UserInteractor(userGateway ,passwordEncoder);
    }

    @Bean
    UserGateway userGateway(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        return new UserRepositoryGateway(userRepository, userEntityMapper);
    }


    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    UserDTOMapper userDTOMapper() {
        return new UserDTOMapper();
    }
}
