
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
    
    public void setSessionToken(String username, String token){
        try{
            String sql = "UPDATE users set token=? where userName=? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, username);
            statement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void destroySessionToken(String username){
        try{
            String sql = "UPDATE users set token='' where userName='"+username+"'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean createUser(User user){
        try{
            String sql = "INSERT into users(userName, password, type, firstName, lastName) values (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getType());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean editUser(User user){
        try{
            String sql = "UPDATE users set password=?, type=?, firstName=?, lastName=? where userName=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getType());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getUserName());
            statement.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteUser(String username){
        try{
            String sql = "DELETE from users where username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
