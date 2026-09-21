package week7.practice;

public class CheckoutPaymentHandler {

    // Abstract parent class
    static abstract class PaymentMethod {

        private static int transactionCounter = 1000;

        private final String transactionId;

        /*
         * Constructor of the abstract class.
         * Every subclass reaches this through super().
         */
        public PaymentMethod() {

            transactionCounter++;

            transactionId =
                    "TXN-" + transactionCounter;
        }

        // Abstract method
        public abstract String processPayment(
                double amount);

        /*
         * Overloaded method.
         *
         * It calls the one-argument version first
         * and then adds the note.
         */
        public String processPayment(
                double amount,
                String note) {

            return processPayment(amount)
                    + " (" + note + ")";
        }

        public String getTransactionId() {

            return transactionId;
        }
    }

    // Credit card payment
    static class CreditCardPayment
            extends PaymentMethod {

        private String cardNumberLastFour;

        public CreditCardPayment(
                String cardNumberLastFour) {

            super();

            this.cardNumberLastFour =
                    cardNumberLastFour;
        }

        @Override
        public String processPayment(
                double amount) {

            return "Charged $" + amount
                    + " to card ending "
                    + cardNumberLastFour
                    + " - Txn "
                    + getTransactionId();
        }
    }

    // Cash payment
    static class CashPayment
            extends PaymentMethod {

        public CashPayment() {

            super();
        }

        @Override
        public String processPayment(
                double amount) {

            return "Received $" + amount
                    + " in cash - Txn "
                    + getTransactionId();
        }
    }

    /*
     * Runtime polymorphism.
     *
     * This method accepts the parent type and
     * doesn't care which subclass is supplied.
     */
    static void printConfirmation(
            PaymentMethod payment,
            double amount) {

        System.out.println(
                payment.processPayment(amount));
    }

    static void testUpcasting() {

        CreditCardPayment cc =
                new CreditCardPayment("4471");

        /*
         * Upcasting:
         * CreditCardPayment object is stored
         * in a PaymentMethod reference.
         */
        PaymentMethod ref = cc;

        printConfirmation(ref, 250.0);
    }

    public static void main(String[] args) {

        CreditCardPayment cc =
                new CreditCardPayment("4471");

        System.out.println(
                cc.processPayment(250.0));

        System.out.println(
                cc.processPayment(
                        250.0,
                        "Birthday gift"));

        CashPayment cash =
                new CashPayment();

        System.out.println(
                cash.processPayment(40.0));

        testUpcasting();

        /*
         * This would NOT compile:
         *
         * PaymentMethod p =
         *         new PaymentMethod();
         *
         * PaymentMethod is abstract, so it cannot
         * be instantiated directly.
         */
    }
}