import java.util.Objects;

public class Practical3_1 {
    static class Point {
    private int x;
    private int y;

        Point(int x , int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Point other = (Point) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }
    }

    public static void main(String args[]){
        Point p[] = {
            new Point(1,2),
            new Point(2,3),
            new Point(4,5),
            new Point(1,3),
            new Point(6,7),
            new Point(8,9),
            new Point(2,7),
            new Point(4,3),
        };

        int count = 0;
        
        for(int i = 0 ; i < p.length ; i++){
            boolean check = false;

            for(int j = 0 ; j < i ; j++){
                if(p[i].equals(p[j])){
                    check = true;
                    break;
                }
            }

            if(!check){
                count++;
            }
        }

        System.out.println(count);
    }
}

