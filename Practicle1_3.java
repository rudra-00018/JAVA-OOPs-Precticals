import java.util.Scanner;

public class Practicle1_3 {
    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    public static int winner(Move a, Move b) {
        if (a == b) {
            return 0;
        }

        return switch (a) {
            case ROCK -> (b == Move.SCISSORS || b == Move.LIZARD) ? 1 : -1;
            case PAPER -> (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;
            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;
            case LIZARD -> (b == Move.PAPER || b == Move.SPOCK) ? 1 : -1;
            case SPOCK -> (b == Move.ROCK || b == Move.SCISSORS) ? 1 : -1;
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Move[] moves = Move.values();
        int playerScore = 0;
        int computerScore = 0;

        for (int round = 1; round <= 5; round++) {
            System.out.print("Round " + round + " - enter ROCK, PAPER, SCISSORS, LIZARD, or SPOCK: ");
            Move player = Move.valueOf(scanner.nextLine().trim().toUpperCase());
            Move computer = moves[(int) (Math.random() * moves.length)];
            int result = winner(player, computer);

            System.out.println("You: " + player + ", Computer: " + computer);
            if (result == 1) {
                playerScore++;
                System.out.println("You win this round.");
            } else if (result == -1) {
                computerScore++;
                System.out.println("Computer wins this round.");
            } else {
                System.out.println("Round tied.");
            }
        }

        if (playerScore > computerScore) {
            System.out.println("You win " + playerScore + "-" + computerScore);
        } else if (computerScore > playerScore) {
            System.out.println("Computer wins " + computerScore + "-" + playerScore);
        } else {
            System.out.println("Overall tie: " + playerScore + "-" + computerScore);
        }
        scanner.close();
    }
}
