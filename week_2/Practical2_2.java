public class Practical2_2 {
    public static class CinemaShow{
        private String title;
        private int seatAvailable;
        private static int totalBooked = 0;
        private final int capacity = 200;

        CinemaShow(String title , int capacity){
            this.title = title;
            this.seatAvailable = capacity;
        }
        CinemaShow(String title){
            this(title,100);
        }

        public boolean book(int n){
            if(n <= seatAvailable){
                seatAvailable -= n;
                totalBooked += n;
                return true;
            }
            return false;
        }

        public void cancle(int seat){
            int a = seatAvailable + seat;
            if(a <= capacity && a >= 0){
                seatAvailable += seat;
                totalBooked -= seat;
            }

        }
    }

    public static void main(String[] args){
        CinemaShow c = new CinemaShow("Spyder Man : Brand New Day",100);
            
        c.book(10);
        System.out.println(c.seatAvailable);
        c.cancle(5);
        System.out.println(c.seatAvailable);

        System.out.println(c.totalBooked);

        }

}