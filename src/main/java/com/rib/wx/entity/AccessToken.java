package com.rib.wx.entity;

public class AccessToken {
    //获得到的凭证  
    private String token;  
    //凭证有效期，单位：秒  
    private int expiresIn;  
      
    public String getToken() {  
        return token;  
    }  
    public void setToken(String token) {  
        this.token = token;  
    }  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}
