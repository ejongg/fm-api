
package com.fm.api.classes;

import java.math.BigInteger;
import java.security.SecureRandom;

public class LoginResponse {
    private SecureRandom random = new SecureRandom();
    private String token;
    private String status;
    private String username;
    private String firstname;
    private String lastname;
    private String type;
    
    public LoginResponse(){
        this.token = new BigInteger(130, random).toString(32);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
