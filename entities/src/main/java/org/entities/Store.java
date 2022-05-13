package org.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;         // id generated for each store

    @OneToOne
    private Employee storeManager;

    @OneToMany
    private List<Employee> employees;

    private String address;

    @OneToMany
    @Column(name="stores_orders")
    private List<Order> orders;

    public Store(Employee storeManager, List<Employee> employees, String address) {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        employees = employees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
