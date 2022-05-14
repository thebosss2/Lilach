package org.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


public class Cart {

    private List<Product> products = new LinkedList<>();
    private double totalCost;

    public Cart() {
        this.products = new LinkedList<Product>();
        this.totalCost = 0;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setTotalCost(double newCost) {
        this.totalCost = newCost;
    }

    public void removeProduct(int id) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getId() == id) {
                totalCost -= this.products.get(i).getPrice();
                products.remove(i);
                break;
            }
        }
    }

    public void insertProduct(Product product) {
        this.products.add(product);
        this.totalCost += product.getPrice();
    }
}