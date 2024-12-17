package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user;

import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.UserInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.exceptions.UserNotFoundException;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.UserDTO;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.DTO.UserDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRoles;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@Validated
public class UserController {

    private final UserInteractor userInteractor;
    private final UserDTOMapper userDTOMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserInteractor userInteractor, UserDTOMapper userDTOMapper) {
        this.userInteractor = userInteractor;
        this.userDTOMapper = userDTOMapper;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        logger.info("Iniciando criação de usuário com login: {}", request.login());
        try {
            UserRoles role;
            try {
                role = request.role() != null ? UserRoles.valueOf(request.role().toUpperCase()) : UserRoles.USER;
            } catch (IllegalArgumentException e) {
                logger.error("Role inválida: {}", request.role());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inválida: " + request.role());
            }

            User newUser = new User(null, request.login(), request.password(), role);
            logger.info("Objeto User criado: {}", newUser);

            User createdUser = userInteractor.createUser(newUser);
            logger.info("Usuário criado com sucesso no interactor: {}", createdUser);

            CreateUserResponse response = new CreateUserResponse(
                    createdUser.login(),
                    createdUser.role().name()
            );
            logger.info("Usuário mapeado para CreateUserResponse: {}", response);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResponseStatusException e) {
            logger.error("Erro de validação ao criar usuário: {}", e.getReason());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao criar usuário: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao criar usuário", e);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest request) {
        logger.info("Iniciando atualização do usuário com ID: {}", id);

        try {
            UserRoles role = UserRoles.valueOf(request.role().toUpperCase());

            User updatedUser = new User(id, request.login(), request.password(), role);

            User user = userInteractor.updateUser(id, updatedUser);

            logger.info("Usuário atualizado com sucesso. ID: {}", id);

            return ResponseEntity.ok(new UpdateUserResponse("Usuário atualizado com sucesso", user.id()));
        } catch (IllegalArgumentException e) {
            logger.error("Role inválida ao atualizar usuário: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inválida: " + request.role());
        } catch (UserNotFoundException e) {
            logger.error("Usuário não encontrado com ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar usuário: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao atualizar usuário", e);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Buscando todos os usuários...");
        List<User> users = userInteractor.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(userDTOMapper::toDTO)
                .collect(Collectors.toList());

        logger.info("Total de usuários encontrados: {}", userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable long id) {
        logger.info("Buscando usuário com ID: {}", id);

        User user = userInteractor.findById(id);

        UserDTO userDTO = userDTOMapper.toDTO(user);

        logger.info("Usuário encontrado: {}", userDTO);

        return ResponseEntity.ok(userDTO);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        logger.info("Iniciando exclusão do usuário com ID: {}", id);

        userInteractor.deleteUser(id);
        logger.info("Usuário com ID {} excluído com sucesso.", id);

        return ResponseEntity.noContent().build();
    }
}
