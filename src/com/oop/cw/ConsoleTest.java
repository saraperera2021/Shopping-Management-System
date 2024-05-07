package com.oop.cw;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
class ConsoleTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }
    @Test
    void main_validInput_exitSuccessfully() {
        // Arrange
        String input = "6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Act
        Console.main(null);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Thank you for using the System!"));
    }

    @Test
    void displayMenu_menuDisplayedSuccessfully() {
        // Arrange
        // No need for additional setup

        // Act
        Console.displayMenu();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Welcome to the Westminster Shopping Manager."));
    }

    @Test
    void openGUI_guiOpenedSuccessfully() {
        // Arrange
        // Mock the SwingUtilities class
        SwingUtilitiesMock.mock();

        // Act
        Console.openGUI();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Open GUI"));
    }

    @Test
    void addToSystem_validElectronicsInput_successfullyAdded() {
        // Arrange
        String input = "electronics\nP0001\nTestElectronics\n10\n100.0\nTestBrand\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Act
        Console.addToSystem();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("com.oop.cw.Product added successfully"));
    }


    @Test
    void removeFromSystem_validProductId_successfullyRemoved() {
        // Arrange
        String input = "P0001\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Act
        Console.removeFromSystem();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("com.oop.cw.Product removed successfully"));
    }

    @Test
    void saveToFile_validInput_fileCreatedSuccessfully() {
        // Arrange
        // Mock the manager with a product
        Product mockProduct = new Electronics("P0003", "TestElectronics", 8, 80.0, "MockBrand", 3);
        WestminsterShoppingManager mockManager = new WestminsterShoppingManager();
        mockManager.addToSystem(mockProduct);
        Console.manager = mockManager;

        // Act
        Console.saveToFile();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("com.oop.cw.Product list saved to file."));
    }

    public static class SwingUtilitiesMock {
        public static void mock() {
            SwingUtilities.invokeLater(() -> {
                // Mock implementation for SwingUtilities.invokeLater
            });
        }
    }


}