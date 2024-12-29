package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.user.controller;

public record CreateUserRequest(String login, String password,String role){}
