package test;

import exception.InvalidProductException;
import model.Product;
import service.ProductService;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class ProductServiceTest {

    @Test
    public void testAddingIncreasesCount() {
        ProductService service = new ProductService();
        assertEquals(0, service.getProductCount(), "Initial count must be 0");

        service.addProduct(new Product(1, "Laptop", 999.99, 10));
        assertEquals(1, service.getProductCount(), "Count must increase to 1 after first add");

        service.addProduct(new Product(2, "Mouse", 29.99, 50));
        assertEquals(2, service.getProductCount(), "Count must increase to 2 after second add");
    }

    @Test
    public void testInvalidPriceThrowsException() {
        ProductService service = new ProductService();

        boolean thrown = false;
        try {
            // Negative price is invalid action
            service.addProduct(new Product(1, "Defective Item", -45.0, 5));
        } catch (InvalidProductException e) {
            thrown = true;
        }

        assertTrue(thrown, "Adding product with negative price must throw InvalidProductException");
    }

    @Test
    public void testDuplicateIdThrowsException() {
        ProductService service = new ProductService();
        service.addProduct(new Product(101, "Keyboard", 49.99, 20));

        boolean thrown = false;
        try {
            // Duplicate ID 101
            service.addProduct(new Product(101, "Another Keyboard", 59.99, 10));
        } catch (InvalidProductException e) {
            thrown = true;
        }

        assertTrue(thrown, "Adding product with duplicate ID must throw InvalidProductException");
    }

    @Test
    public void testSaveAndReloadDataUnchanged() throws Exception {
        String testFile = "test_catalog.ser";
        ProductService service = new ProductService();

        Product p1 = new Product(1, "Monitor", 199.99, 15);
        Product p2 = new Product(2, "Headset", 79.99, 30);
        Product p3 = new Product(3, "Webcam", 49.99, 25);

        service.addProduct(p1);
        service.addProduct(p2);
        service.addProduct(p3);

        // Save data to file
        service.saveToFile(testFile);

        // Load into fresh service instance
        ProductService freshService = new ProductService();
        freshService.loadFromFile(testFile);

        // Verify count and exact object equivalence
        assertEquals(3, freshService.getProductCount(), "Reloaded count must match saved count");

        List<Product> loadedList = freshService.getAllProducts();
        assertEquals(p1, loadedList.get(0), "Product 1 must match exactly after reload");
        assertEquals(p2, loadedList.get(1), "Product 2 must match exactly after reload");
        assertEquals(p3, loadedList.get(2), "Product 3 must match exactly after reload");

        // Clean up test file
        new File(testFile).delete();
    }

    // --- Assertions & Test Runner ---

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError("Assertion Failed: " + message + " [Expected: " + expected + ", Actual: " + actual + "]");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion Failed: " + message);
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("         RUNNING ProductService UNIT TEST SUITE           ");
        System.out.println("==========================================================");

        ProductServiceTest testSuite = new ProductServiceTest();
        Method[] methods = ProductServiceTest.class.getDeclaredMethods();

        int passed = 0;
        int failed = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                String testName = method.getName();
                try {
                    method.invoke(testSuite);
                    System.out.printf("  [PASS] %s()%n", testName);
                    passed++;
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    System.out.printf("  [FAIL] %s() -> %s%n", testName, cause.getMessage());
                    failed++;
                }
            }
        }

        System.out.println("==========================================================");
        System.out.printf("Results: %d Passed, %d Failed (Total: %d)%n", passed, failed, (passed + failed));
        if (failed == 0) {
            System.out.println("ALL UNIT TESTS PASSED SUCCESSFULLY!");
        } else {
            System.err.println("SOME TESTS FAILED!");
        }
        System.out.println("==========================================================");
    }
}
