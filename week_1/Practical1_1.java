import java.util.*;

enum Coin{
    ONE,
    TWO,
    FIVE,
    TEN
}

public class Practical1_1 {
    public static void main(String args[]){
        int total = 15;
        int sum = 0;
        Scanner sc = new Scanner(System.in);

        while(sum < total){
            System.out.println("ONE | TWO | FIVE | TEN ");
            String c = sc.nextLine();

            Coin myC = Coin.valueOf(c);

            switch (myC) {
                case ONE:
                    sum += 1;
                    break;

                case TWO:
                    sum += 2;
                    break;

                case FIVE:
                    sum += 5;
                    break;

                case TEN:
                    sum += 10;
                    break;
            
                default:
                    break;
            }
        }

        int last = sum - total;
        System.out.print(last);

        sc.close();
    }
}
