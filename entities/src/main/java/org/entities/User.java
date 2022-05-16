package org.entities;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class User extends Guest implements Serializable {

    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String password;
    private String email;
    private Date birth;
    private int balance=0;

    public User(String name, String userName, String password, String email, Date birth/*,Cart cart*/) {
        super(name/*,cart*/);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birth = birth;
        System.out.println("Hello there User!");
    }

    public User() {
        super();
        System.out.println("Hello there default User!");
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
