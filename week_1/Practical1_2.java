import java.util.*;

public class Practical1_2 {

    public static int Vehicle(int number, String type) {
        int n = 0;
        if (type.equals("B")) {
            n = number * 20;
        } else if (type.equals("C")) {
            n = number * 50;
        } else if (type.equals("T")) {
            n = number * 150;
        }
        return n;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;

        while (true) {
            System.out.print("Bike : B | Car : C | Truck : T | Exit : Q ");
            String veh = sc.nextLine();

            if (veh.equals("Q")) break;

            System.out.print("Enter number of vehicles: ");
            int num = sc.nextInt();
            sc.nextLine();

            sum += Vehicle(num, veh);
        }

        System.out.println("Total Toll Collected: " + sum);
        sc.close();
    }
}
