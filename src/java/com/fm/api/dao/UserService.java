
package com.fm.api.dao;

import java.sql.*;
import java.util.*;
import com.fm.api.classes.User;
import com.fm.api.utility.DBUtility;

public class UserService {
    private Connection connection;
    
    public UserService(){
        connection = DBUtility.getConnection();
    }
    
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from users");
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("firstName"));
                users.add(user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
    
    public User getUserById(int id){
        User user = new User();
        
        try{  
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from users where userId=" + id);
            while(rs.next()){
                user.setId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    public User getUserByUsername(String username){
        User user = new User();
        
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from users where userName='" + username + "'");
            while(rs.next()){
                user.setId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean login(String username, String password){
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from users where userName='" + username + "'" );
            while(rs.next()){
                if(rs.getString("userName").equals(username) && rs.getString("password").equals(password)){
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
