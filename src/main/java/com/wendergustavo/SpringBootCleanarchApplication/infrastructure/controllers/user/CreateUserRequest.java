package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user;

public record CreateUserRequest(String login, String password,String role){}
