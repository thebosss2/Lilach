package org.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "orders")
public class Order implements Serializable {     //Product class entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;         // id generated for each product

    @ManyToMany
    protected List<PreMadeProduct> preMadeProducts;

    @ManyToMany
    protected List<CustomMadeProduct> customMadeProducts;

    @ManyToOne
    protected Customer orderedBy;

    @ManyToOne
    private Store store;

    protected int price;
    protected Date orderTime;
    protected Date DeliveryTime;
    protected boolean isDelivered;

    protected enum Delivery {SHIPPING, TAKEAWAY}
    protected Delivery delivery;

    // above - shipping order information
    private String phoneNumber = null;
    private String receiverName= null;
    private String address= null;
    private String email= null;
    private Boolean shippingToSelf= null;

    public Order(LinkedList<PreMadeProduct> preMadeProducts, LinkedList<CustomMadeProduct> customMadeProducts,
                 Customer orderedBy, int price, Date orderTime, Date deliveryTime,
                 Delivery delivery, String phoneNumber, String receiverName, String address,
                 String email, Boolean shippingToSelf) {

        this.preMadeProducts = preMadeProducts;
        this.customMadeProducts = customMadeProducts;
        this.orderedBy = orderedBy;
        this.price = price;
        this.orderTime = orderTime;
        this.DeliveryTime = deliveryTime;
        this.isDelivered = false;
        this.delivery = delivery;

        if(delivery == Delivery.SHIPPING)
            setShippingInfo(phoneNumber, receiverName, address, email, shippingToSelf);
    }

    private void setShippingInfo(String phoneNumber, String receiverName,
                                 String address, String email, Boolean shippingToSelf) {

        this.phoneNumber = phoneNumber;
        this.receiverName = receiverName;
        this.address = address;
        this.email = email;
        this.shippingToSelf = shippingToSelf;
    }


    public Order() {

    }

    public List<CustomMadeProduct> getCustomMadeProducts(){
        return this.customMadeProducts;
    }

    public void setCustomMadeProducts(LinkedList<CustomMadeProduct> customMadeProducts){
        this.customMadeProducts = customMadeProducts;
    }

    public void setPreMadeProducts(LinkedList<PreMadeProduct> preMadeProducts){
        this.preMadeProducts = preMadeProducts;
    }

    public List<PreMadeProduct> getPreMadeProducts(){
        return this.preMadeProducts;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getShippingToSelf() {
        return shippingToSelf;
    }

    public void setShippingToSelf(Boolean shippingToSelf) {
        this.shippingToSelf = shippingToSelf;
    }

}
