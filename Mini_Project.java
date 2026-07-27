import java.util.Objects;

class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;

    private static long customerCounter = 101;

    public static class Address {
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() {
            return line;
        }

        public String getCity() {
            return city;
        }

        public String getPincode() {
            return pincode;
        }
    }

    private static String generateCustomerId() {
        return "CUST" + customerCounter++;
    }

    public Customer(String name, String email, String mobile, Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.customerId = generateCustomerId();
    }

    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, new Address("", "", ""));
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

    public Address getAddress() {
        return address;
    }

    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
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
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= balance) {
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

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: " + balance
                + ", Active: " + active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    public String getStatement() {
        return "Account Number: " + accountNumber + "\n"
                + "Owner: " + ownerName + "\n"
                + "Balance: " + balance + "\n"
                + "Active: " + active;
    }
}

public class Mini_Project {
    public static void main(String[] args) {
        Customer.Address address = new Customer.Address("Krishnam Tower", "Anand", "388001");
        Customer c1 = new Customer("Daksh", "daksh18@gmail.com", "9909900018", address);
        Customer c2 = new Customer("Kartavya", "kartavya31@gmail.com", "9909900031");
        Customer c3 = new Customer("Meet", "meet13@gmail.com", "9909900013");

        Customer copiedCustomer = c1.clone();
        System.out.println(c1.getCustomerId() + " " + c1.getName());
        System.out.println(c2.getCustomerId() + " " + c2.getName());
        System.out.println(c3.getCustomerId() + " " + c3.getName());
        System.out.println("City: " + copiedCustomer.getAddress().getCity());

        Account[] accounts = new Account[3];
        accounts[0] = new Account("Daksh", 500000);
        accounts[1] = new Account("Kartavya");
        accounts[2] = new Account("Meet", 350000);

        accounts[0].deposit(100000);
        accounts[1].deposit(250000);
        accounts[2].withdraw(150000);

        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("Account 1 equals Account 2: " + accounts[0].equals(accounts[1]));

        Object object = accounts[0];
        System.out.println("object is an Account: " + (object instanceof Account));

        System.out.println("\nAccount Statement:\n" + accounts[0].getStatement());
    }
}
