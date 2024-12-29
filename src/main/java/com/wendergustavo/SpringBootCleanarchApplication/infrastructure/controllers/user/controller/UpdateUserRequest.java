package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controllers;

public record UpdateUserRequest(String login,String password , String role) {
}
