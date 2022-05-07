package org.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "orders")
public abstract class Order implements Serializable {     //Product class entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;         // id generated for each product
    @ManyToOne
    private Product[] products;

    //TODO - need to make RegisteredUser Class
    //private RegisteredUser orderedBy;

    private int price;
    private Date orderTime;
    private Date DeliveryTime;
    private boolean isDelivered;


    public Order(Product[] products, /*RegisteredUser orderedBy,*/
                 int price, Date orderTime, Date deliveryTime) {
        this.products = products;
        //this.orderedBy = orderedBy;
        this.price = price;
        this.orderTime = orderTime;
        this.DeliveryTime = deliveryTime;
        this.isDelivered = false;
    }

    public Order() {

    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    /*public RegisteredUser getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(RegisteredUser orderedBy) {
        this.orderedBy = orderedBy;
    }*/

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
}
