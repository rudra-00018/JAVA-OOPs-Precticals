@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

class EmailSender implements Notifier, Urgent {

    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSSender implements Notifier {

    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

public class Practical6_2 {

    public static void main(String[] args) {

        // Lambda email sender
        Notifier email = message ->
                System.out.println("Email: " + message);

        // Lambda SMS sender
        Notifier sms = message ->
                System.out.println("SMS: " + message);

        Notifier[] senders = {
            email,
            sms
        };

        String message = "Your assignment is due tomorrow.";

        System.out.println("Broadcasting message:");

        for (Notifier sender : senders) {
            sender.send(message);
        }

        System.out.println("\nUrgent notification:");

        // Using actual classes to demonstrate Urgent marker interface
        Notifier[] urgentSenders = {
            new EmailSender(),
            new SMSSender()
        };

        for (Notifier sender : urgentSenders) {

            sender.send(message);

            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}