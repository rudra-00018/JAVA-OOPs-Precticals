import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardDemo {

    public static void main(String[] args) {
        System.out.println("=== 3. Player Leaderboard (Advanced Learner) ===");

        // Player scores stored in a Map
        Map<String, Integer> playerScores = new HashMap<>();
        playerScores.put("Phoenix", 3400);
        playerScores.put("Apex",    3400); // Tie with Phoenix -> 'Apex' before 'Phoenix'
        playerScores.put("Shadow",  3100);
        playerScores.put("Blaze",   3100); // Tie with Shadow  -> 'Blaze' before 'Shadow'
        playerScores.put("Frost",   2950);
        playerScores.put("Titan",   2950); // Tie with Frost   -> 'Frost' before 'Titan'
        playerScores.put("Viper",   2800);
        playerScores.put("Ghost",   2800); // Tie with Viper   -> 'Ghost' before 'Viper'
        playerScores.put("Nova",    2400);
        playerScores.put("Raven",   1900);

        System.out.println("\n--- Raw Player Scores in Map (" + playerScores.size() + " players) ---");
        playerScores.forEach((name, score) -> System.out.printf("  %-10s : %d pts%n", name, score));

        // Display Top-5 Leaderboard
        int topN = 5;
        printLeaderboard(playerScores, topN);

        // Display Full Leaderboard
        printLeaderboard(playerScores, playerScores.size());
    }

    public static void printLeaderboard(Map<String, Integer> scores, int n) {
        // 1. Convert map entries to a list
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(scores.entrySet());

        // 2. Sort entries by score descending; break ties by player name ascending (alphabetical)
        Comparator<Map.Entry<String, Integer>> leaderboardComparator = new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, Integer> p1, Map.Entry<String, Integer> p2) {
                // Primary: Score descending
                int scoreComparison = p2.getValue().compareTo(p1.getValue());
                if (scoreComparison != 0) {
                    return scoreComparison;
                }
                // Secondary tie-breaker: Name ascending (A to Z)
                return p1.getKey().compareToIgnoreCase(p2.getKey());
            }
        };

        Collections.sort(entries, leaderboardComparator);

        int limit = Math.min(n, entries.size());

        System.out.println("\n========================================================");
        System.out.printf("                TOP-%d PLAYER LEADERBOARD                %n", limit);
        System.out.println("========================================================");
        System.out.printf("%-6s | %-12s | %-10s | %-15s%n", "Rank", "Player", "Score", "Tier Badge");
        System.out.println("--------------------------------------------------------");

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            int rank = i + 1;
            String badge = getBadge(rank);
            System.out.printf("#%-5d | %-12s | %6d pts | %-15s%n", 
                rank, entry.getKey(), entry.getValue(), badge);
        }

        System.out.println("========================================================");
        System.out.println("Rule Note: Scores are sorted descending; ties are broken alphabetically by name.");
    }

    private static String getBadge(int rank) {
        switch (rank) {
            case 1:  return "[Champion]";
            case 2:  return "[Grandmaster]";
            case 3:  return "[Master]";
            case 4:  return "[Diamond]";
            case 5:  return "[Platinum]";
            default: return "[Contender]";
        }
    }
}
