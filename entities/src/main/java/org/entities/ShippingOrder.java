package org.entities;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ShippingOrder extends Order {
    //private Employee storeManager;
    private String phoneNumber;
    private String receiverName;
    private String address;
    private String email;
    private Boolean shippingToSelf;

    public ShippingOrder() {
    }

    /*
        public Employee getStoreManager() {
            return storeManager;
        }

        public void setStoreManager(Employee storeManager) {
            this.storeManager = storeManager;
        }
    */
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
