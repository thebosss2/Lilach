package org.example;

import javafx.scene.image.Image;

public class Product {
    private String name;
    private Image image;
    private double price;
    private double priceBeforeDiscount;

    public void setImage(Image image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(double priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }
}

