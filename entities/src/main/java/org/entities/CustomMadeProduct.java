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
    //
    // this is called when on custom made page
    //user selects multiple premadeProducts into list that is saved on controller(customMadeCatalog controller)
    // when they are done they select create customProduct (when added to cart)
    //then and only then is the constructor called
    public CustomMadeProduct(List<PreMadeProduct> p, int price, String path){
        super(path,price);
        products=p;
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
