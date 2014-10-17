
package com.fm.api.classes;

import java.math.BigInteger;
import java.security.SecureRandom;

public class LoginResponse {
    private SecureRandom random = new SecureRandom();
    private User user;
    private String status;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public User getUser(){
        return user;
    }
    
    public void setToken(){
        user.setToken(new BigInteger(130, random).toString(32));
    }
}
