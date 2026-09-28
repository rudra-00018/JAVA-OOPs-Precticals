import java.util.*;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class Practical6_3 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Double> prices = Arrays.asList(
                1000.0,
                2000.0,
                500.0,
                3000.0
        );

        System.out.println("Choose Discount Rule:");
        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. Flat Rs. 100 Discount");

        int choice = sc.nextInt();

        DiscountRule rule;

        if (choice == 1) {

            rule = price -> price - (price * 0.10);

        } else if (choice == 2) {

            rule = price -> price - (price * 0.20);

        } else if (choice == 3) {

            rule = price -> price - 100;

        } else {

            System.out.println("Invalid choice");
            sc.close();
            return;
        }

        System.out.println("\nOriginal Price -> Discounted Price");

        for (double price : prices) {

            double discountedPrice = rule.apply(price);

            System.out.println(
                    price + " -> " + discountedPrice
            );
        }

        sc.close();
    }
}