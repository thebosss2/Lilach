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
    private int price;
    private int priceBeforeDiscount;

    public Product(String name, String path, int price, int priceBeforeDiscount) {
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

    public Product(String name, byte[] image, int price, int priceBeforeDiscount) {
        this.name = name;
        //image=image;
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.image=image;


    }




    public Product() {

    }

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

