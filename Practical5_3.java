abstract class Media {
    String title;
    int lateDays;

    Media(String title, int lateDays) {
        this.title = title;
        this.lateDays = lateDays;
    }

    abstract double lateFee();
}

class Book extends Media {

    Book(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double lateFee() {
        return lateDays * 2;
    }
}

class Movie extends Media {

    Movie(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double lateFee() {
        return lateDays * 5;
    }
}

class Game extends Media {

    Game(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double lateFee() {
        return lateDays * 3;
    }
}

public class Practical5_3 {
    public static void main(String[] args) {

        Media[] returnedMedia = {
            new Book("Java Programming", 4),
            new Movie("Avengers", 3),
            new Game("Minecraft", 5),
            new Book("Data Structures", 2)
        };

        double totalFee = 0;

        for (Media media : returnedMedia) {

            double fee = media.lateFee();

            System.out.println(
                "Title: " + media.title +
                ", Late Days: " + media.lateDays +
                ", Late Fee: Rs. " + fee
            );

            totalFee += fee;
        }

        System.out.println("Total Late Fees: Rs. " + totalFee);
    }
}