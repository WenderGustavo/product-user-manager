package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(userDetails.getUsername())
                    .withClaim("roles", userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) throws ServletException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm).withIssuer("auth-api").build();
            var decodedJWT = verifier.verify(token);

            Instant expiresAt = decodedJWT.getExpiresAt().toInstant();
            if (expiresAt.isBefore(Instant.now())) {
                throw new ServletException("Token expirado");
            }

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new ServletException("Token inválido ou expirado: " + exception.getMessage(), exception);
        }
    }

    public List<String> getRolesFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm).withIssuer("auth-api").build();
            var decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("roles").asList(String.class);
        } catch (JWTVerificationException exception) {
            return List.of();
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}
