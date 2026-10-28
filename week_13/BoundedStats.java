import java.util.Arrays;

public class BoundedStats {

    /**
     * Generic bounded method that calculates the average of an array of any Number subtype.
     * The upper bound <N extends Number> guarantees that every element has the method doubleValue().
     */
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        double sum = 0.0;
        for (N val : values) {
            if (val == null) {
                throw new IllegalArgumentException("Array contains null elements; cannot calculate average.");
            }
            // Because N extends Number, val.doubleValue() is guaranteed to exist!
            sum += val.doubleValue();
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("=== 2. Bounded Stats (<N extends Number>) ===\n");

        // 1. Works for Integer[]
        Integer[] intValues = { 10, 20, 30, 40, 50 };
        double intAvg = average(intValues);
        System.out.println("Integer[] array : " + Arrays.toString(intValues));
        System.out.printf("Average (Integer) : %.2f%n%n", intAvg);

        // 2. Works for Double[]
        Double[] doubleValues = { 12.5, 24.5, 30.0, 18.2 };
        double dblAvg = average(doubleValues);
        System.out.println("Double[] array  : " + Arrays.toString(doubleValues));
        System.out.printf("Average (Double)  : %.2f%n%n", dblAvg);

        // 3. Works for Long[]
        Long[] longValues = { 1_000_000L, 2_000_000L, 3_000_000L, 4_000_000L };
        double longAvg = average(longValues);
        System.out.println("Long[] array    : " + Arrays.toString(longValues));
        System.out.printf("Average (Long)    : %.2f%n%n", longAvg);

        // 4. Also works for other Number subtypes like Float[] or Short[]
        Float[] floatValues = { 1.5f, 3.5f, 7.0f };
        double floatAvg = average(floatValues);
        System.out.println("Float[] array   : " + Arrays.toString(floatValues));
        System.out.printf("Average (Float)   : %.2f%n%n", floatAvg);

        // Demonstration / Explanation of bounded type restriction:
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("WHY DOES IT REFUSE NON-NUMBER TYPES?");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("1. The type parameter is bounded by <N extends Number>.");
        System.out.println("2. Any type passed to 'average' MUST be a subclass of java.lang.Number (e.g. Integer, Double, Long).");
        System.out.println("3. Non-numeric types (e.g., String[], Boolean[], Object[]) do NOT inherit from Number and");
        System.out.println("   do NOT provide numeric conversion methods like .doubleValue().");
        System.out.println("4. Attempting to call:");
        System.out.println("       String[] strings = {\"hello\", \"world\"};");
        System.out.println("       average(strings);");
        System.out.println("   will trigger a COMPILE-TIME ERROR: 'Bound mismatch: The type String is not a valid substitute");
        System.out.println("   for the bounded parameter <N extends Number>'.");
        System.out.println("5. This provides compile-time type safety and prevents runtime ClassCastException.");
        System.out.println("---------------------------------------------------------------------------------");
    }
}
