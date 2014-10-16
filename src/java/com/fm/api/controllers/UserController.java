
package com.fm.api.controllers;

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
    public boolean login(@RequestBody UserLoginCredentials user){
        return userService.login(user.getUsername(), user.getPassword());
    }
}
