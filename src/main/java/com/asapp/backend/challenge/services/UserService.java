package com.asapp.backend.challenge.services;

import java.util.Collection;

import com.asapp.backend.challenge.model.User;

public interface UserService {
    public User addUser (User user);
    public void updateUser (User user);
    
    public Collection<User> getUsers ();
    public User getUser (String username);
    
}
