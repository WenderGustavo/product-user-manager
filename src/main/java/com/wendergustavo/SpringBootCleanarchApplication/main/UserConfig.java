package com.wendergustavo.SpringBootCleanarchApplication.main;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.PasswordEncoderGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.UserGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.UserInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.UserDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.UserEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.UserRepositoryGateway;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
