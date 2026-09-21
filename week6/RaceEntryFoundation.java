package week6;

public class RaceEntryFoundation {

    // Parent class
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
    }

    // Single inheritance
    static class RunnerEntry extends RaceEntry {

        private String category;

        public RunnerEntry(
                String bibNumber,
                double entryFee,
                String category) {

            super(bibNumber, entryFee);

            if (category == null
                    || category.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Category cannot be blank");
            }

            this.category = category.trim();
        }

        public String getCategory() {
            return category;
        }
    }

    /*
     * Try to construct one RaceEntry for every
     * bib number and count rejected entries.
     */
    static String registerBatch(
            String[] bibNumbers,
            double entryFee) {

        int registered = 0;
        int rejected = 0;

        for (String bibNumber : bibNumbers) {

            try {

                new RaceEntry(
                        bibNumber,
                        entryFee);

                registered++;

            } catch (IllegalArgumentException e) {

                rejected++;
            }
        }

        return "Registered: " + registered
                + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {

        // Invalid construction
        try {

            new RaceEntry(
                    "B1",
                    50);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected");
        }

        // Runner example
        RunnerEntry r =
                new RunnerEntry(
                        "BIB2001",
                        80,
                        "Open 10K");

        r.pay(30);

        System.out.println(
                r.getBalanceDue());

        // Batch registration
        String[] bibNumbers = {
            "BIB1",
            "B1",
            "BIB2"
        };

        System.out.println(
                registerBatch(
                        bibNumbers,
                        80));
    }
}