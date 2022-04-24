package org.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.*;

@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name = "catalog";
    @OneToMany
    protected static List<Product> products = new ArrayList<Product>();

    public static List<Product> getProducts(){
        return products;
    }



}
