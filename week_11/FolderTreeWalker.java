import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FolderTreeWalker {

    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    static class FileRecord {
        String relativePath;
        String type;
        long size;
        String lastModified;

        public FileRecord(String relativePath, String type, long size, String lastModified) {
            this.relativePath = relativePath;
            this.type = type;
            this.size = size;
            this.lastModified = lastModified;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 3. Folder Tree Walker with NIO (Advanced Learner) ===");

        // Target path can be passed as args[0] or default to a generated sample folder tree
        Path targetDir = (args.length > 0) ? Paths.get(args[0]) : Paths.get("sample_directory");

        // Setup sample nested folder structure if using default
        if (targetDir.toString().equals("sample_directory")) {
            createSampleFolderStructure(targetDir);
        }

        System.out.println("Walking folder tree rooted at: " + targetDir.toAbsolutePath());
        String reportFileName = "folder_summary_report.txt";

        List<FileRecord> records = new ArrayList<>();
        final long[] totals = new long[3]; // [totalDirs, totalFiles, totalBytes]

        try {
            Files.walkFileTree(targetDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    totals[0]++;
                    String relPath = targetDir.relativize(dir).toString();
                    if (relPath.isEmpty()) {
                        relPath = ".";
                    }
                    String modified = DATE_FORMATTER.format(attrs.lastModifiedTime().toInstant());
                    records.add(new FileRecord(relPath + "/", "DIR", 0, modified));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    totals[1]++;
                    totals[2] += attrs.size();
                    String relPath = targetDir.relativize(file).toString();
                    String modified = DATE_FORMATTER.format(attrs.lastModifiedTime().toInstant());
                    records.add(new FileRecord(relPath, "FILE", attrs.size(), modified));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.err.println("Could not visit " + file + ": " + exc.getMessage());
                    return FileVisitResult.CONTINUE;
                }
            });

            // Build Report String
            StringBuilder sb = new StringBuilder();
            sb.append("========================================================================================\n");
            sb.append("                               FOLDER TREE SUMMARY REPORT                               \n");
            sb.append("========================================================================================\n");
            sb.append(String.format("Root Directory : %s%n", targetDir.toAbsolutePath()));
            sb.append(String.format("Generated At   : %s%n%n", DATE_FORMATTER.format(Instant.now())));
            sb.append(String.format("%-50s | %-6s | %10s | %-19s%n", "Relative Path", "Type", "Size (B)", "Last Modified"));
            sb.append("----------------------------------------------------------------------------------------\n");

            for (FileRecord rec : records) {
                sb.append(String.format("%-50s | %-6s | %10d | %-19s%n",
                        rec.relativePath, rec.type, rec.size, rec.lastModified));
            }

            sb.append("----------------------------------------------------------------------------------------\n");
            sb.append(String.format("Totals: %d directories, %d files, %,d bytes total.%n", 
                    totals[0], totals[1], totals[2]));
            sb.append("========================================================================================\n");

            String reportText = sb.toString();

            // Print report to console
            System.out.println(reportText);

            // Write summary to file
            Path reportPath = Paths.get(reportFileName);
            Files.writeString(reportPath, reportText);
            System.out.println("Report successfully written to file: " + reportPath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error walking folder tree: " + e.getMessage());
        }
    }

    private static void createSampleFolderStructure(Path root) {
        try {
            Files.createDirectories(root.resolve("docs"));
            Files.createDirectories(root.resolve("src/utils"));
            Files.createDirectories(root.resolve("config"));

            Files.writeString(root.resolve("README.md"), "# Sample Project\nThis is a sample project for NIO walk.");
            Files.writeString(root.resolve("docs/manual.pdf.txt"), "Documentation contents placeholder.");
            Files.writeString(root.resolve("src/Main.java"), "public class Main { public static void main(String[] a){} }");
            Files.writeString(root.resolve("src/utils/Helper.java"), "public class Helper { public static void help(){} }");
            Files.writeString(root.resolve("config/app.properties"), "env=production\nport=8080\nthreads=4");

        } catch (IOException e) {
            System.err.println("Notice: Sample folder structure already exists or had an error: " + e.getMessage());
        }
    }
}
