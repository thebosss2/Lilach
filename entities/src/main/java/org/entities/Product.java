package org.entities;

import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javax.persistence.*;
import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "poducts")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String name;
    //private Image image;
    @Column(name = "image")
    private String image;
    private double price;
    private double priceBeforeDiscount;

    public Product(Product product) {
        this.name=product.getName();
        //image=product.getImage();
        this.price=product.getPrice();
        this.priceBeforeDiscount=product.getPriceBeforeDiscount();
    }

    public Product(String name, String image, double price, double priceBeforeDiscount) {
        this.name=name;
        //image=image;
        this.image=image;
        this.price=price;
        this.priceBeforeDiscount=priceBeforeDiscount;
    }

    public Product() {

    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    public double getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(double priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

/*    public Image getImage() {
        return image;
    }*/

    /*public void setImage(Image image) {
        this.image = image;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

