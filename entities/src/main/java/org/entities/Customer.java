package org.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private enum AccountType{}
    private String creditCard;

    @OneToMany(targetEntity = Complaint.class, mappedBy = "customer")
    @Column(name = "order")
    protected List<Order> orders = new LinkedList<Order>();

    @OneToMany(targetEntity = Complaint.class, mappedBy = "customer")
    @Column(name = "complaint")
    private List<Complaint> complaints = new LinkedList<Complaint>();



    public Customer(String name, String userName, String password, String hashPassword, String email, Date birth, String creditCard) {
        super(name, userName, password, hashPassword, email, birth);
        this.creditCard = creditCard;
        this.complaints = complaints;
    }

    public Customer() {
        super();
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

}
