package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.service;

import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserEntity;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository repository;

    public AuthorizationService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(
                userEntity.getLogin(),
                userEntity.getPassword(),
                userEntity.getAuthorities()
        );
    }
}
