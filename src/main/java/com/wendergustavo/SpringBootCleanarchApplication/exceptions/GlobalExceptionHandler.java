package com.wendergustavo.SpringBootCleanarchApplication.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String message, String details) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message, details));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        logger.error("Produto não encontrado: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), "Verifique o ID do produto.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("Usuário não encontrado: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), "Verifique o ID ou credenciais do usuário.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        logger.error("Erro de validação: {}", errors);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Erro de validação", errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        logger.warn("Acesso negado: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.FORBIDDEN, "Acesso negado.", "Verifique suas permissões.");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String message = "Rota não encontrada: " + ex.getRequestURL();
        logger.warn(message);
        return buildResponseEntity(HttpStatus.NOT_FOUND, message, "Certifique-se de que a URL está correta.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        logger.error("Erro interno inesperado: ", ex); // Stack trace completo no log
        return buildResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor.",
                ex.getMessage() != null ? ex.getMessage() : "Detalhes não disponíveis."
        );

    }

}
