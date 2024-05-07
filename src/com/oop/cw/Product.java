package com.oop.cw;

import java.io.Serializable;

public abstract class Product implements Serializable {

    private String productId;
    private String productName;
    private int productAvailabilityCount;
    private double productPrice;

    //Constructor for the product class
public Product(String productId, String productName,int productAvailabilityCount,double productPrice){
    this.productId=productId;
    this.productName=productName;
    this.productAvailabilityCount=productAvailabilityCount;
    this.productPrice=productPrice;
}

//Getter and setter methods for the product class
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductAvailabilityCount() {
        return productAvailabilityCount;
    }

    public void setProductAvailabilityCount(int productAvailabilityCount) {
        this.productAvailabilityCount = productAvailabilityCount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product Information: " +
                "\nproductId=" + productId +
                ",\n productName=" + productName  +
                ",\n productAvailabilityCount=" + productAvailabilityCount +
                ",\n productPrice=" + productPrice+" $";
    }
}