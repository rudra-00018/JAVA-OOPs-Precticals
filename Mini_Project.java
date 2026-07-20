class Customer {

    private String name;
    private String email;
    private String mobile;
    private final String customerId;

    private static long customerCounter = 101;

    private static String generateCustomerId() {
        return "CUST" + customerCounter++;
    }

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }
}

class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int counter = 1;

    private static String generateAccountNumber() {
        return String.format("AC%04d", counter++);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount > 0)
            balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}

public class Mini_Project {

    public static void main(String[] args) {

        Customer c1 = new Customer("Daksh", "daksh18@gmail.com", "9909900018");
        Customer c2 = new Customer("Kartavya", "kartavya31@gmail.com", "9909900031");
        Customer c3 = new Customer("meet", "meet13@gmail.com", "9909900013");

        System.out.println(c1.getCustomerId() + " " + c1.getName());
        System.out.println(c2.getCustomerId() + " " + c2.getName());
        System.out.println(c3.getCustomerId() + " " + c3.getName());
        

        Account[] accounts = new Account[3];

        accounts[0] = new Account("Daksh", 500000);
        accounts[1] = new Account("Kartavya");
        accounts[2] = new Account("meet", 350000);

        accounts[0].deposit(100000);
        accounts[1].deposit(250000);
        accounts[2].withdraw(150000);
        accounts[0].withdraw(1700000);

        for (Account acc : accounts) {
            System.out.println("Account No : " + acc.getAccountNumber());
            System.out.println("Owner      : " + acc.getOwnerName());
            System.out.println("Balance    : " + acc.getBalance());
            System.out.println(" ");
        }
    }
}