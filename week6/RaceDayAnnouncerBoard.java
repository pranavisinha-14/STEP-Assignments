package week6;

public class RaceDayAnnouncerBoard {

    // Base class
    static class RaceEntry {

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

            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
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

        public String announce() {

            return "Race Entry | Bib: "
                    + bibNumber
                    + " | Balance: "
                    + getBalanceDue();
        }
    }

    // RunnerEntry inherits RaceEntry
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
        public String announce() {

            return "Runner Entry | Bib: "
                    + bibNumber
                    + " | Category: "
                    + category
                    + " | Balance: "
                    + getBalanceDue();
        }
    }

    // RelayTeamEntry inherits RaceEntry
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

            this.teamSize = teamSize;
        }

        public int getTeamSize() {

            return teamSize;
        }

        @Override
        public String announce() {

            return "Relay Team | Bib: "
                    + bibNumber
                    + " | Team Size: "
                    + teamSize
                    + " | Balance: "
                    + getBalanceDue();
        }
    }

    /*
     * Problem 4:
     * Announce all entries using polymorphism.
     */
    static String announceAll(
            RaceEntry[] entries) {

        StringBuilder report =
                new StringBuilder();

        for (RaceEntry entry : entries) {

            /*
             * Polymorphism:
             * Java automatically calls the
             * correct overridden announce().
             */
            report.append(
                    entry.announce());

            /*
             * Check before downcasting.
             */
            if (entry instanceof RelayTeamEntry) {

                RelayTeamEntry relay =
                        (RelayTeamEntry) entry;

                report.append(
                        " [Team size via downcast: ")
                      .append(
                        relay.getTeamSize())
                      .append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        // Create runner
        RunnerEntry runnerEntry =
                new RunnerEntry(
                        "BIB2001",
                        80,
                        "Open 10K");

        // Pay some amount
        runnerEntry.pay(30);

        // Create relay team
        RelayTeamEntry relayEntry =
                new RelayTeamEntry(
                        "BIB4001",
                        300,
                        4);

        // Store different objects
        // using the common parent type
        RaceEntry[] fleet = {
            runnerEntry,
            relayEntry
        };

        // Generate complete announcement
        String report =
                announceAll(fleet);

        System.out.println(report);

        /*
         * Demonstrate safe downcasting.
         */
        RaceEntry plain =
                new RaceEntry(
                        "BIB5001",
                        50);

        if (plain instanceof RelayTeamEntry) {

            RelayTeamEntry relay =
                    (RelayTeamEntry) plain;

            System.out.println(
                    "Team Size: "
                    + relay.getTeamSize());

        } else {

            System.out.println(
                    "Downcast avoided: object is not "
                    + "a RelayTeamEntry.");
        }
    }
}