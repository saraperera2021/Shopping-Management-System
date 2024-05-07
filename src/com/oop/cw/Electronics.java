package com.oop.cw;

public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;

    //Constructor for Electronics class
    public Electronics(String productId,String productName,int productAvailabilityCount,double productPrice,String brand,int warrantyPeriod)
    {
        super(productId,productName,productAvailabilityCount,productPrice);//calling the constructor using the super keyword
        this.brand=brand;
        this.warrantyPeriod=warrantyPeriod;
    }

    //Getter and setter methods for Electronics class
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }


    @Override
    public String toString() {
        return super.toString() +
                "\nBrand: " + brand +
                "\nWarranty Period: " + warrantyPeriod + " years";
    }
}
