package com.oop.cw;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// WestminsterShoppingManager class implementing the ShoppingManager interface
public class WestminsterShoppingManager implements ShoppingManager {

    private static final String FILE_NAME = "products.dat";

    public static final int MAX_SLOTS=50;
    private int freeSlot=MAX_SLOTS;
    private List<Product> listOfProducts= new ArrayList<>();


    // Implement methods from ShoppingManager interface
    @Override
    public void addToSystem(Product product) {
        if(listOfProducts.contains(product)){ //check if product is already in the system
            System.out.println("Product is already in the system");
        }
        else if (listOfProducts.size()>=MAX_SLOTS) {
            System.out.println("System is full");
        }
        else{
            listOfProducts.add(product);
            freeSlot--;
            System.out.println(freeSlot>0 ? "Free slots remaining: "+freeSlot:"No more space available");
        }
    }

    @Override
    public void removeFromSystem(String productId) {
        Iterator<Product> iterator = listOfProducts.iterator();
        boolean found=false;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product != null && product.getProductId().equals(productId)) {
                found = true;
                iterator.remove();
                System.out.printf("A %s product has been removed from the system\n",
                        product instanceof Electronics ? "Electronics" : "Clothing");
                break;
            }
        }

        if(!found) {
            System.out.println("Invalid product ID . Please Recheck.");
        }

    }

    @Override
    public void printListOfProducts() {
        if (listOfProducts.isEmpty()) {
            System.out.println("No products in the system");
        } else {
            // Filter out null elements before sorting
            List<Product> nonNullProducts = listOfProducts.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            nonNullProducts.sort(Comparator.comparing(Product::getProductId,
                    Comparator.nullsLast(String::compareToIgnoreCase)));

            System.out.println("List of all the products (ordered alphabetically by Product ID):");
            for (Product product : nonNullProducts) {
                printProductDetails(product);
                System.out.println("------------------------------");
            }
        }
    }
    private void printProductDetails(Product product) {
        if (product instanceof Electronics) {
            System.out.println("Electronics Product:");
            System.out.println(((Electronics) product).toString());
        } else if (product instanceof Clothing) {
            System.out.println("Clothing Product:");
            System.out.println(((Clothing) product).toString());
        }
    }

    public void loadProductsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            listOfProducts = (List<Product>) inputStream.readObject();
            System.out.println("Product list loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading product list from file: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getListOfProducts() {
        return listOfProducts;
    }

    public void displayLoadedData() {
        if (listOfProducts.isEmpty()) {
            System.out.println("No products loaded from the file.");
        } else {
            System.out.println("List of products loaded from the file:");
            for (Product product : listOfProducts) {
                if (product instanceof Electronics) {
                    System.out.println("Electronics Product:");
                    System.out.println(((Electronics) product).toString());
                } else if (product instanceof Clothing) {
                    System.out.println("Clothing Product:");
                    System.out.println(((Clothing) product).toString());
                }
                System.out.println("------------------------------");
            }
        }
    }

    public List<Product> getElectronicsProducts() {
        return listOfProducts.stream()
                .filter(product -> product instanceof Electronics)
                .collect(Collectors.toList());
    }

    public List<Product> getClothingProducts() {
        return listOfProducts.stream()
                .filter(product -> product instanceof Clothing)
                .collect(Collectors.toList());
    }
}