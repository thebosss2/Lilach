package org.client;

import javafx.scene.image.Image;
import javafx.scene.text.Text;

public class Product{
    private String name;
    private Image image;
    private int price;
    private int priceBeforeDiscount;

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
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

