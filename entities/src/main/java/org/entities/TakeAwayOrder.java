package org.entities;

public class TakeAwayOrder extends Order {
    private Store store;

    public TakeAwayOrder(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
