package week6.practice;

public class ThreeShapesOneFamilyTree {

    // Base class
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

        public void printTicket() {

            System.out.println(
                    "Standard Event Ticket | Balance Due: "
                    + getBalanceDue());
        }
    }

    // First level
    static class WorkshopTicket
            extends EventTicket {

        protected String track;

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

        // Convenience constructor
        public WorkshopTicket(
                double basePrice,
                String track) {

            this("STU1", basePrice, track);
        }

        @Override
        public void printTicket() {

            System.out.println(
                    "Workshop Ticket | Track: "
                    + track
                    + " | Balance Due: "
                    + getBalanceDue());
        }
    }

    // Second level = multilevel inheritance
    static class PremiumWorkshopTicket
            extends WorkshopTicket {

        private double kitFee;

        public PremiumWorkshopTicket(
                String attendeeId,
                double basePrice,
                String track,
                double kitFee) {

            super(
                    attendeeId,
                    basePrice,
                    track);

            if (kitFee < 0) {
                throw new IllegalArgumentException(
                        "Kit fee cannot be negative");
            }

            this.kitFee = kitFee;

            // Kit fee is part of the amount due.
            this.basePrice += kitFee;
        }

        @Override
        public void printTicket() {

            System.out.println(
                    "Premium Workshop Ticket | Track: "
                    + track
                    + " | Kit Fee: "
                    + kitFee
                    + " | Balance Due: "
                    + getBalanceDue());
        }
    }

    // Hierarchical sibling of WorkshopTicket
    static class HackathonTicket
            extends EventTicket {

        private String teamName;

        public HackathonTicket(
                String attendeeId,
                double basePrice,
                String teamName) {

            super(attendeeId, basePrice);

            if (teamName == null
                    || teamName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Team name cannot be blank");
            }

            this.teamName = teamName.trim();
        }

        @Override
        public void printTicket() {

            System.out.println(
                    "Hackathon Ticket | Team: "
                    + teamName
                    + " | Balance Due: "
                    + getBalanceDue());
        }
    }

    static String classifyGeneration(
            EventTicket ticket) {

        if (ticket instanceof PremiumWorkshopTicket) {

            return "Multilevel descendant "
                    + "(3 generations deep)";

        } else if (ticket instanceof HackathonTicket) {

            return "Hierarchical sibling "
                    + "(independent branch)";

        } else if (ticket instanceof WorkshopTicket) {

            return "Single-level descendant";

        } else {

            return "Base EventTicket";
        }
    }

    /*
     * Polymorphism:
     * No type checking is required here.
     */
    static double getTotalBalanceDue(
            EventTicket[] tickets) {

        double total = 0.0;

        for (EventTicket ticket : tickets) {

            total += ticket.getBalanceDue();
        }

        return total;
    }

    public static void main(String[] args) {

        EventTicket standardTicket =
                new EventTicket(
                        "STU1",
                        500);

        WorkshopTicket workshopTicket =
                new WorkshopTicket(
                        "STU2",
                        1200,
                        "AI/ML");

        PremiumWorkshopTicket premiumTicket =
                new PremiumWorkshopTicket(
                        "STU3",
                        1700,
                        "Cloud Native",
                        300);

        HackathonTicket hackathonTicket =
                new HackathonTicket(
                        "STU4",
                        800,
                        "Byte Force");

        standardTicket.printTicket();
        workshopTicket.printTicket();
        premiumTicket.printTicket();
        hackathonTicket.printTicket();

        System.out.println();

        System.out.println(
                classifyGeneration(
                        premiumTicket));

        System.out.println(
                classifyGeneration(
                        hackathonTicket));

        EventTicket[] tickets = {
            standardTicket,
            workshopTicket,
            premiumTicket,
            hackathonTicket
        };

        System.out.println(
                getTotalBalanceDue(tickets));
    }
}