package org.entities;

import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javax.persistence.*;
import java.io.*;
import java.util.*;

@MappedSuperclass
public class Product implements Serializable {      //Product class entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;         // id generated for each product
    //private Image image;
    @Column(name = "image", length = 65555)
    protected byte[] image;
    protected int price;
    //protected int priceBeforeDiscount;


    public Product(String path, int price) {      //constructor

        //image=image;
        this.price = price;
        //this.priceBeforeDiscount = priceBeforeDiscount;


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

    public Product( byte[] image, int price) {

        //image=image;
        this.price = price;
        //this.priceBeforeDiscount = priceBeforeDiscount;
        this.image=image;


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


    /*public int getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(int priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }
*/
    public Image getImage() {
        return new Image(new ByteArrayInputStream(this.image));
    }

    public byte[] getByteImage(){ return image;}

    /*public void setImage(Image image) {
        this.image = image;
    }*/

    public int getId(){
        return id;
    }
}

