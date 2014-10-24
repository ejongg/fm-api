
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
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from users");
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
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
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from users where user_id=" + id);
            while(rs.next()){
                user.setId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    public User getUserByUsername(String username){
        User user = new User();
        
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from users where username='" + username + "'");
            while(rs.next()){
                user.setId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean login(String username, String password){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from users where username='" + username + "'" );
            while(rs.next()){
                if(rs.getString("username").equals(username) && rs.getString("password").equals(password)){
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public void setSessionToken(String username, String token){
        try{
            String sql = "UPDATE users set token=? where username=? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void destroySessionToken(String username){
        try{
            String sql = "UPDATE users set token='' where username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean createUser(User user){
        try{
            String sql = "INSERT into users(username, password, type, first_name, last_name) values (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean editUser(User user){
        try{
            String sql = "UPDATE users set password=?, type=?, first_name=?, last_name=? where username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getType());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getUserName());
            stmt.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteUser(int id){
        try{
            String sql = "DELETE from users where user_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
