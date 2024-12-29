package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller;

import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.UserRegistrationService;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.*;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.user.UserRoles;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRegistrationService userRegistrationService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserRegistrationService userRegistrationService,
                                    TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            logger.info("Attempting to authenticate user with login: {}", data.login());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(data.login(), data.password());

            var authentication = authenticationManager.authenticate(authenticationToken);

            var token = tokenService.generateToken((UserDetails) authentication.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            logger.error("Login failed: Invalid login or password");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login or password", e);
        } catch (Exception e) {
            logger.error("Unexpected error during login: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid RegisterDTO data) {
        logger.info("Creating new ADMIN with login: {}", data.login());

        UserRoles role;
        try {
            role = data.role() != null ? UserRoles.valueOf(data.role().toUpperCase()) : UserRoles.USER;
        } catch (IllegalArgumentException e) {
            logger.error("Role inválida: {}", data.role());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inválida: " + data.role());
        }

        User newUser = User.withoutId(data.login(), data.password(), role);
        logger.info("Objeto User criado: {}", newUser);

        User savedUser = userRegistrationService.registerUser(newUser);
        logger.info("ADMIN user created successfully with ID: {}", savedUser.id());

        UserResponseDTO responseDTO = new UserResponseDTO(savedUser.id(), savedUser.login(), savedUser.role());
        logger.info("Usuário mapeado para CreateUserResponse: {}", responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    }
