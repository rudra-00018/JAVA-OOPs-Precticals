import java.util.Scanner;

public class Practicle1_2 {
    record Vehicle(String number, String type) {
    }

    public static void main(String[] args) {
        int totalToll = 0;
        int bikes = 0;
        int cars = 0;
        int trucks = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter vehicle number (or done): ");
            String number = scanner.nextLine().trim();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike, car, truck): ");
            String type = scanner.nextLine().trim().toLowerCase();
            Vehicle vehicle = new Vehicle(number, type);

            int toll = switch (vehicle.type()) {
                case "bike" -> {
                    bikes++;
                    yield 20;
                }
                case "car" -> {
                    cars++;
                    yield 50;
                }
                case "truck" -> {
                    trucks++;
                    yield 150;
                }
                default -> throw new IllegalArgumentException("Unknown vehicle type: " + vehicle.type());
            };

            totalToll += toll;
        }

        String mostFrequent = "bike";
        int highestCount = bikes;
        if (cars > highestCount) {
            mostFrequent = "car";
            highestCount = cars;
        }
        if (trucks > highestCount) {
            mostFrequent = "truck";
        }

        System.out.println("Total toll: " + totalToll);
        System.out.println("Most frequent: " + mostFrequent);
        scanner.close();
    }
}
