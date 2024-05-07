package com.oop.cw;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ShoppingGUI extends JFrame {
    private WestminsterShoppingManager manager;
    private ShoppingCart shoppingCart;
    private JTable productTable;
    private JTable cartItems;
    private JComboBox<String> categoryComboBox;
    private TableRowSorter<DefaultTableModel> tableRowSorter;
    private JTextArea detailsTextArea;

    public ShoppingGUI(WestminsterShoppingManager manager) {
        this.manager = manager;

        setTitle("Westminster Shopping Manager");
        setSize(1300, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.shoppingCart = new ShoppingCart();
        initializeComponents();
        refreshDisplay();
        setVisible(true);
    }

    private void showShoppingCart() {
        // JFrame to display the shopping cart
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(1100, 1000);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // DefaultTableModel for the shopping cart table
        DefaultTableModel cartTableModel = new DefaultTableModel();
        cartItems = new JTable(cartTableModel);

        cartTableModel.addColumn("Product");
        cartTableModel.addColumn("Price(€)");
        cartTableModel.addColumn("Quantity");
        cartTableModel.addColumn("Total Price(€)");

        for (Product product : shoppingCart.getItems()) {
            if (product instanceof Electronics) {

                String name = "<html>" + product.getProductId() + "<br>" + product.getProductName() + "<br>" + ((Electronics) product).getBrand() + "</html>";

                double price = product.getProductPrice();
                int quantity = shoppingCart.getQuantity(product);
                double totalPrice = price * quantity;

                Object[] rowData = {name, price, quantity, totalPrice};
                cartTableModel.addRow(rowData);

            } else if (product instanceof Clothing) {
                String name = "<html>" + product.getProductId() + "<br>" + product.getProductName() + "<br>" + ((Clothing) product).getSize() + "\n, " + ((Clothing) product).getColor() + "</html>";

                double price = product.getProductPrice();
                int quantity = shoppingCart.getQuantity(product);
                double totalPrice = price * quantity;

                Object[] rowData = {name, price, quantity, totalPrice};
                cartTableModel.addRow(rowData);
            }

        }
        // JTable for the shopping cart
        cartItems = new JTable(cartTableModel);

        // Add the shopping cart table to a JScrollPane
        JScrollPane cartScrollPane = new JScrollPane(cartItems);

        // Create a panel for other components
        JPanel otherComponentsPanel = new JPanel();
        otherComponentsPanel.setLayout(new BoxLayout(otherComponentsPanel, BoxLayout.Y_AXIS)); // Use BoxLayout to arrange components vertically


        // Calculate the total price and discounts
        double totalPrice = calculateTotalPrice();
        double discountAmount = calculateDiscount();

        // Create JLabels to display the discount and final total
        JLabel totalLabel=new JLabel("Total Price: €"+totalPrice);
        JLabel discountLabel = new JLabel("Three Items in Same Category Discount (20%) : " + getCategoryDiscountDetails());
        JLabel finalTotalLabel = new JLabel("Final Total: €" + (totalPrice - discountAmount));


        // Add labels to the panel
        otherComponentsPanel.add(totalLabel);
        otherComponentsPanel.add(discountLabel);
        otherComponentsPanel.add(finalTotalLabel);

        //GridLayout to arrange components in the shoppingCartFrame
        shoppingCartFrame.setLayout(new GridLayout(2, 1));

        // Add the JScrollPane to the shoppingCartFrame
        shoppingCartFrame.add(cartScrollPane, BorderLayout.CENTER);

        // Add the panel with other components to the SOUTH position of the shoppingCartFrame
        shoppingCartFrame.add(otherComponentsPanel, BorderLayout.SOUTH);

        cartItems.setRowHeight(60);// Set the desired row height

        // Make the shoppingCartFrame visible
        shoppingCartFrame.setVisible(true);
    }

    // Calculate the total discount amount
    private double calculateDiscount() {
        double totalDiscount = 0.0;

        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Product product : shoppingCart.getItems()) {
            String category = getCategoryForProduct(product);
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }

        // Apply discount if there are at least three items in the same category
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            if (entry.getValue() >= 3) {
                double categoryDiscount = 0.2; // 20% discount
                double categoryTotal = getCategoryTotal(entry.getKey());
                totalDiscount += categoryTotal * categoryDiscount;
            }
        }

        return totalDiscount;
    }

    // Get details about the category discount
    private String getCategoryDiscountDetails() {
        StringBuilder discountDetails = new StringBuilder();

        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Product product : shoppingCart.getItems()) {
            String category = getCategoryForProduct(product);
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }

        // Apply discount if there are at least three items in the same category
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            if (entry.getValue() >= 3) {
                double categoryDiscount = 0.2; // 20% discount
                double categoryTotal = getCategoryTotal(entry.getKey());
                double discountAmount = categoryTotal * categoryDiscount;

                discountDetails.append(entry.getKey()).append(": €").append(discountAmount).append(" ");
            }
        }

        return discountDetails.toString();
    }


    // Calculate the total price of products in the shopping cart
    private double calculateTotalPrice() {
        double totalPrice = 0.0;

        // Map to store the count of items for each category
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Product product : shoppingCart.getItems()) {
            totalPrice += product.getProductPrice() * shoppingCart.getQuantity(product);

            // Update category count
            String category = getCategoryForProduct(product);
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);

        }

        // Apply discount if there are at least three items in the same category
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            if (entry.getValue() >= 3) {
                double categoryDiscount = 0.2; // 20% discount
                double categoryTotal = getCategoryTotal(entry.getKey());
                double discountAmount = categoryTotal * categoryDiscount;
                totalPrice -= discountAmount;

                // Display the amount reduced from the total price
                System.out.println("Three Items in Same Category Discount (20%) - " + entry.getKey() + ": €" + discountAmount);
            }
        }

        // Display the Final Total after reducing the Discount from the Total price
        System.out.println("Final Total: €" + totalPrice);

        return totalPrice;
    }

    //Method to get the category for a given product
    private String getCategoryForProduct(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        }
        return "";
    }

    // Method to calculate the total price for a specific category
    private double getCategoryTotal(String category) {
        double categoryTotal = 0.0;
        for (Product product : shoppingCart.getItems()) {
            if (getCategoryForProduct(product).equals(category)) {
                categoryTotal += product.getProductPrice() * shoppingCart.getQuantity(product);
            }
        }
        return categoryTotal;
    }


    private void initializeComponents() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);

        // Create a JTextArea for displaying product details
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);  // Make it non-editable
        detailsTextArea.setLineWrap(true);
        JScrollPane detailsScrollPane = new JScrollPane(detailsTextArea);
        detailsScrollPane.setPreferredSize(new Dimension(600, 400));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JLabel("Product Details"), BorderLayout.NORTH);
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        add(detailsPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        scrollPane.setViewportBorder(border);
        scrollPane.setBorder(border);

        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price(€)");
        tableModel.addColumn("Category");
        tableModel.addColumn("Info");

        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        categoryComboBox.addActionListener(e -> refreshDisplay());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshDisplay());

        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> showShoppingCart());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(categoryComboBox);
        controlPanel.add(refreshButton);
        controlPanel.add(shoppingCartButton); // Add the "Shopping Cart" button to the control panel

        // Initialize TableRowSorter and set it to the JTable
        tableRowSorter = new TableRowSorter<>((DefaultTableModel) productTable.getModel());
        productTable.setRowSorter(tableRowSorter);

        JButton sortButton = new JButton("Sort by Product ID");
        sortButton.addActionListener(e -> sortProductTable());

        controlPanel.add(sortButton);


        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = getSelectedProducts().get(selectedRow);
                int quantity = askForQuantity(); // Prompt the user for quantity
                shoppingCart.addToCart(selectedProduct, quantity);
                refreshDisplay(); // Refresh the display to update availability color
            }
        });

        controlPanel.add(addToCartButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, controlPanel);
        splitPane.setDividerLocation(300);

        add(splitPane);

        ListSelectionModel selectionModel = productTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Product selectedProduct = getSelectedProducts().get(selectedRow);
                    displayProductDetails(selectedProduct);
                }
            }
        });

        refreshDisplay();
    }



    private int askForQuantity() {
        String input = JOptionPane.showInputDialog("Enter quantity:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 1; // Default to 1 if the input is not a valid number
        }
    }


