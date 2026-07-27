import java.util.Scanner;

public class Practicle1_1 {
    enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        final int snackPrice = 15;
        int total = 0;
        Scanner scanner = new Scanner(System.in);

        while (total < snackPrice) {
            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            Coin coin = Coin.valueOf(scanner.nextLine().trim().toUpperCase());

            int value = switch (coin) {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };

            total += value;
            System.out.println("Total so far: " + total);
        }

        System.out.println("Paid. Change: " + (total - snackPrice));
        scanner.close();
    }
}