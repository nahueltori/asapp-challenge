package com.asapp.backend.challenge.resources;


public class UserResource {
    private long id;

    public UserResource(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
