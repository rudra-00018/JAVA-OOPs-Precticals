import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class LogAnalyzer {

    public static void main(String[] args) {
        System.out.println("=== 2. Log Analyzer with NIO Path and Files ===");
        String keyword = "ERROR";

        // Define filenames for sample logs
        List<String> fileNames = Arrays.asList("app.log", "auth.log", "database.log");

        // 1. Create sample log files with realistic content
        createSampleLogs(fileNames);

        long grandTotalLines = 0;
        long grandTotalKeywordMatches = 0;

        System.out.println("\nAnalyzing log files for keyword: \"" + keyword + "\"\n");
        System.out.printf("%-16s | %-12s | %-12s | %-15s%n", "Filename", "File Size", "Total Lines", "Keyword Matches");
        System.out.println("------------------------------------------------------------------");

        // 2. Read each file line-by-line using Path and Files
        for (String fileName : fileNames) {
            Path path = Paths.get(fileName);
            if (!Files.exists(path)) {
                System.err.println("File not found: " + fileName);
                continue;
            }

            try {
                // Read file attributes (size, modified time)
                BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                long fileSize = attrs.size();

                long fileLines = 0;
                long keywordMatches = 0;

                // Read line-by-line using Files.newBufferedReader
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileLines++;
                        if (line.contains(keyword)) {
                            keywordMatches++;
                        }
                    }
                }

                grandTotalLines += fileLines;
                grandTotalKeywordMatches += keywordMatches;

                System.out.printf("%-16s | %10d B | %12d | %15d%n", 
                    fileName, fileSize, fileLines, keywordMatches);

            } catch (IOException e) {
                System.err.println("Error reading file " + fileName + ": " + e.getMessage());
            }
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-16s | %-12s | %12d | %15d%n", 
            "TOTALS", "--", grandTotalLines, grandTotalKeywordMatches);

        System.out.println("\n--- Summary ---");
        System.out.printf("Total lines processed across all files : %d%n", grandTotalLines);
        System.out.printf("Total lines containing '%s'            : %d%n", keyword, grandTotalKeywordMatches);
    }

    private static void createSampleLogs(List<String> fileNames) {
        try {
            // app.log
            Files.writeString(Paths.get(fileNames.get(0)),
                "2026-10-28 10:00:01 INFO [App] Application starting up...\n" +
                "2026-10-28 10:00:05 INFO [App] Initializing service beans\n" +
                "2026-10-28 10:01:23 ERROR [App] Failed to load optional plugin config: timeout\n" +
                "2026-10-28 10:02:10 INFO [App] Ready to accept incoming connections\n"
            );

            // auth.log
            Files.writeString(Paths.get(fileNames.get(1)),
                "2026-10-28 10:05:12 INFO [Auth] User 'alice' logged in successfully\n" +
                "2026-10-28 10:06:44 WARN [Auth] Invalid credentials attempt for 'admin'\n" +
                "2026-10-28 10:06:45 ERROR [Auth] Account locked after 5 failed attempts: 'admin'\n" +
                "2026-10-28 10:07:01 ERROR [Auth] OAuth2 token verification failed: expired token\n" +
                "2026-10-28 10:08:15 INFO [Auth] User 'bob' logged out\n"
            );

            // database.log
            Files.writeString(Paths.get(fileNames.get(2)),
                "2026-10-28 09:59:00 INFO [DB] Connection pool created (pool_size=10)\n" +
                "2026-10-28 10:12:30 INFO [DB] Executing query: SELECT * FROM users\n" +
                "2026-10-28 10:15:00 ERROR [DB] Connection lost to secondary replica\n" +
                "2026-10-28 10:15:02 INFO [DB] Reconnection attempt succeeded\n"
            );

            System.out.println("Sample log files generated successfully.");
        } catch (IOException e) {
            System.err.println("Error generating sample logs: " + e.getMessage());
        }
    }
}
