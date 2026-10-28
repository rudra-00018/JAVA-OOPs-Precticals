import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveLoadDemo {

    // Serializable class representing a student/user
    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int id;
        private final String name;
        private final double score;
        
        // Transient field: should NOT be serialized
        private final transient String secretToken;

        public Student(int id, String name, double score, String secretToken) {
            this.id = id;
            this.name = name;
            this.score = score;
            this.secretToken = secretToken;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getScore() {
            return score;
        }

        public String getSecretToken() {
            return secretToken;
        }

        @Override
        public String toString() {
            return String.format("Student[id=%d, name='%s', score=%.1f, secretToken='%s']",
                    id, name, score, (secretToken == null ? "<null / NOT SAVED>" : secretToken));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Save/Load Object Serialization Demo ===");
        String filename = "students_data.ser";

        // Create an array of objects
        Student[] originalStudents = new Student[] {
            new Student(101, "Alice", 92.5, "pass#Alice101"),
            new Student(102, "Bob", 88.0, "pass#Bob102"),
            new Student(103, "Charlie", 95.0, "pass#Charlie103")
        };

        System.out.println("\n--- Original Objects (Before Serialization) ---");
        for (Student s : originalStudents) {
            System.out.println("  " + s);
        }

        // 1. Save objects to file using ObjectOutputStream
        System.out.println("\nSaving array of Student objects to file: " + filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(originalStudents);
            System.out.println("Objects saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving objects: " + e.getMessage());
            return;
        }

        // 2. Read objects back using ObjectInputStream
        System.out.println("\nReading objects back from file into fresh objects...");
        Student[] deserializedStudents;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            deserializedStudents = (Student[]) ois.readObject();
            System.out.println("Objects loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading objects: " + e.getMessage());
            return;
        }

        // 3. Confirm values survived and transient field is null
        System.out.println("\n--- Restored Objects (After Deserialization) ---");
        boolean allSurvived = true;
        boolean allTransientNull = true;

        for (int i = 0; i < deserializedStudents.length; i++) {
            Student orig = originalStudents[i];
            Student loaded = deserializedStudents[i];

            System.out.println("  " + loaded);

            if (loaded.getId() != orig.getId() || 
                !loaded.getName().equals(orig.getName()) || 
                Double.compare(loaded.getScore(), orig.getScore()) != 0) {
                allSurvived = false;
            }

            if (loaded.getSecretToken() != null) {
                allTransientNull = false;
            }
        }

        System.out.println("\n--- Verification Summary ---");
        System.out.println("1. Regular fields (id, name, score) survived: " + 
            (allSurvived ? "YES (CONFIRMED)" : "NO (FAILED)"));
        System.out.println("2. Transient field (secretToken) was NOT saved: " + 
            (allTransientNull ? "YES (CONFIRMED - all null)" : "NO (FAILED)"));

        // Clean up the temporary serialized file
        File file = new File(filename);
        if (file.exists() && file.delete()) {
            System.out.println("\nCleaned up temporary file: " + filename);
        }
    }
}
