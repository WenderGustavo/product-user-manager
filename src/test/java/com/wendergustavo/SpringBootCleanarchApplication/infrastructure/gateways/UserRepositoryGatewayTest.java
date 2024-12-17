package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.impl.UserRepositoryGateway;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper.UserEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repository.user.UserEntity;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repository.user.UserRepository;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repository.user.UserRoles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryGatewayTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserRepositoryGateway userRepositoryGateway;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            // Arrange
            var input = new User(
                    1L,
                    "wender",
                    "1555",
                    UserRoles.ADMIN);

            var userEntity = new UserEntity(
                    1L,
                    "wender",
                    "1555",
                    UserRoles.ADMIN);

            when(userEntityMapper.toEntity(input)).thenReturn(userEntity);
            when(userRepository.save(userEntity)).thenReturn(userEntity);
            when(userEntityMapper.toDomainObj(userEntity)).thenReturn(input);

            var result = userRepositoryGateway.createUser(input);

            verify(userEntityMapper, times(1)).toEntity(input);
            verify(userRepository, times(1)).save(userEntity);
            verify(userEntityMapper, times(1)).toDomainObj(userEntity);

            assertNotNull(result, "O resultado não deveria ser nulo");
            assertEquals(input.login(), result.login(), "O nome de usuário deveria ser igual");
            assertEquals(input.role(), result.role(), "O role do usuário deveria ser igual");
        }

        @Test
        @DisplayName("Should throw exception when mapper fails during toEntity")
        void shouldThrowExceptionWhenMapperFails() {

            var input = new User(
                    1L,
                    "john_doe",
                    "password123",
                    UserRoles.ADMIN);

            when(userEntityMapper.toEntity(input)).thenThrow(new RuntimeException("Mapper error"));

            assertThrows(RuntimeException.class, () -> userRepositoryGateway.createUser(input));
            verify(userEntityMapper, times(1)).toEntity(input);
            verifyNoInteractions(userRepository);
        }

        @Test
        @DisplayName("Should throw exception when repository fails during save")
        void shouldThrowExceptionWhenRepositoryFails() {

            var input = new User(
                    1L,
                    "john_doe",
                    "password123",
                    UserRoles.ADMIN);

            var userEntity = new UserEntity(
                    1L,
                    "john_doe",
                    "password123",
                    UserRoles.ADMIN);

            when(userEntityMapper.toEntity(input)).thenReturn(userEntity);
            when(userRepository.save(userEntity)).thenThrow(new RuntimeException("Database error"));

            assertThrows(RuntimeException.class, () -> userRepositoryGateway.createUser(input));

            verify(userEntityMapper, times(1)).toEntity(input);
            verify(userRepository, times(1)).save(userEntity);
            verifyNoMoreInteractions(userEntityMapper, userRepository);
        }

        @Test
        @DisplayName("Should throw exception when repository returns null")
        void shouldThrowExceptionWhenRepositoryReturnsNull() {

            var input = new User(
                    1L,
                    "john_doe",
                    "password123",
                    UserRoles.ADMIN);

            var userEntity = new UserEntity(
                    1L,
                    "john_doe",
                    "password123",
                    UserRoles.ADMIN);

            when(userEntityMapper.toEntity(input)).thenReturn(userEntity);
            when(userRepository.save(userEntity)).thenReturn(null);
             
            assertThrows(IllegalStateException.class, () -> userRepositoryGateway.createUser(input));

            verify(userEntityMapper, times(1)).toEntity(input);
            verify(userRepository, times(1)).save(userEntity);
        }
    }
}
