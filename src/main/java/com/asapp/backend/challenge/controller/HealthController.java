package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.resources.HealthResource;
import com.asapp.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import com.asapp.backend.challenge.utils.Constants;

public class HealthController {

    public static Route check = (Request req, Response resp) -> {
        //TODO: Add check of database.
        resp.type(Constants.appJson);
        return JSONUtil.dataToJson(new HealthResource());
    };
}
