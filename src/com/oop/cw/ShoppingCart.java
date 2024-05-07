package com.oop.cw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private List<Product> items;
    private Map<Product, Integer> quantities;

    //Constructor for the ShoppingCart class
    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.quantities = new HashMap<>();
    }

    //Method to add a product to the cart
    public void addToCart(Product product, int quantity) {
        items.add(product);
        quantities.put(product, quantity);
    }

    //Method to remove a product from the cart

    public void removeFromCart(Product product) {
        items.remove(product);
        quantities.remove(product);
    }


    //Method to calculate the total cost of products in the cart
    public double calculateTotal() {
        double total = 0.0;
        for (Product product : items) {
            int quantity = quantities.getOrDefault(product, 1); // default to 1 if quantity is not present
            total += product.getProductPrice() * quantity;
        }
        return total;
    }


    // Getter method to retrieve the list of products in the cart
    public List<Product> getItems() {
        return items;
    }

    public int getQuantity(Product product) {
        return quantities.getOrDefault(product, 0);
    }
}










