import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {

    static class Task implements Runnable {
        private final int taskId;
        private final Set<String> observedThreads;

        public Task(int taskId, Set<String> observedThreads) {
            this.taskId = taskId;
            this.observedThreads = observedThreads;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            observedThreads.add(threadName);
            System.out.printf("[Task %2d] STARTED  by %s%n", taskId, threadName);
            try {
                // Sleep briefly to simulate work
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.printf("[Task %2d] was interrupted%n", taskId);
            }
            System.out.printf("[Task %2d] FINISHED by %s%n", taskId, threadName);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Thread-Pool Runner Demo ===");
        int poolSize = 3;
        int totalTasks = 10;

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        Set<String> observedThreads = Collections.synchronizedSet(new HashSet<>());

        System.out.printf("Initialized fixed thread pool with %d threads.%n", poolSize);
        System.out.printf("Submitting %d tasks...%n%n", totalTasks);

        for (int i = 1; i <= totalTasks; i++) {
            executor.submit(new Task(i, observedThreads));
        }

        // Initiate orderly shutdown
        executor.shutdown();
        System.out.println("All tasks submitted. ExecutorService shutdown initiated.\n");

        try {
            // Await completion of all tasks
            boolean completed = executor.awaitTermination(10, TimeUnit.SECONDS);
            if (completed) {
                System.out.println("\nAll tasks completed successfully.");
            } else {
                System.out.println("\nTimed out waiting for tasks to finish.");
            }
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted while waiting: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Summary of observed threads being reused
        System.out.println("\n--- Thread Reuse Observation ---");
        System.out.println("Unique threads used across " + totalTasks + " tasks: " + observedThreads.size());
        for (String tName : observedThreads) {
            System.out.println(" - " + tName);
        }
        System.out.println("Notice how all " + totalTasks + " tasks were executed using only " 
            + observedThreads.size() + " worker threads through thread reuse.");
    }
}
