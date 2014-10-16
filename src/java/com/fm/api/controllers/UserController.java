
package com.fm.api.controllers;

import com.fm.api.classes.LoginResponse;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fm.api.classes.User;
import com.fm.api.dao.UserService;
import com.fm.api.classes.UserLoginCredentials;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("user")
public class UserController {
    UserService userService = new UserService();
    
    @RequestMapping(method = RequestMethod.GET,headers="Accept=application/json")
     public List<User> getAllUsers() {
      List<User> users=userService.getAllUsers();
      return users;
     }
         
    @RequestMapping(value="/{id}", method=RequestMethod.GET, headers="Accept=application/json")
    public User getUserById(@PathVariable int id){
        User user = userService.getUserById(id);
        return user;
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public LoginResponse login(@RequestBody UserLoginCredentials user){
        
        boolean loginResult = userService.login(user.getUsername(), user.getPassword());
        User userInfo = userService.getUserByUsername(user.getUsername());
        
        if(loginResult == true){
            LoginResponse response = new LoginResponse();
            response.setStatus("OK");
            response.setUsername(userInfo.getUserName());
            response.setFirstname(userInfo.getFirstName());
            response.setLastname(userInfo.getLastName());
            response.setType(userInfo.getType());
            return response;
        }else{
            LoginResponse response = new LoginResponse();
            response.setStatus("ERROR");
            response.setToken(null);
            return response;
        }
    }
}
