package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.model.User;
import com.asapp.backend.challenge.resources.UserResource;
import com.asapp.backend.challenge.services.UserService;
import com.asapp.backend.challenge.services.UserServiceImpl;
import com.asapp.backend.challenge.utils.Constants;
import com.asapp.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class UsersController {
    final static UserService userService = new UserServiceImpl();

    public static Route createUser = (Request req, Response resp) -> {
        User newUser = new User(req.body());
        newUser.generatePasswordHash();
        newUser = userService.addUser(newUser);
        
        resp.type(Constants.appJson);
        return JSONUtil.dataToJson(new UserResource(newUser.getId()));
    };

    public static Route listUsers = (Request req, Response resp) -> {
        userService.getUsers();
        resp.type(Constants.appJson);
        
        return "{\"See\": \"you\"}";
    };
}
