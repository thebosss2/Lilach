package org.entities;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class User extends Guest implements Serializable {

    @Column(name = " Identification")
    private int ID;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String password;
    private String email;
    private Date birth;
    private int balance=0;
    private Boolean connected;

    public User(int ID, String name, String userName, String password, String email, Date birth/*,Cart cart*/) {
        super(name/*,cart*/);
        this.ID=ID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birth = birth;
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

    public Date getBirth() {
        return birth;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
