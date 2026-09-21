package week6.practice;
public class FestWideTicketSettlement {

    static class EventTicket {

        private static int ticketsIssued = 0;

        private final String ticketId;

        protected double basePrice;
        protected double amountPaid;

        public EventTicket(
                double basePrice) {

            if (basePrice <= 0) {
                throw new IllegalArgumentException(
                        "Base price must be positive");
            }

            this.basePrice = basePrice;
            this.amountPaid = 0.0;

            /*
             * Increment exactly once for every
             * successfully constructed object.
             */
            ticketsIssued++;

            ticketId =
                    "TCK-" + (1000 + ticketsIssued);
        }

        public void pay(
                double amount) {

            if (amount < 0) {
                throw new IllegalArgumentException(
                        "Payment cannot be negative");
            }

            amountPaid += amount;

            if (amountPaid > basePrice) {
                amountPaid = basePrice;
            }
        }

        /*
         * Overloaded pay method.
         *
         * It records the mode and then delegates
         * the actual payment to pay(double).
         */
        public void pay(
                double amount,
                String mode) {

            System.out.println(
                    "Payment mode: " + mode);

            pay(amount);
        }

        public double getBalanceDue() {

            return basePrice - amountPaid;
        }

        public String getTicketId() {

            return ticketId;
        }

        public static boolean isValidPromoCode(
                String code) {

            /*
             * Exact format:
             * F + 3 digits + 1 uppercase letter
             *
             * Example: F123A
             */
            if (code == null
                    || code.length() != 5) {

                return false;
            }

            if (code.charAt(0) != 'F') {
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

        public static int getTicketsIssued() {

            return ticketsIssued;
        }
    }

    static class GroupTicket
            extends EventTicket {

        private int groupSize;

        public GroupTicket(
                double basePrice,
                int groupSize) {

            super(basePrice);

            if (groupSize <= 0) {
                throw new IllegalArgumentException(
                        "Group size must be positive");
            }

            this.groupSize = groupSize;
        }

        public int getGroupSize() {

            return groupSize;
        }
    }

    static String processNightlySettlement(
            EventTicket[] tickets) {

        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (EventTicket ticket : tickets) {

            /*
             * Never process a null entry.
             */
            if (ticket == null) {

                nullSkipped++;
                continue;
            }

            processed++;

            /*
             * instanceof separates the group branch
             * from the regular branch.
             */
            if (ticket instanceof GroupTicket) {

                groupCount++;

            } else {

                individualCount++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + groupCount
                + " group | "
                + individualCount
                + " individual";
    }

    public static void main(String[] args) {

        EventTicket t1 =
                new EventTicket(500);

        System.out.println(
                t1.getTicketId());

        System.out.println(
                EventTicket.getTicketsIssued());

        System.out.println();

        System.out.println(
                EventTicket.isValidPromoCode(
                        "F123A"));

        System.out.println(
                EventTicket.isValidPromoCode(
                        "F12A"));

        System.out.println(
                EventTicket.isValidPromoCode(
                        "X123A"));

        System.out.println();

        t1.pay(200);

        t1.pay(
                200,
                "UPI");

        System.out.println(
                t1.getBalanceDue());

        System.out.println();

        GroupTicket group =
                new GroupTicket(
                        2000,
                        5);

        EventTicket individual =
                new EventTicket(500);

        EventTicket[] tickets = {
            group,
            null,
            individual
        };

        System.out.println(
                processNightlySettlement(
                        tickets));
    }
}