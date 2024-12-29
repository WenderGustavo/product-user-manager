package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user;

import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.UserInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.UserDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller.CreateUserRequest;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller.CreateUserResponse;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller.UserController;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.user.UserRoles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserInteractor userInteract;

    @Mock
    private UserDTOMapper userDTOMapper;

    @InjectMocks
    private UserController userController;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should return 201 Created when user is successfully created")
        void shouldReturnCreatedWhenUserIsSuccessfullyCreated() {

            var request = new CreateUserRequest("john_doe", "password123", "ADMIN");
            var user = new User(null, "john_doe", "password123", UserRoles.ADMIN);
            var createdUser = new User(1L, "john_doe", "password123", UserRoles.ADMIN);
            var response = new CreateUserResponse("john_doe", "ADMIN");

            when(userInteract.createUser(any(User.class))).thenReturn(createdUser);

            ResponseEntity<CreateUserResponse> result = userController.createUser(request);

            verify(userInteract, times(1)).createUser(any(User.class));
            assertNotNull(result, "Response should not be null");
            assertEquals(HttpStatus.CREATED, result.getStatusCode(), "HTTP status should be 201 Created");
            assertEquals(response, result.getBody(), "Response body should match the expected CreateUserResponse");
        }
        @Test
        @DisplayName("Should throw 400 Bad Request when input is invalid")
        void shouldThrowBadRequestWhenInputIsInvalid() {
            var invalidRequest = new CreateUserRequest("", "", "");

            // Verifique se uma ResponseStatusException é lançada
            var exception = assertThrows(ResponseStatusException.class,
                    () -> userController.createUser(invalidRequest));

            // Valide o status e a mensagem da exceção
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "HTTP status deve ser 400 BAD_REQUEST");
            assertEquals("Role inválida: ", exception.getReason(), "A mensagem de erro deve corresponder à esperada");

            // Certifique-se de que nenhum método foi chamado no userInteractor ou userDTOMapper
            verifyNoInteractions(userInteract);
            verifyNoInteractions(userDTOMapper);
        }
        @Test
        @DisplayName("Should return 500 Internal Server Error when use case throws exception")
        void shouldReturnInternalServerErrorWhenUseCaseThrowsException() {

            var request = new CreateUserRequest("john_doe", "password123", "ADMIN");


            when(userInteract.createUser(any(User.class)))
                    .thenThrow(new RuntimeException("Unexpected error"));


            var exception = assertThrows(ResponseStatusException.class,
                    () -> userController.createUser(request));

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode(),
                    "The status code should be 500 Internal Server Error");
            assertEquals("Erro inesperado ao criar usuário", exception.getReason(),
                    "The error message should match the exception reason set in the controller");

            // Verifica se o método do interactor foi chamado exatamente uma vez
            verify(userInteract, times(1)).createUser(any(User.class));
        }

        @Test
        @DisplayName("Should return 400 Bad Request for invalid role")
        void shouldReturnBadRequestForInvalidRole() {
            var invalidRequest = new CreateUserRequest("john_doe", "password123", "INVALID_ROLE");

            var exception = assertThrows(ResponseStatusException.class,
                    () -> userController.createUser(invalidRequest));

            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            assertEquals("Role inválida: INVALID_ROLE", exception.getReason());
        }
    }
}
