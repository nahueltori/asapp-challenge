package com.asapp.backend.challenge.resources;

public class HealthResource {
    private String health;

    public HealthResource(){
        health = "ok";
    }

    public String getHealth(){
        return health;
    }
    
    public void setHealth(String health){
        this.health = health;
    }
}
