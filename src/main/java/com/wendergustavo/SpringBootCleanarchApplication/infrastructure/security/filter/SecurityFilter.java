package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.security.filter;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.UserGateway;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.User;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.security.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserGateway userGateway;

    public SecurityFilter(TokenService tokenService, UserGateway userGateway) {
        this.tokenService = tokenService;
        this.userGateway = userGateway;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = recoveToken(request);

        if (token != null) {
            try {
                var login = tokenService.validateToken(token);
                if (login == null) {
                    handleUnauthorized(response, "Token inválido ou expirado.");
                    return;
                }

                Optional<User> userOptional = userGateway.findByLogin(login);
                if (userOptional.isEmpty()) {
                    handleUnauthorized(response, "Usuário não encontrado.");
                    return;
                }

                User user = userOptional.get();
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.role()));
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logSuccessAuthentication(user);

            } catch (Exception e) {
                handleForbidden(response, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoveToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || (!authHeader.startsWith("Bearer ") && !authHeader.startsWith("Bearer:"))) {
            return null;
        }
        return authHeader.replaceFirst("Bearer[:]*\\s*", "").trim();
    }

    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

    private void handleForbidden(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Acesso negado: " + message);
    }

    private void logSuccessAuthentication(User user) {
        System.out.println("Autenticação bem-sucedida:");
        System.out.println("Usuário: " + user.login());
        System.out.println("Papel: " + user.role());
    }
}