private void refreshDisplay() {
    DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
    tableModel.setRowCount(0);

    List<Product> productList = getSelectedProducts();

    for (Product product : productList) {
        if (product == null) {
            continue;
        }

        String productID = "";
        String name = "";
        double price = 0.0;
        String category = "";
        String details = "";

        // Check the availability of the product
        boolean isLowAvailability = false;
        if (product.getProductAvailabilityCount() < 3) {
            isLowAvailability = true;
        }

        // Check if the product is in the shopping cart
        boolean isInCart = shoppingCart.getItems().contains(product);

        if (product instanceof Electronics) {
            productID = product.getProductId();
            name = product.getProductName();
            price = product.getProductPrice();
            category = "Electronics";
            details = ((Electronics) product).getBrand() + " , " + ((Electronics) product).getWarrantyPeriod() + " years";
        } else if (product instanceof Clothing) {
            productID = product.getProductId();
            name = product.getProductName();
            price = product.getProductPrice();
            category = "Clothing";
            details = ((Clothing) product).getSize() + " , " + ((Clothing) product).getColor();
        }

        // Set text color based on availability and whether the product is in the cart
        Object[] rowData;
        if (isLowAvailability) {
            rowData = new Object[]{makeRed(productID), makeRed(name), makeRed(price), makeRed(category), makeRed(details)};
        } else {
            if (isInCart) {
                rowData = new Object[]{"[In Cart] " + productID, "[In Cart] " + name, "[In Cart] " + price, "[In Cart] " + category, "[In Cart] " + details};
            } else {
                rowData = new Object[]{productID, name, price, category, details};
            }
        }

        tableModel.addRow(rowData);
    }
}


    private Object makeRed(Object value) {
        // Wrap the value with HTML to apply red color
        String redText = "<html><font color='red'>" + value + "</font></html>";

        // Return the original value without HTML tags for sorting
        return redText;
    }



    private void sortProductTable() {
        // Sort the table based on the first column (Product ID)
        List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        tableRowSorter.setSortKeys(sortKeys);

        // Set a custom comparator to compare the original values without HTML tags
        tableRowSorter.setComparator(0, Comparator.comparing(this::getOriginalValue));

        tableRowSorter.sort();
    }

    private String getOriginalValue(Object value) {
        // Remove HTML tags to get the original value
        return value.toString().replaceAll("<.*?>", "");
    }


    private List<Product> getSelectedProducts() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        switch (selectedCategory) {
            case "Electronics":
                return manager.getElectronicsProducts();
            case "Clothing":
                return manager.getClothingProducts();
            default:
                return manager.getListOfProducts();
        }
    }

        private void displayProductDetails(Product product) {

            System.out.println("Displaying details for product: " + product);// Display product details in detailsTextArea
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            detailsTextArea.setText("Product ID: " + electronicsProduct.getProductId() + "\n"
                    + "Name: " + electronicsProduct.getProductName() + "\n"
                    + "Price: €" + electronicsProduct.getProductPrice() + "\n"
                    + "Category: Electronics\n"
                    + "Brand: " + electronicsProduct.getBrand() + "\n"
                    + "Warranty Period: " + electronicsProduct.getWarrantyPeriod() + " years");
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            detailsTextArea.setText("Product ID: " + clothingProduct.getProductId() + "\n"
                    + "Name: " + clothingProduct.getProductName() + "\n"
                    + "Price: €" + clothingProduct.getProductPrice() + "\n"
                    + "Category: Clothing\n"
                    + "Size: " + clothingProduct.getSize() + "\n"
                    + "Color: " + clothingProduct.getColor());
        }
    }



    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ShoppingGUI shoppingGUI = new ShoppingGUI(manager);

        // Initialize selection model
        ListSelectionModel selectionModel = shoppingGUI.productTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                System.out.println("Selection Event Triggered!");

                int selectedRow = shoppingGUI.productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Product selectedProduct = shoppingGUI.getSelectedProducts().get(selectedRow);

                    System.out.println("Selected Row: " + selectedRow);
                    System.out.println("Selected Product: " + selectedProduct);

                    shoppingGUI.displayProductDetails(selectedProduct);
                }
            }
        });

        // Refresh display after initializing the shoppingCart
        shoppingGUI.refreshDisplay();
    });
}
}