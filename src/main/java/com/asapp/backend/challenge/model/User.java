package com.asapp.backend.challenge.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asapp.backend.challenge.utils.Constants;
import com.asapp.backend.challenge.utils.Security;


public class User {
    private long id;
    private String username;
    private String password;
    private String passwordHash;
    private String salt;
    private String token;
    private LocalDateTime dateTokenCreated;
    private Collection<Message> messages;

    public User(){}

    public User(String jsonUser){
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            User newUser = mapper.readValue(jsonUser, User.class);
            setId(newUser.id);
            setUsername(newUser.username);
            setPassword(newUser.password);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        if(salt == null){
            salt = Security.generateSalt(512).get();
        }
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getDateTokenCreated() {
        return dateTokenCreated;
    }

    public void setDateTokenCreated(String dateTokenCreated) {
        if(dateTokenCreated != null){
            this.dateTokenCreated = LocalDateTime.parse(dateTokenCreated);
        }
    }

    public String getToken() {
        if( token == null ){
            renewToken();
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean verifyToken(String receivedToken){
        return this.token == receivedToken && !isTokenExpired();
    }

    public void renewToken(){
        token = Security.generateToken();
        this.dateTokenCreated = LocalDateTime.now();
        System.out.println("New token: " + token);
    }

    private boolean isTokenExpired(){
        return dateTokenCreated.plusMinutes(Constants.TOKEN_DURATION).isAfter(LocalDateTime.now());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void generatePasswordHash() {
        String hash = Security.hashPassword(password, getSalt()).get();
        setPasswordHash(hash);
        setPassword("");
    }

    public boolean verifyPassword(User otherUser){
        if(Security.verifyPassword(otherUser.password, getPasswordHash(), getSalt())){
            System.out.println("Password OK");
            setPassword("");
            return true;
        }
        System.out.println("Wrong password");
        return false;
    }

}
