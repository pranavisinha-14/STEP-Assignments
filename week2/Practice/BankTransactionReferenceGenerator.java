package week2.Practice;

public class BankTransactionReferenceGenerator {

    static String normalizeReference(String raw) {

        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference.toUpperCase();
        }

        String bankCode =
                reference.substring(0, 3).toUpperCase();

        String remaining =
                reference.substring(3);

        return bankCode + remaining;
    }

    static String validateAndFormat(String reference) {

        if (reference.length() != 14) {

            return "Invalid: wrong length";
        }

        // Check first 3 characters are letters
        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(
                    reference.charAt(i))) {

                return "Invalid: bank code must be 3 letters";
            }
        }

        // Check remaining 11 characters are digits
        for (int i = 3; i < reference.length(); i++) {

            if (!Character.isDigit(
                    reference.charAt(i))) {

                return "Invalid: body must contain only digits";
            }
        }

        String bankCode =
                reference.substring(0, 3);

        String date =
                reference.substring(3, 9);

        String sequence =
                reference.substring(9, 14);

        StringBuilder result =
                new StringBuilder();

        result.append("[")
              .append(bankCode)
              .append("] DATE: ")
              .append(date.substring(0, 2))
              .append("/")
              .append(date.substring(2, 4))
              .append("/")
              .append(date.substring(4, 6))
              .append(" | SEQ: ")
              .append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {

        String reference =
                normalizeReference(
                        " hdf03022600042 ");

        System.out.println(
                validateAndFormat(reference));

        String invalidReference =
                normalizeReference(
                        "12F03022600042");

        System.out.println(
                validateAndFormat(invalidReference));
    }
}