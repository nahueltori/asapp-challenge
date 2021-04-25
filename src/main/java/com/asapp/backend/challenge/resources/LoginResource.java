package com.asapp.backend.challenge.resources;

public class LoginResource {
    private long id;
    private String token;

    public LoginResource(long id, String token){
        this.setId(id);
        this.setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
