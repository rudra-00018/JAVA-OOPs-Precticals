package app;

import exception.InvalidProductException;
import model.Product;
import service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProductApp {

    private static final String DEFAULT_STORAGE_FILE = "products.dat";

    public static void main(String[] args) {
        ProductService service = new ProductService();

        // Check if demo mode was requested or if stdin is non-interactive
        boolean runDemo = (args.length > 0 && args[0].equalsIgnoreCase("--demo"));

        if (runDemo) {
            runAutomatedDemo(service);
            return;
        }

        // Try interactive menu; fallback to automated demo if no input is piped
        Scanner scanner = new Scanner(System.in);
        System.out.println("==========================================================");
        System.out.println("           PRODUCT INVENTORY MANAGEMENT SYSTEM            ");
        System.out.println("              (Layered Architecture App)                  ");
        System.out.println("==========================================================");

        if (!scanner.hasNext()) {
            System.out.println("No interactive input detected. Running automated demo mode...\n");
            runAutomatedDemo(service);
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice (1-7): ");
            if (!scanner.hasNextInt()) {
                break;
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> handleAddProduct(scanner, service);
                case 2 -> handleListProducts(service);
                case 3 -> handleFindProduct(scanner, service);
                case 4 -> handleRemoveProduct(scanner, service);
                case 5 -> handleSaveToFile(service);
                case 6 -> handleLoadFromFile(service);
                case 7 -> {
                    System.out.println("Exiting Product Inventory App. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid selection! Please enter a number between 1 and 7.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Find Product by ID");
        System.out.println("4. Remove Product by ID");
        System.out.println("5. Save Catalog to File");
        System.out.println("6. Load Catalog from File");
        System.out.println("7. Exit");
        System.out.println("--------------------------------------");
    }

    private static void handleAddProduct(Scanner scanner, ProductService service) {
        try {
            System.out.print("Enter Product ID (int): ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Product Price: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine().trim());

            service.addProduct(new Product(id, name, price, qty));
            System.out.println("SUCCESS: Product added! Current total: " + service.getProductCount());
        } catch (NumberFormatException e) {
            System.out.println("Input error: Please enter valid numbers for ID, price, and quantity.");
        } catch (InvalidProductException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private static void handleListProducts(ProductService service) {
        List<Product> list = service.getAllProducts();
        if (list.isEmpty()) {
            System.out.println("Inventory is currently empty.");
            return;
        }
        System.out.println("\n--- Current Inventory (" + list.size() + " items) ---");
        System.out.printf("%-6s | %-20s | %-10s | %-8s%n", "ID", "Name", "Price", "Quantity");
        System.out.println("-----------------------------------------------------");
        for (Product p : list) {
            System.out.printf("%-6d | %-20s | $%-9.2f | %-8d%n", 
                p.getId(), p.getName(), p.getPrice(), p.getQuantity());
        }
    }

    private static void handleFindProduct(Scanner scanner, ProductService service) {
        try {
            System.out.print("Enter Product ID to search: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Product p = service.getProductById(id);
            System.out.println("Found: " + p);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleRemoveProduct(Scanner scanner, ProductService service) {
        try {
            System.out.print("Enter Product ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            service.removeProduct(id);
            System.out.println("SUCCESS: Product removed. Remaining: " + service.getProductCount());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleSaveToFile(ProductService service) {
        try {
            service.saveToFile(DEFAULT_STORAGE_FILE);
            System.out.println("SUCCESS: Saved " + service.getProductCount() + " products to " + DEFAULT_STORAGE_FILE);
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    private static void handleLoadFromFile(ProductService service) {
        try {
            service.loadFromFile(DEFAULT_STORAGE_FILE);
            System.out.println("SUCCESS: Loaded " + service.getProductCount() + " products from " + DEFAULT_STORAGE_FILE);
        } catch (Exception e) {
            System.out.println("Load failed: " + e.getMessage());
        }
    }

    public static void runAutomatedDemo(ProductService service) {
        System.out.println("--- 1. Demonstrating Adding Valid Products ---");
        service.addProduct(new Product(101, "Mechanical Keyboard", 89.99, 15));
        service.addProduct(new Product(102, "Wireless Mouse", 34.50, 40));
        service.addProduct(new Product(103, "UltraWide Monitor", 349.00, 8));
        System.out.println("Added 3 products. Current count: " + service.getProductCount());
        handleListProducts(service);

        System.out.println("\n--- 2. Demonstrating Invalid Action Handling (Exceptions) ---");
        try {
            System.out.println("Attempting to add product with negative price (-50.0)...");
            service.addProduct(new Product(104, "Invalid Item", -50.0, 5));
        } catch (InvalidProductException e) {
            System.out.println("  -> CAUGHT EXPECTED EXCEPTION: " + e.getMessage());
        }

        try {
            System.out.println("Attempting to add product with duplicate ID (101)...");
            service.addProduct(new Product(101, "Duplicate Item", 99.0, 2));
        } catch (InvalidProductException e) {
            System.out.println("  -> CAUGHT EXPECTED EXCEPTION: " + e.getMessage());
        }

        System.out.println("\n--- 3. Demonstrating Save & Reload Persistence ---");
        try {
            service.saveToFile(DEFAULT_STORAGE_FILE);
            System.out.println("Catalog saved to " + DEFAULT_STORAGE_FILE);

            // Fresh service instance
            ProductService freshService = new ProductService();
            System.out.println("Fresh service initial count: " + freshService.getProductCount());
            freshService.loadFromFile(DEFAULT_STORAGE_FILE);
            System.out.println("Fresh service count after loading: " + freshService.getProductCount());
            handleListProducts(freshService);

            // Clean up demo file
            new File(DEFAULT_STORAGE_FILE).delete();
            System.out.println("Cleaned up temporary storage file.");
        } catch (Exception e) {
            System.err.println("Persistence error: " + e.getMessage());
        }

        System.out.println("\nAutomated demonstration completed successfully!");
    }
}
