package com.asapp.backend.challenge.resources;

public class ErrorResource {
    private String description;

    public ErrorResource(String desc){
        this.description = desc;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
