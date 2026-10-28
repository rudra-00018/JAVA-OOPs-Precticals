import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequency {

    public static void main(String[] args) {
        System.out.println("=== 1. Word Frequency Counter with HashMap and Comparator ===");

        String paragraph = 
            "Java is a versatile programming language. Java is widely used in enterprise software development! " +
            "Programming in Java is powerful, elegant, and efficient. Collections in Java make programming easier, " +
            "and Java collections provide high performance data structures for every software project.";

        System.out.println("\n--- Input Paragraph ---");
        System.out.println(paragraph);

        // 1. Sanitize text: remove punctuation, lowercase, split by whitespace
        String sanitized = paragraph.replaceAll("[^a-zA-Z0-9\\s]", " ").toLowerCase();
        String[] words = sanitized.split("\\s+");

        // 2. Count frequencies using HashMap
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            if (!word.isBlank()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }

        // 3. Convert entries to List for custom sorting
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());

        // 4. Sort using Comparator: highest frequency first; ties broken alphabetically by word
        Comparator<Map.Entry<String, Integer>> frequencyComparator = new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                int freqCompare = e2.getValue().compareTo(e1.getValue()); // Descending order
                if (freqCompare != 0) {
                    return freqCompare;
                }
                return e1.getKey().compareTo(e2.getKey()); // Ascending alphabetically for ties
            }
        };

        Collections.sort(entryList, frequencyComparator);

        // 5. Print sorted words
        System.out.println("\n--- Words Sorted by Frequency (Highest First) ---");
        System.out.printf("%-4s | %-16s | %-10s | %s%n", "Rank", "Word", "Frequency", "Distribution");
        System.out.println("---------------------------------------------------------");

        int rank = 1;
        for (Map.Entry<String, Integer> entry : entryList) {
            String bar = "*".repeat(entry.getValue() * 2);
            System.out.printf("%-4d | %-16s | %-10d | %s%n", 
                rank++, entry.getKey(), entry.getValue(), bar);
        }

        System.out.println("---------------------------------------------------------");
        System.out.println("Total distinct words : " + entryList.size());
        System.out.println("Total word count     : " + words.length);
    }
}
