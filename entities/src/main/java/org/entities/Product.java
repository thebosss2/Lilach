package org.entities;

import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javax.persistence.*;
import java.io.*;
import java.util.*;

@Entity
@Table(name = "poducts")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String name;
    //private Image image;
    @Column(name = "image", length = 6555500)
    private byte[] image;
    private double price;
    private double priceBeforeDiscount;

    /*public Product(Product product) {
        this.name=product.getName();
        //image=product.getImage();
        this.price=product.getPrice();
        this.priceBeforeDiscount=product.getPriceBeforeDiscount();
    }*/

    public Product(String name, String path, double price, double priceBeforeDiscount) {
        this.name = name;
        //image=image;
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;


        File file = new File(path);
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

    public Product(String name, byte[] image, double price, double priceBeforeDiscount) {
        this.name = name;
        //image=image;
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.image=image;


    }




    public Product() {

    }

/*    public Product() {

    }*/

/*    public Product() {
        this.name = "Sagi's flower";
        this.price = 500000000;
        this.priceBeforeDiscount = 500000000;

    }*/

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

    public Image getImage() {
        return new Image(new ByteArrayInputStream(this.image));
    }

    public byte[] getByteImage(){ return image;}

    /*public void setImage(Image image) {
        this.image = image;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
    }
}

