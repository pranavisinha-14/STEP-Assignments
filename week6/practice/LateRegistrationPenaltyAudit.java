package week6.practice;

public class LateRegistrationPenaltyAudit {

    static class EventTicket {

        protected double basePrice;
        protected double amountPaid;

        private double[] lateFeeHistory;
        private int feeCount;

        public EventTicket(double basePrice) {

            if (basePrice <= 0) {
                throw new IllegalArgumentException(
                        "Base price must be positive");
            }

            this.basePrice = basePrice;
            this.amountPaid = 0.0;

            lateFeeHistory =
                    new double[10];

            feeCount = 0;
        }

        public void pay(double amount) {

            if (amount < 0) {
                throw new IllegalArgumentException(
                        "Payment cannot be negative");
            }

            amountPaid += amount;

            if (amountPaid > basePrice) {
                amountPaid = basePrice;
            }
        }

        public double getBalanceDue() {
            return basePrice - amountPaid;
        }

        protected void applyLateFee(
                double amount) {

            if (amount <= 0) {
                throw new IllegalArgumentException(
                        "Fee must be positive");
            }

            /*
             * Late fee increases the amount due.
             */
            basePrice += amount;

            if (feeCount < lateFeeHistory.length) {

                lateFeeHistory[feeCount] =
                        amount;

                feeCount++;
            }
        }

        /*
         * Defensive copy.
         */
        public double[] getLateFeeHistory() {

            double[] copy =
                    new double[feeCount];

            for (int i = 0; i < feeCount; i++) {

                copy[i] =
                        lateFeeHistory[i];
            }

            return copy;
        }
    }

    static class WorkshopTicket
            extends EventTicket {

        public WorkshopTicket(
                double basePrice) {

            super(basePrice);
        }

        /*
         * Constructor with attendee information
         * is also supported.
         */
        public WorkshopTicket(
                String attendeeId,
                double basePrice,
                String track) {

            super(basePrice);
        }

        @Override
        protected void applyLateFee(
                double amount) {

            /*
             * Double the fee and let the parent
             * handle the actual logic and recording.
             */
            super.applyLateFee(
                    amount * 2);
        }

        /*
         * Public helper so main() can demonstrate
         * the protected method.
         */
        public void addLateFee(
                double amount) {

            applyLateFee(amount);
        }
    }

    public static void main(String[] args) {

        WorkshopTicket w =
                new WorkshopTicket(1200);

        w.pay(1200);

        w.addLateFee(100);

        System.out.println(
                w.getBalanceDue());

        double[] history =
                w.getLateFeeHistory();

        System.out.print("History: ");

        for (double fee : history) {
            System.out.print(fee + " ");
        }

        System.out.println();

        /*
         * Try to tamper with the returned array.
         */
        history[0] = 999;

        double[] historyAgain =
                w.getLateFeeHistory();

        System.out.print(
                "After tampering: ");

        for (double fee : historyAgain) {
            System.out.print(fee + " ");
        }

        System.out.println();
    }
}