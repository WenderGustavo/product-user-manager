package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user;

import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.AuthenticationDTO;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.LoginResponseDTO;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller.AuthenticationController;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should return 200 OK and token when login is successful")
    void shouldReturnOkAndTokenWhenLoginIsSuccessful() {
        // Arrange
        var authenticationDTO = new AuthenticationDTO("admin", "password123");
        var authenticationToken = new UsernamePasswordAuthenticationToken("admin", "password123");

        var mockUser = new User("admin", "password123", new ArrayList<>()); // Lista vazia
        var mockAuthentication = mock(Authentication.class);

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn("jwt-token");

        ResponseEntity<LoginResponseDTO> response = authenticationController.login(authenticationDTO);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("jwt-token", response.getBody().token(), "Token should match expected value");

        verify(authenticationManager, times(1)).authenticate(authenticationToken);
        verify(tokenService, times(1)).generateToken(mockUser);
    }

    @Test
    @DisplayName("Should return 500 Internal Server Error when unexpected error occurs")
    void shouldReturnInternalServerErrorWhenUnexpectedErrorOccurs() {
        var authenticationDTO = new AuthenticationDTO("admin", "password123");
        var authenticationToken = new UsernamePasswordAuthenticationToken("admin", "password123");

        when(authenticationManager.authenticate(authenticationToken))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authenticationController.login(authenticationDTO));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode(), "HTTP status should be 500 Internal Server Error");
        assertEquals("Unexpected error occurred", exception.getReason(), "Error message should match");
        verify(authenticationManager, times(1)).authenticate(authenticationToken);
        verifyNoInteractions(tokenService);
    }

}
