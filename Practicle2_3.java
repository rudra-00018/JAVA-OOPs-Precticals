public class Practicle2_3 {
    private int twoWheelers = 0;
    private int fourWheelers = 0;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    Practicle2_3(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
    }

    public void park(String type) {
        if (type.equals("two")) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                System.out.println("Two-wheeler parked");
            } else {
                System.out.println("Two-wheeler section full");
            }
        } else if (type.equals("four")) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                System.out.println("Four-wheeler parked");
            } else {
                System.out.println("Four-wheeler section full");
            }
        }
    }

    public void leave(String type) {
        if (type.equals("two") && twoWheelers > 0) {
            twoWheelers--;
            System.out.println("Two-wheeler left");
        } else if (type.equals("four") && fourWheelers > 0) {
            fourWheelers--;
            System.out.println("Four-wheeler left");
        }
    }

    public void display() {
        System.out.println("Two-wheelers: " + twoWheelers);
        System.out.println("Four-wheelers: " + fourWheelers);
        System.out.println("Revenue: " + revenue);
    }

    public static void main(String[] args) {
        Practicle2_3 p = new Practicle2_3(2, 2);
        p.park("two");
        p.park("two");
        p.park("two");
        p.park("four");
        p.park("four");
        p.park("four");
        p.leave("two");
        p.leave("four");
        p.display();
    }
}
