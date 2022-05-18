package org.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    public enum AccountType{STORE, CHAIN, MEMBERSHIP}
    private AccountType accountType;
    private String creditCard;
    ///////////////////////////////////////////////Past Orders
    ///////////////////////////////////////////////private Store store;
    @OneToMany(targetEntity = Complaint.class, mappedBy = "customer")
    @Column(name = "order")
    protected List<Order> orders = new LinkedList<Order>();

    @OneToMany(targetEntity = Complaint.class, mappedBy = "customer")
    @Column(name = "complaint")
    private List<Complaint> complaints = new LinkedList<Complaint>();

    public Customer(String userID, String name, String userName, String password, String email, String phone, String creditCard, AccountType accountType) {
        super(userID, name, userName, password, email, phone);
        this.creditCard = creditCard;
        this.accountType = accountType;
        //TODO add hashing to password if have time.
    }

    public Customer() {
        super();
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
