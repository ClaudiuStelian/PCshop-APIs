package com.pcshop.demoone.api.model;

public class UserBasket {
    private String userName;
    private String productName;

    public UserBasket(String userName, String productName) {
        this.userName = userName;
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
