package org.entities;

import javafx.scene.image.Image;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {      //Product class entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;         // id generated for each product
    @Column(name = "product_name")
    private String name;
    //private Image image;
    @Column(name = "image", length = 65555)
    private byte[] image;
    private int price;
    private int priceBeforeDiscount;

    public Product(String name, String path, int price, int priceBeforeDiscount) {      //constructor
        this.name = name;
        //image=image;
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;


        File file = new File(path);         //converts string pth into bytecode image
        this.image = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            //convert file into array of bytes
            fileInputStream.read(this.image);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product(String name, byte[] image, int price, int priceBeforeDiscount) {
        this.name = name;
        //image=image;
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.image = image;


    }

    public Product() {

    }

    //getters and setters

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(int priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    public Image getImage() {
        return new Image(new ByteArrayInputStream(this.image));
    }

    public byte[] getByteImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}

