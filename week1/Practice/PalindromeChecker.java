package Practice;

public class PalindromeChecker {

    static boolean isPalindromeIterative(String text) {

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {

            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    static boolean isPalindromeRecursive(String text) {
        return checkRecursive(text, 0, text.length() - 1);
    }

    static boolean checkRecursive(String text, int left, int right) {

        if (left >= right) {
            return true;
        }

        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }

        return checkRecursive(text, left + 1, right - 1);
    }

    static boolean isPalindromeArrayReversal(String text) {

        char[] characters = text.toCharArray();

        for (int i = 0; i < characters.length / 2; i++) {

            char temp = characters[i];

            characters[i] =
                    characters[characters.length - 1 - i];

            characters[characters.length - 1 - i] = temp;
        }

        String reversed = new String(characters);

        return reversed.equals(text);
    }

    public static void main(String[] args) {

        String text = "madam";

        System.out.println("Input: " + text);

        System.out.println("Iterative: " +
                (isPalindromeIterative(text)
                        ? "Palindrome"
                        : "Not Palindrome"));

        System.out.println("Recursive: " +
                (isPalindromeRecursive(text)
                        ? "Palindrome"
                        : "Not Palindrome"));

        System.out.println("Array Reversal: " +
                (isPalindromeArrayReversal(text)
                        ? "Palindrome"
                        : "Not Palindrome"));
    }
}