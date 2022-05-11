package org.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Guest {

    @Column(name = "nameOfCustomer")
    private String name="Guest";
    //@Transient
    //private Cart cart;

    public Guest(String name) {
        this.name = name;
    }

    public Guest() {
    }
    /*    public void setCart(Cart c){
        this.cart=c;
    }
    public Cart getCart(){
        return cart;
    }
    */














    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
