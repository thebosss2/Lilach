package org.entities;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class User extends Guest{

    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String password;
    private String hashPassword;
    private String email;
    private Date birth;
    private int balance=0;

    public User(String name, String userName, String password, String hashPassword, String email, Date birth/*,Cart cart*/) {
        super(name/*,cart*/);
        this.userName = userName;
        this.password = password;
        this.hashPassword = hashPassword;
        this.email = email;
        this.birth = birth;
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

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
