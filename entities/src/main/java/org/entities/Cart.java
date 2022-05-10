package org.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    private List<Product> products = new LinkedList<>();
    private double totalCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart(){
        this.products = new LinkedList<Product>();
        this.totalCost = 0;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(double newCost) {
        this.totalCost = newCost;
    }

    public void removeProduct(int id) {
        for (int i=0; i<this.products.size();i++) {
            if (this.products.get(i).getId() == id) {
                totalCost -= this.products.get(i).getPrice();
                products.remove(i);
                break;
            }
        }
    }

    public void insertProduct(Product product){
        this.products.add(product);
        this.totalCost+= product.getPrice();
    }
}
