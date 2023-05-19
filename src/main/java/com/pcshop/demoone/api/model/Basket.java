package com.pcshop.demoone.api.model;

import java.util.List;

public class Basket {

    private String userName;
    private List<Product> products;

    public Basket(String userName, List<Product> products) {
        this.userName = userName;
        this.products = products;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }

}
