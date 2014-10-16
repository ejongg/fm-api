
package com.fm.api.classes;

import java.math.BigInteger;
import java.security.SecureRandom;

public class LoginResponse {
    private SecureRandom random = new SecureRandom();
    private String token;
    private String status;
    
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
}
