package Practice;

public class ReverseCustomerName {

    static String reverseCustomerName(String customerName) {

        StringBuilder reversed =
                new StringBuilder();

        for (int i = customerName.length() - 1;
             i >= 0;
             i--) {

            reversed.append(
                    customerName.charAt(i)
            );
        }

        return reversed.toString();
    }

    public static void main(String[] args) {

        String customerName = "Sunil";

        System.out.println(
                "Original Name: " + customerName
        );

        System.out.println(
                "Reversed Name: "
                + reverseCustomerName(customerName)
        );
    }
}