package week6;

public class RaceWideBibSettlement {

    static class RaceEntry {

        private static int bibCounter = 0;

        private final String entryCode;

        protected String bibNumber;
        protected double entryFee;
        protected double amountPaid;

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

            this.bibNumber =
                    bibNumber.trim();

            this.entryFee =
                    entryFee;

            this.amountPaid =
                    0.0;

            /*
             * Increment only after validation.
             * Therefore rejected constructions
             * never increment the counter.
             */
            bibCounter++;

            entryCode =
                    "ENT-" + bibCounter;
        }

        // Normal payment
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

        // Overloaded payment
        public void pay(
                double amount,
                String mode) {

            System.out.println(
                    "Paying via " + mode);

            /*
             * Reuse the normal pay method.
             */
            pay(amount);
        }

        public double getBalanceDue() {

            return entryFee - amountPaid;
        }

        public String getEntryCode() {

            return entryCode;
        }

        /*
         * Format:
         * M + 3 digits + 1 uppercase letter
         *
         * Example:
         * M123A
         */
        public static boolean isValidDiscountCode(
                String code) {

            if (code == null
                    || code.length() != 5) {

                return false;
            }

            if (code.charAt(0) != 'M') {

                return false;
            }

            if (!Character.isDigit(
                    code.charAt(1))) {

                return false;
            }

            if (!Character.isDigit(
                    code.charAt(2))) {

                return false;
            }

            if (!Character.isDigit(
                    code.charAt(3))) {

                return false;
            }

            if (!Character.isUpperCase(
                    code.charAt(4))) {

                return false;
            }

            return true;
        }

        public static int getBibCounter() {

            return bibCounter;
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

            this.category =
                    category.trim();
        }
    }

    static class EliteRunnerEntry
            extends RunnerEntry {

        private double sponsorBonus;

        public EliteRunnerEntry(
                String bibNumber,
                double entryFee,
                String category,
                double sponsorBonus) {

            super(
                    bibNumber,
                    entryFee,
                    category);

            this.sponsorBonus =
                    sponsorBonus;
        }
    }

    static class RelayTeamEntry
            extends RaceEntry {

        private int teamSize;

        public RelayTeamEntry(
                String bibNumber,
                double entryFee,
                int teamSize) {

            super(
                    bibNumber,
                    entryFee);

            if (teamSize <= 0) {

                throw new IllegalArgumentException(
                        "Team size must be positive");
            }

            this.teamSize =
                    teamSize;
        }

        public int getTeamSize() {

            return teamSize;
        }
    }

    static String settleNight(
            RaceEntry[] entries) {

        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        for (RaceEntry entry : entries) {

            /*
             * Null entry must never cause
             * the program to crash.
             */
            if (entry == null) {

                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {

                relayCount++;

            } else {

                individualCount++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + relayCount
                + " relay | "
                + individualCount
                + " individual";
    }

    public static void main(String[] args) {

        /*
         * Create entries so that the counter
         * can be demonstrated.
         */
        RunnerEntry runnerEntry =
                new RunnerEntry(
                        "BIB2001",
                        80,
                        "Open 10K");

        EliteRunnerEntry eliteEntry =
                new EliteRunnerEntry(
                        "BIB3001",
                        150,
                        "Elite Full Marathon",
                        500);

        RelayTeamEntry relayEntry =
                new RelayTeamEntry(
                        "BIB4001",
                        300,
                        4);

        RaceEntry anotherEntry =
                new RaceEntry(
                        "BIB5001",
                        50);

        /*
         * Discount code tests
         */
        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "M123A"));

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "M12A"));

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "X123A"));

        System.out.println();

        /*
         * Payment mode example
         */
        runnerEntry.pay(
                10,
                "UPI");

        System.out.println();

        /*
         * Night settlement
         */
        RaceEntry[] entries = {
            eliteEntry,
            null,
            relayEntry
        };

        System.out.println(
                settleNight(entries));

        System.out.println();

        /*
         * Four successfully constructed objects.
         */
        System.out.println(
                "Bib Counter: "
                + RaceEntry.getBibCounter());
    }
}