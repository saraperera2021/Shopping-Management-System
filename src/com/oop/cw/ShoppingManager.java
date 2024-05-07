package com.oop.cw;

import java.util.List;
interface ShoppingManager {
    void addToSystem(Product product);
    void removeFromSystem(String productId);
    void printListOfProducts();
    void loadProductsFromFile();
    List<Product> getListOfProducts();
    void displayLoadedData();
}
