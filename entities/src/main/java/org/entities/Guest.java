package org.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Guest implements Serializable {

    @Column(name = "nameOfCustomer")
    private String name;
    //@Transient
    //private Cart cart;

    public Guest(String name) {
        this.name = name;
        System.out.println("Hello there Guest!");
    }

    public Guest() {
        this.name="Guest";
        System.out.println("Hello there default Guest!");
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
