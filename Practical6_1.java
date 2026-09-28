interface Switchable {

    void on();

    void off();

    default void toggle() {
        on();
    }
}

class Fan implements Switchable {

    boolean isOn = false;

    @Override
    public void on() {
        isOn = true;
        System.out.println("Fan is ON");
    }

    @Override
    public void off() {
        isOn = false;
        System.out.println("Fan is OFF");
    }

    public boolean isOn() {
        return isOn;
    }
}

class Light implements Switchable {

    boolean isOn = false;

    @Override
    public void on() {
        isOn = true;
        System.out.println("Light is ON");
    }

    @Override
    public void off() {
        isOn = false;
        System.out.println("Light is OFF");
    }

    public boolean isOn() {
        return isOn;
    }
}

@FunctionalInterface
interface SwitchPermission {
    boolean maySwitchOn(Switchable device, int hour);
}

public class Practical6_1 {

    public static void main(String[] args) {

        Fan fan = new Fan();
        Light light = new Light();

        Switchable[] devices = { fan, light };

        System.out.println("Toggling devices:");

        for (Switchable device : devices) {
            device.toggle();
        }

        // Anonymous class
        SwitchPermission permission1 = new SwitchPermission() {
            @Override
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        // Lambda
        SwitchPermission permission2 =
                (device, hour) -> hour >= 6 && hour <= 22;

        int hour = 10;

        System.out.println("\nUsing Anonymous Class:");
        System.out.println("May switch ON at " + hour + ":00? "
                + permission1.maySwitchOn(fan, hour));

        System.out.println("\nUsing Lambda:");
        System.out.println("May switch ON at " + hour + ":00? "
                + permission2.maySwitchOn(light, hour));
    }
}