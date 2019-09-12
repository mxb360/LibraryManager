package com.mxb.librarymanager.dataprocess;

public class UserInfo {
    public String number;
    public String name;
    public String password;
    public boolean isAdmin;
    public boolean canLend;
    
    public boolean isActive;
    public String userToken;
    public String college;
    public String imageUrl;

    public UserInfo() {

    }

    public UserInfo(String number, String password, boolean isAdmin) {
        this.number = number;
        this.isAdmin = isAdmin;
        this.password = password;
    }
}
