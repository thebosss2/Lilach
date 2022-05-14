package org.entities;


import javax.persistence.*;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;         // id generated for each store

    @OneToOne
    private Employee storeManager;

    @ManyToOne
    private Employee[] employees;

    private String address;
    @ManyToOne
    private Order[] orders;

    public Store(Employee storeManager, Employee[] employees, String address) {
        this.storeManager = storeManager;
        employees = employees;
        this.address = address;
    }

    public Store() {

    }

    public Employee getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(Employee storeManager) {
        this.storeManager = storeManager;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        employees = employees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

}
