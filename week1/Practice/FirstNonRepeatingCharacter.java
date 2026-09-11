package Practice;

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatingCharacter {

    static char findFirstNonRepeatingChar(String text) {

        Map<Character, Integer> frequency =
                new HashMap<>();

        // Count frequency of every character
        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);

            frequency.put(
                    ch,
                    frequency.getOrDefault(ch, 0) + 1
            );
        }

        // Find the first character occurring once
        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);

            if (frequency.get(ch) == 1) {
                return ch;
            }
        }

        return '\0';
    }

    public static void main(String[] args) {

        String text = "swiss";

        char result =
                findFirstNonRepeatingChar(text);

        if (result == '\0') {

            System.out.println(
                    "No Non-Repeating Character Found");

        } else {

            System.out.println(
                    "First Non-Repeating Character: '"
                    + result + "'");
        }
    }
}