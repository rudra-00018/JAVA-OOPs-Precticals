public class Practicle2_2 {
    public static class CinemaShow {
        private String title;
        private int seatAvailable;
        private static int totalBooked = 0;
        private final int capacity;

        CinemaShow(String title, int capacity) {
            this.title = title;
            this.capacity = capacity;
            this.seatAvailable = capacity;
        }

        CinemaShow(String title) {
            this(title, 100);
        }

        public boolean book(int n) {
            if (n > 0 && n <= seatAvailable) {
                seatAvailable -= n;
                totalBooked += n;
                return true;
            }
            return false;
        }

        public void cancel(int seats) {
            if (seats > 0 && seatAvailable + seats <= capacity) {
                seatAvailable += seats;
                totalBooked -= seats;
            }
        }
    }

    public static void main(String[] args) {
        CinemaShow c = new CinemaShow("Spider-Man: Brand New Day", 100);
        c.book(10);
        System.out.println(c.seatAvailable);
        c.cancel(5);
        System.out.println(c.seatAvailable);
        System.out.println(CinemaShow.totalBooked);
    }
}
