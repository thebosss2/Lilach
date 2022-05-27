package org.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "premadeProducts")
public class PreMadeProduct extends Product {

    @Column(name = "product_name")
    private String name;
    private int priceBeforeDiscount;
    private String mainColor;

    public enum ProductType {CATALOG, CUSTOM_CATALOG}

    private ProductType productType;
    private String description;
    private int discount;

    //Ctor for catalog product
    public PreMadeProduct(String name, String path, int priceBeforeDiscount, String description, int discount) { //constructor
        super(path, (int) (priceBeforeDiscount*(1-discount*0.01)));
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.name = name;
        this.productType = ProductType.CATALOG;
        this.description = description;
        this.discount=discount;
    }

    //Ctor for catalog product
    public PreMadeProduct(String name, byte[] image, int priceBeforeDiscount, String description, int discount) {
        super(image, (int) (priceBeforeDiscount*(1-discount*0.01)));
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.name = name;
        this.productType = ProductType.CATALOG;
        this.description = description;
        this.discount=discount;
    }


    //Ctor for custom-made catalog product
    public PreMadeProduct(String name, String path, int priceBeforeDiscount, int discount, String mainColor) { //constructor
        super(path, (int) (priceBeforeDiscount*(1-discount*0.01)));
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.name = name;
        this.mainColor = mainColor;
        this.productType = ProductType.CUSTOM_CATALOG;
        this.discount=discount;

    }

    //Ctor for custom-made catalog product
    public PreMadeProduct(String name, byte[] image, int priceBeforeDiscount, int discount, String mainColor) {
        super(image, (int) (priceBeforeDiscount*(1-discount*0.01)));
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.name = name;
        this.mainColor = mainColor;
        this.productType = ProductType.CUSTOM_CATALOG;
        this.discount=discount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() {
        return this.productType;
    }

    public String getMainColor() {
        return this.mainColor;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

}
