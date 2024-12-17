package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user;

public record UpdateUserRequest(String login,String password , String role) {
}
