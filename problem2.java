
import java.util.Scanner;

public class problem2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        String key = sc.nextLine();

        String[] w = s.trim().split(" +");
        StringBuilder sb = new StringBuilder();

        int count = 0, vowels = 0;
        String shortest = w[0].toLowerCase();

        for (int i = 0; i < w.length; i++) {
            String word = w[i].toLowerCase();

            if (i > 0)
                sb.append(" ");

            sb.append(word);

            if (word.length() < shortest.length())
                shortest = word;

            if (word.equalsIgnoreCase(key))
                count++;
        }

        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

        for (int i = 0; i < s.length(); i++) {
            char ch = Character.toLowerCase(s.charAt(i));

            if ("aeiou".indexOf(ch) != -1)
                vowels++;
        }

        System.out.println("Sentence: " + sb);
        System.out.println("Words: " + w.length);
        System.out.println("Shortest word: " + shortest);
        System.out.println("Keyword count: " + count);
        System.out.println("Vowels: " + vowels);

        sc.close();
    }
}
