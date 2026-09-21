package week6.practice;

public class NightlyTicketAnnouncer {

    static class EventTicket {

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

        public String printTicket() {

            return "Standard | Balance: "
                    + getBalanceDue();
        }
    }

    static class WorkshopTicket
            extends EventTicket {

        private String track;

        public WorkshopTicket(
                double basePrice,
                String track) {

            super(basePrice);

            if (track == null
                    || track.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Track cannot be blank");
            }

            this.track = track.trim();
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {

            return "Workshop | Track: "
                    + track
                    + " | Balance: "
                    + getBalanceDue();
        }
    }

    static String batchPrint(
            EventTicket[] tickets) {

        StringBuilder report =
                new StringBuilder();

        for (EventTicket ticket : tickets) {

            /*
             * Polymorphic call.
             * Java chooses the correct printTicket()
             * based on the actual object.
             */
            report.append(
                    ticket.printTicket());

            /*
             * Only downcast when the object really
             * is a WorkshopTicket.
             */
            if (ticket instanceof WorkshopTicket) {

                WorkshopTicket workshop =
                        (WorkshopTicket) ticket;

                report.append(
                        " [Track via downcast: ")
                      .append(
                        workshop.getTrack())
                      .append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        EventTicket plain =
                new EventTicket(500);

        WorkshopTicket workshop =
                new WorkshopTicket(
                        1200,
                        "AI/ML");

        EventTicket[] tickets = {
            plain,
            workshop
        };

        System.out.println(
                batchPrint(tickets));

        /*
         * Demonstrating the unsafe downcast.
         *
         * This is intentionally NOT executed because
         * it would throw ClassCastException.
         */
        EventTicket anotherPlain =
                new EventTicket(500);

        if (anotherPlain instanceof WorkshopTicket) {

            WorkshopTicket safeCast =
                    (WorkshopTicket) anotherPlain;

            System.out.println(
                    safeCast.getTrack());
        } else {

            System.out.println(
                    "Downcast avoided: object is not "
                    + "a WorkshopTicket.");
        }
    }
}