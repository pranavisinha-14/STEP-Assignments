package week2.Practice;

public class MaskedPhoneNumberFormatter {

    static String maskPhoneNumber(String phone) {

        if (phone.length() != 10) {
            return "Invalid phone number";
        }

        for (int i = 0; i < phone.length(); i++) {

            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        String lastFour =
                phone.substring(6);

        StringBuilder result =
                new StringBuilder("XXXXXX");

        result.append("-");

        result.append(lastFour);

        return result.toString();
    }

    public static void main(String[] args) {

        System.out.println(
                maskPhoneNumber("9876543210"));

        System.out.println(
                maskPhoneNumber("98765"));
    }
}