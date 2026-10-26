
import java.util.*;
import java.util.Objects;

public class Practical3_2 {
    static class Card {
        private String rank;
        private String suit;

        Card(String rank, String suit) {
            this.rank = rank;
            this.suit = suit;
        }

        @Override
        public String toString() {
            return "(" + this.rank + ", " + this.suit + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Card other = (Card) obj;
            return Objects.equals(this.rank, other.rank) && Objects.equals(this.suit, other.suit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.rank, this.suit);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter No of cards : ");
        int n = sc.nextInt();
        sc.nextLine();

        Card c[] = new Card[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            boolean check = false;
            System.out.println("Enter rank for card " + (i + 1) + ": ");
            String r = sc.nextLine();
            System.out.println("Enter suit for card " + (i + 1) + ": ");
            String s = sc.nextLine();
            c[i] = new Card(r, s);

            for (int j = 0; j < i; j++) {
                if (c[i].equals(c[j])) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                count++;
            }
        }
        System.out.println("Total unique cards: " + count);
        sc.close();
    }
}
