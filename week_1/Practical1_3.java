import java.util.*;
class  Practical1_3
{
    enum Move{ROCK,PAPER,SCISSORS,LIZARD,SPOCK}
    static int winner(Move a, Move b)
    {
        if(a==b)
        return 0;
        switch (a)
        {
            case ROCK:
                if(b==Move.SCISSORS || b==Move.LIZARD)
                return 1;
                else
                return -1;
            case PAPER:
                if(b==Move.ROCK||b==Move.SPOCK)
                return 1;
                else
                return -1;
            case SCISSORS:
                if(b==Move.PAPER||b==Move.LIZARD)
                return 1;
                else
                return -1;
            case LIZARD:
                if(b==Move.PAPER||b==Move.SPOCK)
                return 1;
                else
                return -1;
            case SPOCK:
                if(b==Move.ROCK||b==Move.SCISSORS)
                return 1;
                else
                return -1;
        }
        return 0;
    }

    public static void main(String arg[])
    {
        Scanner sc = new Scanner(System.in);
        Random r=new Random();
        Move moves[]=Move.values();
        int playerScore=0,compScore=0;
        for(int i=1;i<=5;i++)
        {
            System.out.println("Enter your move:");
                Move player=Move.valueOf(sc.next().toUpperCase());
                Move computer=moves[r.nextInt(moves.length)];
                System.out.println("Your move:"+player);
                System.out.println("Computer move:"+computer);
                int result=winner(player,computer);
                if(result==1)
                {
                    System.out.println("You won!");
                    playerScore++;
                }
                else if(result==-1)
                {
                    System.out.println("Computer won!");
                    compScore++;
                }
                else
                System.out.println("It's a tie!");
        }
        System.out.println("\nFinal Score:");
        System.out.println("Your Score:"+playerScore);
        System.out.println("Computer's Score:"+compScore);
        if(playerScore>compScore)
        System.out.println("You are the final winner!");
        else if(playerScore<compScore)
        System.out.println("Computer is the final winner!");
        else
        System.out.println("It's a Tie!");
    }
}