package org.entities;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class User extends Guest implements Serializable {

    @Column(name = " Identification")
    private String userID;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String password;
    @Column(name="usermail")
    private String email;
    private String phoneNum;
    private int balance=0;
    private Boolean connected;

    public User(String userID, String name, String userName, String password, String email, String phoneNum) {
        super(name);
        this.userID=userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNum=phoneNum;
        this.connected=false;
    }

    public User() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getID() {
        return userID;
    }

    public void setID(String ID) {
        this.userID = userID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
