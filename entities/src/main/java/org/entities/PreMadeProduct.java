package org.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;
import java.io.FileInputStream;

@Entity
@Table(name = "premadeProducts")
public class PreMadeProduct extends Product{

    @Column(name = "product_name")
    private String name;
    private int priceBeforeDiscount;
    private String mainColor;
    public enum ProductType {CATALOG, CUSTOM_CATALOG}
    private ProductType productType;

    //Ctor for catalog product
    public PreMadeProduct(String name, String path, int price, int priceBeforeDiscount) {      //constructor
        super(path,price);
        this.priceBeforeDiscount=priceBeforeDiscount;
        this.name = name;
        this.productType = ProductType.CATALOG;
    }

    //Ctor for catalog product
    public PreMadeProduct(String name, byte[] image, int price, int priceBeforeDiscount) {
        super(image,price);
        this.priceBeforeDiscount=priceBeforeDiscount;
        this.name = name;
        this.productType = ProductType.CATALOG;
    }

    //Ctor for custom-made catalog product
    public PreMadeProduct(String name, String path, int price, int priceBeforeDiscount, String mainColor) {      //constructor
        super(path,price);
        this.priceBeforeDiscount=priceBeforeDiscount;
        this.name = name;
        this.mainColor = mainColor;
        this.productType = ProductType.CUSTOM_CATALOG;

    }

    //Ctor for custom-made catalog product
    public PreMadeProduct(String name, byte[] image, int price, int priceBeforeDiscount, String mainColor) {
        super(image,price);
        this.priceBeforeDiscount=priceBeforeDiscount;
        this.name = name;
        this.mainColor = mainColor;
        this.productType = ProductType.CUSTOM_CATALOG;
    }

    public PreMadeProduct() {

    }

    public int getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(int priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
