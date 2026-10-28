import java.util.Arrays;
import java.util.EmptyStackException;

public class GenericStack {

    /**
     * Generic Stack container backed by an internal resizable Object array.
     */
    static class ArrayStack<E> {
        private static final int DEFAULT_CAPACITY = 4;
        private Object[] elements;
        private int size;

        public ArrayStack() {
            this(DEFAULT_CAPACITY);
        }

        public ArrayStack(int initialCapacity) {
            if (initialCapacity <= 0) {
                throw new IllegalArgumentException("Initial capacity must be greater than 0");
            }
            this.elements = new Object[initialCapacity];
            this.size = 0;
        }

        public void push(E item) {
            if (item == null) {
                throw new IllegalArgumentException("Cannot push null element to stack.");
            }
            ensureCapacity();
            elements[size++] = item;
        }

        @SuppressWarnings("unchecked")
        public E pop() {
            // Sensibly handle empty stack case
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            int topIndex = --size;
            E item = (E) elements[topIndex];
            elements[topIndex] = null; // Prevent memory leak by enabling garbage collection
            return item;
        }

        @SuppressWarnings("unchecked")
        public E peek() {
            // Sensibly handle empty stack case
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return (E) elements[size - 1];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        private void ensureCapacity() {
            if (size == elements.length) {
                int newCapacity = elements.length * 2;
                elements = Arrays.copyOf(elements, newCapacity);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < size; i++) {
                sb.append(elements[i]);
                if (i < size - 1) sb.append(", ");
            }
            sb.append("] (top is at right, size=").append(size).append(")");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 3. Generic Array-Backed Stack (Advanced Learner) ===\n");

        // -------------------------------------------------------------
        // Case 1: Reusing ArrayStack with String
        // -------------------------------------------------------------
        System.out.println("--- 1. Testing ArrayStack<String> (Browser History) ---");
        ArrayStack<String> historyStack = new ArrayStack<>(2); // small initial capacity to test auto-growth

        System.out.println("Pushing URLs onto history stack...");
        historyStack.push("https://google.com");
        historyStack.push("https://github.com");
        historyStack.push("https://stackoverflow.com"); // triggers capacity expansion
        historyStack.push("https://developer.mozilla.org");

        System.out.println("Current Stack: " + historyStack);
        System.out.println("Top URL (peek): " + historyStack.peek());

        System.out.println("\nPopping URLs (Back navigation):");
        while (!historyStack.isEmpty()) {
            System.out.println("  Popped: " + historyStack.pop() + " | Remaining size: " + historyStack.size());
        }

        // Test empty case handling for String stack
        System.out.println("\nTesting empty stack handling for historyStack:");
        try {
            historyStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("  Handled sensibly! EmptyStackException caught when calling pop() on empty stack.");
        }

        // -------------------------------------------------------------
        // Case 2: Reusing the same generic ArrayStack with Integer
        // -------------------------------------------------------------
        System.out.println("\n--- 2. Testing ArrayStack<Integer> (Undo Actions / Calculation) ---");
        ArrayStack<Integer> numberStack = new ArrayStack<>(3);

        System.out.println("Pushing integers: 10, 25, 50, 100...");
        numberStack.push(10);
        numberStack.push(25);
        numberStack.push(50);
        numberStack.push(100);

        System.out.println("Current Stack: " + numberStack);
        System.out.println("Top element (peek): " + numberStack.peek());

        int poppedValue = numberStack.pop();
        System.out.println("Popped value: " + poppedValue + " (as int, no cast required)");
        System.out.println("Stack after pop: " + numberStack);

        // Test empty case handling for Integer stack
        System.out.println("\nEmptying the stack...");
        while (!numberStack.isEmpty()) {
            numberStack.pop();
        }

        System.out.println("Stack is now empty: " + numberStack.isEmpty());
        try {
            numberStack.peek();
        } catch (EmptyStackException e) {
            System.out.println("  Handled sensibly! EmptyStackException caught when calling peek() on empty stack.");
        }

        System.out.println("\nSuccess: The generic ArrayStack<E> proved fully reusable across different types with robust empty-case protection!");
    }
}
