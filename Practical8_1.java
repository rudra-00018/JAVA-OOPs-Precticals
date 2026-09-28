import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class Account {

    @Positive
    private double balance;

    @MaxLength(20)
    private String owner;

    public Account(double balance, String owner) {
        this.balance = balance;
        this.owner = owner;
    }
}

public class Practical8_1 {

    public static void main(String[] args) {

        Account account = new Account(-5000, "Rudra");

        System.out.println("Account created successfully.");
    }
}