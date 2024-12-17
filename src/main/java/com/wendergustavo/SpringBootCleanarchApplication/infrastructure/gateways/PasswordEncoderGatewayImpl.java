package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.PasswordEncoderGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderGatewayImpl implements PasswordEncoderGateway {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderGatewayImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
