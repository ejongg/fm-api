
package com.fm.api.dao;

import java.sql.*;
import java.util.*;
import com.fm.api.classes.User;
import com.fm.api.utility.DBUtility;
import org.springframework.security.crypto.bcrypt.BCrypt;

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
                user.setPassword(rs.getString("password"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setStatus(rs.getString("status"));
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
                user.setPassword(rs.getString("password"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setStatus(rs.getString("status"));
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
                user.setPassword(rs.getString("password"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setStatus(rs.getString("status"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean login(String username, String password){
        try{
            String sql = "SELECT * from users where username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getString("username").equals(username) && BCrypt.checkpw(password, rs.getString("password"))){
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
    
    public User createUser(User user){
        User createdUser = user;
        try{
            String sql = "INSERT into users(username, password, type, first_name, last_name, status) values (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            String hashedpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            
            stmt.setString(1, user.getUserName());
            stmt.setString(2, hashedpw);
            stmt.setString(3, user.getType());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, "active");
            stmt.execute();
            
            createdUser = getUserByUsername(user.getUserName());
        }catch(Exception e){
            e.printStackTrace();
        }
        return createdUser;
    }
    
    public User editUser(User user){
        User updatedUser = user;
        try{
            String sql = "UPDATE users set type=?, first_name=?, last_name=? where username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
               
            stmt.setString(1, user.getType());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getUserName());
            stmt.executeUpdate();
            
            updatedUser = getUserByUsername(user.getUserName());
        }catch(Exception e){
            e.printStackTrace();
        }
        return updatedUser;
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
    
    public boolean changePassword(int id, String passwords){
        try{
            String[] password = passwords.split(",");
            String oldPassword = password[0].trim();
            String newPassword = password[1].trim();
            
            User user = getUserById(id);
            
            if(BCrypt.checkpw(oldPassword, user.getPassword())){
                
                String hashedpw = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                
                String sql = "UPDATE users set password=? where user_id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, hashedpw);
                stmt.setInt(2, id);
                stmt.executeUpdate();
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
    }
}
