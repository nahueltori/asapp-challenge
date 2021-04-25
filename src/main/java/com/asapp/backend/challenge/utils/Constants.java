package com.asapp.backend.challenge.utils;

public interface Constants {
    final static int TOKEN_LENGTH = 32;
    final static int TOKEN_DURATION = 60; //Minutes
    final static String appJson = "application/json";
    final static String databaseName = "chat.db";
    final static int HTTP_OK = 200;
    final static int HTTP_NOT_AUTH = 401;
    final static int HTTP_FORBIDDEN = 403;
}
