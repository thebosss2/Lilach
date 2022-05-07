package org.entities;


import javax.persistence.*;
import java.util.LinkedList;

@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name = "catalog";
    @OneToMany
    protected static LinkedList<Product> products = new LinkedList<Product>();

    public static LinkedList<Product> getProducts() {
        return products;
    }


}
