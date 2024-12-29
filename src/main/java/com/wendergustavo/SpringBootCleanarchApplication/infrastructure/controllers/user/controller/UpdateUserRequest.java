package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller;

public record UpdateUserRequest(String login,String password , String role) {
}
