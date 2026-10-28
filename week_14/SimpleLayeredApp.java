import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.*;

// ==========================================
// 1. Model Layer
// ==========================================
class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Double.compare(item.price, price) == 0 && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return String.format("Item[id=%d, name='%s', price=$%.2f]", id, name, price);
    }
}

// Custom Exception for invalid business actions
class InvalidActionException extends RuntimeException {
    public InvalidActionException(String message) {
        super(message);
    }
}

// ==========================================
// 2. Service Layer (uses ArrayList)
// ==========================================
class ItemService {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (item == null) {
            throw new InvalidActionException("Item cannot be null");
        }
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new InvalidActionException("Item name cannot be blank");
        }
        if (item.getPrice() <= 0) {
            throw new InvalidActionException("Price must be positive (received: " + item.getPrice() + ")");
        }
        for (Item existing : items) {
            if (existing.getId() == item.getId()) {
                throw new InvalidActionException("Duplicate item ID: " + item.getId());
            }
        }
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getItemCount() {
        return items.size();
    }

    public Item findById(int id) {
        for (Item it : items) {
            if (it.getId() == id) return it;
        }
        throw new InvalidActionException("Item not found with id: " + id);
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(new ArrayList<>(items));
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Item> loaded = (List<Item>) ois.readObject();
            items.clear();
            items.addAll(loaded);
        }
    }
}

// ==========================================
// 3. Test Layer with @Test Annotation
// ==========================================
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestMethod {
}

class ItemServiceTests {

    @TestMethod
    public void testAddingIncreasesCount() {
        ItemService service = new ItemService();
        assertCondition(service.getItemCount() == 0, "Initial count should be 0");

        service.addItem(new Item(1, "Notebook", 4.99));
        assertCondition(service.getItemCount() == 1, "Count should be 1 after 1 add");

        service.addItem(new Item(2, "Pen", 1.99));
        assertCondition(service.getItemCount() == 2, "Count should be 2 after 2 adds");
    }

    @TestMethod
    public void testInvalidActionThrows() {
        ItemService service = new ItemService();

        boolean caughtNegativePrice = false;
        try {
            service.addItem(new Item(3, "Invalid Price Item", -10.0));
        } catch (InvalidActionException e) {
            caughtNegativePrice = true;
        }
        assertCondition(caughtNegativePrice, "Adding negative price must throw InvalidActionException");

        service.addItem(new Item(4, "Eraser", 0.99));
        boolean caughtDuplicateId = false;
        try {
            service.addItem(new Item(4, "Duplicate Eraser", 0.99));
        } catch (InvalidActionException e) {
            caughtDuplicateId = true;
        }
        assertCondition(caughtDuplicateId, "Adding duplicate ID must throw InvalidActionException");
    }

    @TestMethod
    public void testSaveAndReloadDataUnchanged() throws Exception {
        String testFile = "item_test_storage.ser";
        ItemService service = new ItemService();
        Item item1 = new Item(10, "Desk Lamp", 24.50);
        Item item2 = new Item(20, "Stapler", 8.20);
        service.addItem(item1);
        service.addItem(item2);

        service.saveToFile(testFile);

        ItemService loadedService = new ItemService();
        loadedService.loadFromFile(testFile);

        assertCondition(loadedService.getItemCount() == 2, "Reloaded count must match 2");
        assertCondition(loadedService.getItems().get(0).equals(item1), "Item 1 must match unchanged");
        assertCondition(loadedService.getItems().get(1).equals(item2), "Item 2 must match unchanged");

        new File(testFile).delete();
    }

    private static void assertCondition(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError("Test Failed: " + msg);
        }
    }
}

// ==========================================
// 4. Main Menu Layer (Controller / UI)
// ==========================================
public class SimpleLayeredApp {

    public static void main(String[] args) {
        System.out.println("=== Week 14: Layered Architecture & Testing Demo ===\n");

        // 1. Run Unit Tests First
        runTests();

        // 2. Demo the Layered Application
        ItemService service = new ItemService();
        System.out.println("\n--- Running Menu / Service Operations ---");
        service.addItem(new Item(101, "Java Book", 45.00));
        service.addItem(new Item(102, "Coffee Mug", 12.50));
        service.addItem(new Item(103, "USB Cable", 7.99));

        System.out.println("Items in catalog (" + service.getItemCount() + " items):");
        for (Item item : service.getItems()) {
            System.out.println(" - " + item);
        }

        System.out.println("\nTesting search by id (102): " + service.findById(102));

        try {
            System.out.println("Testing invalid action (price = -5.0)...");
            service.addItem(new Item(104, "Invalid", -5.0));
        } catch (InvalidActionException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        System.out.println("\nLayered App execution finished successfully.");
    }

    private static void runTests() {
        System.out.println("--- Executing @Test Unit Tests ---");
        ItemServiceTests tests = new ItemServiceTests();
        Method[] methods = ItemServiceTests.class.getDeclaredMethods();
        int passed = 0, total = 0;

        for (Method m : methods) {
            if (m.isAnnotationPresent(TestMethod.class)) {
                total++;
                try {
                    m.invoke(tests);
                    System.out.println("  [PASS] " + m.getName() + "()");
                    passed++;
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    System.out.println("  [FAIL] " + m.getName() + "() -> " + cause.getMessage());
                }
            }
        }
        System.out.printf("Tests Finished: %d/%d passed.%n", passed, total);
    }
}
