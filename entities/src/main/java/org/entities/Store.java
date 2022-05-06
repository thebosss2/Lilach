package org.entities;

public class Store {
    //private Employee storeManager;
    //private Employee[] Employees;
    private String address;
    private Order orders;
    private Report[] reports;

    /*public Store(Employee storeManager, Employee[] employees, String address) {
        this.storeManager = storeManager;
        Employees = employees;
        this.address = address;
    }

    public Employee getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(Employee storeManager) {
        this.storeManager = storeManager;
    }

    public Employee[] getEmployees() {
        return Employees;
    }

    public void setEmployees(Employee[] employees) {
        Employees = employees;
    }*/

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    public Report[] getReports() {
        return reports;
    }

    public void setReports(Report[] reports) {
        this.reports = reports;
    }
}
