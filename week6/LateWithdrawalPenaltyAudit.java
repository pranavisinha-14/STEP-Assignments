package week6;

public class LateWithdrawalPenaltyAudit {

    static class RaceEntry {

        protected String bibNumber;
        protected double entryFee;
        protected double amountPaid;

        private double[] lateFeeHistory;
        private int feeCount;

        public RaceEntry(
                String bibNumber,
                double entryFee) {

            if (bibNumber == null
                    || bibNumber.trim().isEmpty()
                    || bibNumber.trim().length() < 4) {

                throw new IllegalArgumentException(
                        "Invalid bib number");
            }

            if (entryFee <= 0) {

                throw new IllegalArgumentException(
                        "Entry fee must be positive");
            }

            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.amountPaid = 0.0;

            // Maximum 10 late fees
            this.lateFeeHistory =
                    new double[10];

            this.feeCount = 0;
        }

        public void pay(double amount) {

            if (amount < 0) {

                throw new IllegalArgumentException(
                        "Payment cannot be negative");
            }

            amountPaid += amount;

            if (amountPaid > entryFee) {
                amountPaid = entryFee;
            }
        }

        public double getBalanceDue() {

            return entryFee - amountPaid;
        }

        protected void applyLateFee(
                double amount) {

            if (amount <= 0) {

                throw new IllegalArgumentException(
                        "Late fee must be positive");
            }

            /*
             * Add the penalty to the outstanding
             * entry amount.
             */
            entryFee += amount;

            /*
             * Record the penalty.
             */
            if (feeCount < lateFeeHistory.length) {

                lateFeeHistory[feeCount] =
                        amount;

                feeCount++;
            }
        }

        /*
         * Defensive copy
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

    static class RunnerEntry
            extends RaceEntry {

        private String category;

        public RunnerEntry(
                String bibNumber,
                double entryFee,
                String category) {

            super(
                    bibNumber,
                    entryFee);

            if (category == null
                    || category.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Category cannot be blank");
            }

            this.category = category.trim();
        }

        @Override
        protected void applyLateFee(
                double amount) {

            /*
             * Runner pays DOUBLE the normal
             * late fee.
             *
             * Parent class performs the actual
             * penalty and recording.
             */
            super.applyLateFee(
                    amount * 2);
        }
    }

    public static void main(String[] args) {

        RunnerEntry r =
                new RunnerEntry(
                        "BIB2001",
                        80,
                        "Open 10K");

        r.pay(30);

        r.applyLateFee(20);

        System.out.println(
                r.getBalanceDue());

        double[] history =
                r.getLateFeeHistory();

        System.out.print(
                "History: ");

        for (double fee : history) {

            System.out.print(
                    fee + " ");
        }

        System.out.println();

        /*
         * Attempt to modify the returned array.
         */
        history[0] = 999;

        double[] historyAgain =
                r.getLateFeeHistory();

        System.out.print(
                "After tampering: ");

        for (double fee : historyAgain) {

            System.out.print(
                    fee + " ");
        }

        System.out.println();
    }
}