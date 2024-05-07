package com.oop.cw;

import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Console {
    static WestminsterShoppingManager manager=new WestminsterShoppingManager();
    final static Scanner USER_INPUT=new Scanner(System.in);


    public static void main(String[] args) {
        manager.loadProductsFromFile();
        manager.displayLoadedData();
        menuLoop:
        while (true){
            displayMenu();
            int choice=USER_INPUT.nextInt();

            switch(choice){
                case 1:
                    addToSystem();
                    break;
                case 2:
                    removeFromSystem();
                    break;
                case 3:
                    manager.printListOfProducts();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    System.out.println("Open GUI");
                    openGUI();
                    break;
                case 6:
                    System.out.println("Thank you for using the System!");
                    break menuLoop;
                default:
                    System.out.println("You selected an invalid option. Please try again.");
            }
        }
    }
    public static void displayMenu(){
        System.out.println("Welcome to the Westminster Shopping Manager.\n Please select an option: ");
        System.out.println("1: Add a Product");
        System.out.println("2: Delete a Product");
        System.out.println("3: Display all Products");
        System.out.println("4: Save products in a file.");
        System.out.println("5: Load GUI");
        System.out.println("6: Quit");

    }

    static void openGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                new ShoppingGUI(manager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    static void addToSystem() {
        Product product;

        System.out.println("Please enter the type of Product:\nElectronics/Clothing");
        String choice = USER_INPUT.next().toLowerCase();

        System.out.println("Please enter the product ID");
        String productId = USER_INPUT.next();
        // Use RegEx to validate the idPlate
        if (!productId.matches("P\\d{4}")) {
            System.out.println("Invalid product ID. Please use the format P0001, P0002, P0003, etc.");
            return; // Return from the method if validation fails
        }

        System.out.println("Please enter the name of the product");
        String productName = USER_INPUT.next();

        System.out.println("Please enter the product availability count");
        int productAvailabilityCount = USER_INPUT.nextInt();

        System.out.println("Please enter the price of the product");
        double productPrice = USER_INPUT.nextDouble();

        switch (choice) {
            case "electronics":
                System.out.println("Pls enter brand");
                String brand = USER_INPUT.next();

                System.out.println("Pls enter  warranty period in years:");
                int warrantyPeriod = USER_INPUT.nextInt();

                product = new Electronics(productId,productName, productAvailabilityCount, productPrice,brand,warrantyPeriod);
                break;

            case "clothing":
                System.out.println("Pls enter size");
                String size = USER_INPUT.next();

                System.out.println("Please enter the color of clothing");
                String color=USER_INPUT.next();

                product = new Clothing(productId,productName, productAvailabilityCount, productPrice, size,color);
                break;

            default:
                product = null;
        }
            manager.addToSystem(product);
    }
    static void removeFromSystem(){
        System.out.println("Please Enter the Product ID\n.............................................");
        String productId=USER_INPUT.next();
        // Use RegEx to validate the ProductId
        if (!productId.matches("P\\d{4}")) {
            System.out.println("Invalid product ID. Please use the format P0001, P0002, P0003, etc.");
            return; // Return from the method if validation fails
        }

        manager.removeFromSystem(productId);
    }


    static void saveToFile(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
            outputStream.writeObject(manager.getListOfProducts());
            System.out.println("Product list saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving product list to file: " + e.getMessage());
        }
    }

}
