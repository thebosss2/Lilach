package org.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "customProducts")
public class CustomMadeProduct extends Product {

    @ManyToMany
    @Column(name= "products")
    private List<PreMadeProduct> products = new LinkedList<PreMadeProduct>();


    public enum ItemType {FLOWER_ARRANGEMENT, BLOOMING_POT, BRIDES_BOUQUET, BOUQUET}
    private ItemType itemType;

    public ItemType getItemType() {
        return itemType;
    }

    // this is called when on custom made page
    //user selects multiple premadeProducts into list that is saved on controller(customMadeCatalog controller)
    // when they are done they select create customProduct (when added to cart)
    //then and only then is the constructor called
    public CustomMadeProduct(List<PreMadeProduct> products, int price, String path, ItemType itemType){
        super(path,price);
        this.products=products;
        this.itemType = itemType;
    }

    public CustomMadeProduct(List<PreMadeProduct> p, int price, byte[] image){
        super(image,price);
        products=p;
    }

    public CustomMadeProduct() {

    }

    public List<PreMadeProduct> getProducts(){
        return this.products;
    }

}
