public class Practical3_3 {
    public static void main(String[] args) {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(2, 4);
        Fraction third = new Fraction(3, 6);

        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
        System.out.println("All equal: "
                + (first.equals(second) && second.equals(third) && first.equals(third)));
    }
}

/** An immutable fraction stored in lowest terms. */
class Fraction {
    private int num;
    private int den;

    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }

        long numerator = num;
        long denominator = den;
        long g = gcd(numerator, denominator);
        numerator /= g;
        denominator /= g;

        // Keep the denominator positive, so equal values have one representation.
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        this.num = Math.toIntExact(numerator);
        this.den = Math.toIntExact(denominator);
    }

    private static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Fraction)) {
            return false;
        }
        Fraction fraction = (Fraction) other;
        return num == fraction.num && den == fraction.den;
    }

    @Override
    public int hashCode() {
        return 31 * num + den;
    }
}
