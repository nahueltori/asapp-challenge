package com.asapp.backend.challenge.services;

import java.util.Collection;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.asapp.backend.challenge.model.User;

public class UserServiceImpl implements UserService {
    SQLite database = new SQLite();
    Random rand = new Random();

    public long getLastUser(){
        String sql = "SELECT username, MAX(id) FROM persons;";
        long lastUser = 0;
        try (Connection conn = database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                lastUser = rs.getLong("MAX(id)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastUser;
    }

    @Override
    public User addUser(User user) {
        long newUserId = getLastUser() + 1;
        String sql = "INSERT INTO persons(id,username,password,salt) VALUES(?,?,?,?)";

        try (Connection conn = database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, newUserId);
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getSalt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("User created: " + String.valueOf(newUserId));
        user.setId(newUserId);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE persons SET username = ? , "
                + "password = ? , "
                + "salt = ? , "
                + "token = ? , "
                + "date_token_created = ? "
                + "WHERE id = ?";

        try (Connection conn = database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getSalt());
            pstmt.setString(4, user.getToken());
            pstmt.setString(5, user.getDateTokenCreated().toString());
            pstmt.setLong(6, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Collection<User> getUsers() {
        String sql = "SELECT id, username, password FROM persons;";
        System.out.println("Querying users");
        try (Connection conn = database.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                System.out.println(rs.getLong("id") +  "\t\t" + 
                                   rs.getString("username") + "\t\t" +
                                   rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        String sql = "SELECT id, password, salt, token, date_token_created FROM persons WHERE username = ?;";
        
        try (Connection conn = database.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            pstmt.setString(1,username);
            ResultSet rs  = pstmt.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setUsername(username);
                user.setPasswordHash(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
                user.setToken(rs.getString("token"));
                user.setDateTokenCreated(rs.getString("date_token_created"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
