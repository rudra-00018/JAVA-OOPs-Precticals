import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducerConsumer {

    static class BoundedBuffer {
        private final Queue<Integer> buffer = new LinkedList<>();
        private final int capacity;

        public BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int value) throws InterruptedException {
            while (buffer.size() == capacity) {
                System.out.println("[Producer] Buffer is FULL (capacity " + capacity + "). Waiting for consumer...");
                wait();
            }
            buffer.add(value);
            System.out.printf("[Producer] Produced: %2d | Buffer state: %s (size: %d)%n", 
                value, buffer, buffer.size());
            notifyAll(); // Signal consumer waiting on empty buffer
        }

        public synchronized int consume() throws InterruptedException {
            while (buffer.isEmpty()) {
                System.out.println("[Consumer] Buffer is EMPTY. Waiting for producer...");
                wait();
            }
            int value = buffer.poll();
            System.out.printf("[Consumer] Consumed: %2d | Buffer state: %s (size: %d)%n", 
                value, buffer, buffer.size());
            notifyAll(); // Signal producer waiting on full buffer
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 2. Producer-Consumer Demo (wait / notify) ===");
        int bufferCapacity = 3;
        int totalItems = 10;
        BoundedBuffer buffer = new BoundedBuffer(bufferCapacity);
        List<Integer> consumedList = new ArrayList<>();

        System.out.printf("Buffer Capacity: %d, Total items to transfer: %d%n%n", bufferCapacity, totalItems);

        // Producer Thread
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.produce(i);
                    Thread.sleep(80); // Small delay to simulate production rate
                }
                System.out.println("--> Producer finished all items.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer interrupted: " + e.getMessage());
            }
        }, "Producer-Thread");

        // Consumer Thread
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    int val = buffer.consume();
                    consumedList.add(val);
                    Thread.sleep(150); // Slightly slower to test buffer full behavior
                }
                System.out.println("--> Consumer finished all items.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer interrupted: " + e.getMessage());
            }
        }, "Consumer-Thread");

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        System.out.println("\n--- Verification ---");
        System.out.println("Total items consumed: " + consumedList.size());
        System.out.println("Consumed items in order: " + consumedList);

        boolean inOrder = true;
        for (int i = 0; i < totalItems; i++) {
            if (consumedList.get(i) != i + 1) {
                inOrder = false;
                break;
            }
        }

        if (inOrder && consumedList.size() == totalItems) {
            System.out.println("SUCCESS: All " + totalItems + " items produced were consumed strictly in order with zero loss!");
        } else {
            System.err.println("FAILURE: Items were lost or received out of order.");
        }
    }
}
