package com.wendergustavo.SpringBootCleanarchApplication.application.interfaces;

public interface PasswordEncoderGateway {
    String encode(String rawPassword);
}
