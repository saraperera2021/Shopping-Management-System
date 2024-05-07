package com.oop.cw;

public class Clothing extends Product {
    private String size;
    private String color;

    //Constructor for the Clothing class
    public Clothing(String productId, String productName, int productAvailabilityCount, double productPrice, String size, String color){
        super(productId,productName,productAvailabilityCount,productPrice);
        this.size=size;
        this.color=color;
    }

    //Getter and setter methods for the Clothing class
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return super.toString() +
                "\nSize: " + size +
                "\nColor: " + color;
    }
}
