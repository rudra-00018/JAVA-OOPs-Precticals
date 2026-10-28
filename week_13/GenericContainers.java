import java.util.Arrays;
import java.util.NoSuchElementException;

public class GenericContainers {

    // 1. Generic Box<T> container with get/set
    static class Box<T> {
        private T content;

        public Box() {
            this.content = null;
        }

        public Box(T content) {
            this.content = content;
        }

        public void set(T content) {
            this.content = content;
        }

        public T get() {
            return content;
        }

        public boolean isEmpty() {
            return content == null;
        }

        @Override
        public String toString() {
            return "Box[" + (content != null ? content.toString() : "empty") + "]";
        }
    }

    // 2. Generic Pair<A, B> container
    static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return String.format("Pair(first=%s, second=%s)", first, second);
        }
    }

    // 3. Generic method returning the first element of any array
    public static <T> T getFirst(T[] array) {
        if (array == null || array.length == 0) {
            throw new NoSuchElementException("Array is empty or null; no first element exists.");
        }
        return array[0]; // Guaranteed type T without explicit cast
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Generic Containers (Box<T>, Pair<A,B> and Generic Method) ===\n");

        // --- Demo Box<T> with type safety without casting ---
        System.out.println("--- 1. Testing Generic Box<T> ---");
        Box<String> stringBox = new Box<>("Hello, Generics!");
        Box<Integer> intBox = new Box<>(42);
        Box<Double> doubleBox = new Box<>(3.14159);

        // Direct assignment without explicit casts:
        String strVal = stringBox.get();   // No (String) cast needed
        Integer intVal = intBox.get();     // No (Integer) cast needed
        Double dblVal = doubleBox.get();   // No (Double) cast needed

        System.out.println("String Box : " + stringBox + " -> Content: \"" + strVal + "\" (length: " + strVal.length() + ")");
        System.out.println("Integer Box: " + intBox + " -> Content: " + intVal + " (squared: " + (intVal * intVal) + ")");
        System.out.println("Double Box : " + doubleBox + " -> Content: " + dblVal);

        // Mutating Box content
        stringBox.set("Updated generic string");
        System.out.println("Updated String Box: " + stringBox.get());

        // Note on compile-time type safety:
        // stringBox.set(100); // Compile-time error: The method set(String) is not applicable for (int)
        System.out.println("Type safety verified: Box<String> guarantees only String objects are accepted at compile time.\n");

        // --- Demo Pair<A, B> with different type pairs ---
        System.out.println("--- 2. Testing Generic Pair<A, B> ---");
        Pair<String, Integer> studentScore = new Pair<>("Alice", 95);
        Pair<Integer, String> httpStatus = new Pair<>(404, "Not Found");
        Pair<String, Boolean> featureToggle = new Pair<>("darkMode", true);

        // Accessing first and second with strong types
        String name = studentScore.getFirst();
        Integer score = studentScore.getSecond();
        System.out.printf("Student Score   : %s -> Name: %s, Score: %d%n", studentScore, name, score);
        System.out.printf("HTTP Status     : %s -> Code: %d, Message: %s%n", httpStatus, httpStatus.getFirst(), httpStatus.getSecond());
        System.out.printf("Feature Toggle  : %s -> Feature: %s, Enabled: %b%n%n", featureToggle, featureToggle.getFirst(), featureToggle.getSecond());

        // --- Demo Generic Method: getFirst(T[] array) ---
        System.out.println("--- 3. Testing Generic Method getFirst(T[] array) ---");
        String[] words = { "First", "Second", "Third" };
        Integer[] numbers = { 100, 200, 300, 400 };
        Character[] letters = { 'A', 'B', 'C' };

        String firstWord = getFirst(words);
        Integer firstNumber = getFirst(numbers);
        Character firstLetter = getFirst(letters);

        System.out.println("Array " + Arrays.toString(words) + " -> getFirst(): \"" + firstWord + "\"");
        System.out.println("Array " + Arrays.toString(numbers) + " -> getFirst(): " + firstNumber);
        System.out.println("Array " + Arrays.toString(letters) + " -> getFirst(): '" + firstLetter + "'");

        // Edge case: Empty array
        try {
            String[] empty = new String[0];
            getFirst(empty);
        } catch (NoSuchElementException e) {
            System.out.println("Empty array handled sensibly: " + e.getMessage());
        }
    }
}
