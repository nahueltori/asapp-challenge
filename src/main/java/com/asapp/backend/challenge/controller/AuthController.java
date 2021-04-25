package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.model.User;
import com.asapp.backend.challenge.resources.ErrorResource;
import com.asapp.backend.challenge.resources.LoginResource;
import com.asapp.backend.challenge.services.UserService;
import com.asapp.backend.challenge.services.UserServiceImpl;
import com.asapp.backend.challenge.utils.Constants;
import com.asapp.backend.challenge.utils.JSONUtil;
import spark.*;

public class AuthController {
    final static UserService userService = new UserServiceImpl();

    public static Route login = (Request req, Response resp) -> {
        ErrorResource usrPwdError = new ErrorResource("Incorrect User or Passowrd");
        User userReceived = new User(req.body());
        System.out.println("User logging: " + userReceived.getUsername());
        User databaseUser = userService.getUser(userReceived.getUsername());
        if(databaseUser == null){
            resp.status(Constants.HTTP_FORBIDDEN);
            return JSONUtil.dataToJson(usrPwdError);
        }
        
        //Check password received
        if(databaseUser.verifyPassword(userReceived)){
            databaseUser.renewToken();
            userService.updateUser(databaseUser);
            resp.status(Constants.HTTP_OK);
        }
        else{
            resp.status(Constants.HTTP_FORBIDDEN);
            return JSONUtil.dataToJson(usrPwdError);
        }
        resp.type(Constants.appJson);

        return JSONUtil.dataToJson(new LoginResource(databaseUser.getId(), databaseUser.getToken()));
    };

}
