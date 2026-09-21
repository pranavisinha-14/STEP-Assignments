package week6.practice;

public class TicketHierarchyFoundation {

    // Parent class
    static class EventTicket {

        protected String attendeeId;
        protected double basePrice;
        protected double amountPaid;

        public EventTicket(
                String attendeeId,
                double basePrice) {

            if (attendeeId == null
                    || attendeeId.trim().isEmpty()
                    || attendeeId.trim().length() < 4) {

                throw new IllegalArgumentException(
                        "Invalid attendee ID");
            }

            if (basePrice <= 0) {
                throw new IllegalArgumentException(
                        "Base price must be positive");
            }

            this.attendeeId = attendeeId.trim();
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
    }

    // Single inheritance
    static class WorkshopTicket extends EventTicket {

        private String track;

        public WorkshopTicket(
                String attendeeId,
                double basePrice,
                String track) {

            super(attendeeId, basePrice);

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
    }

    /*
     * Attempts to construct one EventTicket
     * for every entry.
     */
    static String registerBatch(
            String[] attendeeIds,
            double basePrice) {

        int registered = 0;
        int rejected = 0;

        for (String attendeeId : attendeeIds) {

            try {

                new EventTicket(
                        attendeeId,
                        basePrice);

                registered++;

            } catch (IllegalArgumentException e) {

                rejected++;
            }
        }

        return "Registered: " + registered
                + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {

        try {

            new EventTicket(
                    "ST1",
                    500);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected");
        }

        WorkshopTicket workshop =
                new WorkshopTicket(
                        "STU2",
                        1200,
                        "AI/ML");

        workshop.pay(500);

        System.out.println(
                workshop.getBalanceDue());

        String[] attendees = {
            "STU1",
            "ST1",
            "STU2",
            " ",
            "STU3"
        };

        System.out.println(
                registerBatch(
                        attendees,
                        500));
    }
}