package week2.Practice;

public class VowelConsonantCounter {

    static void countVowelsAndConsonants(String text) {

        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);

            if (ch == ' ') {
                continue;
            }

            ch = Character.toLowerCase(ch);

            if (ch == 'a' || ch == 'e' || ch == 'i'
                    || ch == 'o' || ch == 'u') {

                vowels++;

            } else {
                consonants++;
            }
        }

        System.out.println(
                "Vowels: " + vowels
                + " | Consonants: " + consonants);
    }

    public static void main(String[] args) {

        String text = "Java Programming";

        countVowelsAndConsonants(text);
    }
}