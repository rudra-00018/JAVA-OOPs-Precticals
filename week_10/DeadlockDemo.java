import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadlockDemo {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        System.out.println("=== 3. Deadlock Demonstration (Advanced Learner) ===");
        System.out.println("Demonstrating circular wait condition with two locks acquired in reverse order.\n");

        // Thread 1: Acquires LOCK_A first, then tries to acquire LOCK_B
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("[Thread-1] Acquired LOCK_A.");
                try {
                    // Small sleep ensures Thread-2 has time to acquire LOCK_B
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("[Thread-1] Waiting to acquire LOCK_B...");
                synchronized (LOCK_B) {
                    System.out.println("[Thread-1] Acquired LOCK_B!");
                }
            }
        }, "Worker-Thread-1");

        // Thread 2: Acquires LOCK_B first, then tries to acquire LOCK_A
        Thread thread2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("[Thread-2] Acquired LOCK_B.");
                try {
                    // Small sleep ensures Thread-1 has time to acquire LOCK_A
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("[Thread-2] Waiting to acquire LOCK_A...");
                synchronized (LOCK_A) {
                    System.out.println("[Thread-2] Acquired LOCK_A!");
                }
            }
        }, "Worker-Thread-2");

        // Start both threads to initiate the deadlock
        thread1.start();
        thread2.start();

        // Deadlock Watchdog using JVM's ThreadMXBean to detect and report deadlock
        Thread watchdog = new Thread(() -> {
            try {
                // Wait for threads to enter deadlock state
                Thread.sleep(1000);
                ThreadMXBean bean = ManagementFactory.getThreadMXBean();
                long[] deadlockedThreadIds = bean.findDeadlockedThreads();

                if (deadlockedThreadIds != null && deadlockedThreadIds.length > 0) {
                    System.out.println("\n-----------------------------------------------------");
                    System.out.println("!!! DEADLOCK DETECTED BY JVM ThreadMXBean !!!");
                    System.out.println("-----------------------------------------------------");
                    ThreadInfo[] infos = bean.getThreadInfo(deadlockedThreadIds);
                    for (ThreadInfo info : infos) {
                        System.out.printf("Deadlocked Thread: '%s' (State: %s)%n", 
                            info.getThreadName(), info.getThreadState());
                        System.out.printf("  -> Waiting on lock: %s%n", info.getLockName());
                        System.out.printf("  -> Held by thread: %s%n", info.getLockOwnerName());
                    }
                    System.out.println("-----------------------------------------------------");
                    System.out.println("Conclusion: Deadlock successfully reproduced! Neither thread can proceed.");
                    System.out.println("Watchdog will now exit to prevent terminal hang.");
                    System.exit(0);
                } else {
                    System.out.println("No deadlock detected within timeout.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Deadlock-Watchdog");

        watchdog.setDaemon(true);
        watchdog.start();

        try {
            thread1.join(3000);
            thread2.join(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
