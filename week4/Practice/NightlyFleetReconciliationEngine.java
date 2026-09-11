package week4.Practice;

public class NightlyFleetReconciliationEngine {

    static class BusTicketAccount {

        private static String depotName;

        private String bookingId;
        private double ticketFare;
        private double amountPaid;

        /*
         * Static block initializes class-level state
         * exactly once when the class is loaded.
         */
        static {
            depotName = "SRM Central Bus Depot";
        }

        public BusTicketAccount(
                String bookingId,
                double ticketFare) {

            if (bookingId == null
                    || bookingId.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Invalid booking ID");
            }

            if (ticketFare < 0) {

                throw new IllegalArgumentException(
                        "Ticket fare cannot be negative");
            }

            this.bookingId = bookingId.trim();
            this.ticketFare = ticketFare;
            this.amountPaid = 0.0;
        }

        // Provisional constructor using this(...)
        public BusTicketAccount(
                String bookingId) {

            this(bookingId, 0.0);
        }

        public final double calculatePenalty(
                int minutesLate) {

            if (minutesLate < 0) {

                throw new IllegalArgumentException(
                        "Minutes late cannot be negative");
            }

            // Flat 1% penalty for late boarding.
            if (minutesLate == 0) {
                return 0.0;
            }

            return ticketFare * 0.01;
        }

        public void processAccount(
                BusTicketAccount account,
                double amount,
                int minutesLate) {

            if (account == null) {
                return;
            }

            if (amount < 0) {

                System.out.println(
                        "Rejected payment for "
                        + bookingId);

                return;
            }

            amountPaid += amount;

            if (amountPaid > ticketFare) {
                amountPaid = ticketFare;
            }

            double penalty =
                    calculatePenalty(minutesLate);

            System.out.printf(
                    "%s processed | Payment: Rs %.2f"
                    + " | Penalty: Rs %.2f%n",
                    bookingId,
                    amount,
                    penalty);
        }

        public double getPenalty(
                int minutesLate) {

            return calculatePenalty(minutesLate);
        }
    }

    static class SleeperAccount
            extends BusTicketAccount {

        public SleeperAccount(
                String bookingId,
                double ticketFare) {

            super(bookingId, ticketFare);
        }

        /*
         * Sleeper settlement is intentionally different.
         * The supplied payment is settled after a 10% sleeper
         * adjustment.
         */
        public double sleeperSettlement(
                double amount) {

            return amount * 0.90;
        }
    }

    static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts == null
                || amounts == null
                || minutesLateArray == null) {

            System.out.println(
                    "Invalid batch: arrays cannot be null");

            return;
        }

        /*
         * If arrays have different lengths, process only
         * the common range. This prevents reading unrelated
         * indexes and avoids applying the wrong payment or
         * delay value to an account.
         */
        int length =
                Math.min(
                        accounts.length,
                        Math.min(
                                amounts.length,
                                minutesLateArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;

        double grandTotalPenalties = 0.0;

        for (int i = 0; i < length; i++) {

            BusTicketAccount account =
                    accounts[i];

            if (account == null) {

                nullSkipped++;
                continue;
            }

            double amount =
                    amounts[i];

            int minutesLate =
                    minutesLateArray[i];

            double penalty;

            try {

                if (account instanceof SleeperAccount) {

                    SleeperAccount sleeper =
                            (SleeperAccount) account;

                    double settledAmount =
                            sleeper.sleeperSettlement(
                                    amount);

                    sleeper.processAccount(
                            sleeper,
                            settledAmount,
                            minutesLate);

                    sleeperCount++;

                } else {

                    account.processAccount(
                            account,
                            amount,
                            minutesLate);

                    regularCount++;
                }

                penalty =
                        account.getPenalty(
                                minutesLate);

                grandTotalPenalties += penalty;

                processed++;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Skipped invalid account: "
                        + e.getMessage());
            }
        }

        System.out.println();
        System.out.println(
                processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + sleeperCount
                + " sleeper | "
                + regularCount
                + " regular");

        System.out.printf(
                "Grand total penalties = Rs %.2f%n",
                grandTotalPenalties);
    }

    public static void main(String[] args) {

        BusTicketAccount sleeper =
                new SleeperAccount(
                        "BK001",
                        2000);

        BusTicketAccount regular =
                new BusTicketAccount(
                        "BK002",
                        1200);

        BusTicketAccount[] accounts = {
            sleeper,
            null,
            regular
        };

        double[] amounts = {
            1200,
            900,
            700
        };

        int[] minutesLateArray = {
            10,
            5,
            0
        };

        processBatch(
                accounts,
                amounts,
                minutesLateArray);
    }
}
