import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueVisitors {

    public static void main(String[] args) {
        System.out.println("=== 2. Unique Visitors with HashSet and LinkedHashSet ===");

        // List of incoming logins with deliberate repeats
        List<String> logins = Arrays.asList(
            "alice", "bob", "charlie", "alice", "david", 
            "bob", "emma", "alice", "frank", "david", "grace"
        );

        System.out.println("\n--- Raw Login Stream (" + logins.size() + " total login events) ---");
        System.out.println(logins);

        // 1. HashSet: used to compute unique users (efficient, unordered)
        Set<String> hashSet = new HashSet<>(logins);

        // 2. LinkedHashSet: preserves the exact first-seen insertion order
        Set<String> linkedHashSet = new LinkedHashSet<>();
        for (String user : logins) {
            linkedHashSet.add(user);
        }

        // Print unique count
        System.out.println("\n--- Results ---");
        System.out.println("Total login events : " + logins.size());
        System.out.println("Unique users count : " + hashSet.size());

        // Print first-seen order
        System.out.println("\nAll unique users in FIRST-SEEN order (LinkedHashSet):");
        System.out.println(linkedHashSet);

        // 3. Print the first three distinct names in first-seen order
        System.out.println("\nFirst three distinct visitors:");
        Iterator<String> iterator = linkedHashSet.iterator();
        int count = 0;
        while (iterator.hasNext() && count < 3) {
            count++;
            System.out.printf("  %d. %s%n", count, iterator.next());
        }

        // Difference demonstration:
        System.out.println("\n--- Order Comparison ---");
        System.out.println("HashSet (arbitrary hash order)      : " + hashSet);
        System.out.println("LinkedHashSet (exact insertion order): " + linkedHashSet);
    }
}
