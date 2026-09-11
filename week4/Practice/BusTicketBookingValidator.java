package week4.Practice;

import java.util.HashSet;
import java.util.Set;

public class BusTicketBookingValidator {

    static class BusTicket {

        private String passengerName;
        private String destination;
        private boolean checkedIn;

        // No no-argument constructor is provided.
        public BusTicket(
                String passengerName,
                String destination) {

            if (!isMeaningfulName(passengerName)) {
                throw new IllegalArgumentException(
                        "Invalid passenger name");
            }

            if (!isMeaningfulDestination(destination)) {
                throw new IllegalArgumentException(
                        "Invalid destination");
            }

            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
            this.checkedIn = false;
        }

        private static boolean isMeaningfulName(
                String name) {

            if (name == null || name.trim().isEmpty()) {
                return false;
            }

            // Name must contain letters and spaces only.
            for (int i = 0; i < name.length(); i++) {

                char ch = name.charAt(i);

                if (!Character.isLetter(ch)
                        && ch != ' ') {
                    return false;
                }
            }

            return true;
        }

        private static boolean isMeaningfulDestination(
                String destination) {

            return destination != null
                    && !destination.trim().isEmpty();
        }

        public void markCheckedIn() {

            // Idempotent: checking in twice changes nothing.
            if (!checkedIn) {
                checkedIn = true;
                System.out.println(
                        passengerName
                        + " checked in successfully.");
            } else {
                System.out.println(
                        passengerName
                        + " is already checked in.");
            }
        }

        public String getPassengerName() {
            return passengerName;
        }

        public String getDestination() {
            return destination;
        }
    }

    static void processBatch(
            String[][] rawBookings) {

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        Set<String> acceptedBookings =
                new HashSet<>();

        for (String[] booking : rawBookings) {

            if (booking == null
                    || booking.length < 2) {

                rejected++;
                continue;
            }

            String name = booking[0];
            String destination = booking[1];

            try {

                BusTicket ticket =
                        new BusTicket(
                                name,
                                destination);

                String key =
                        ticket.getPassengerName()
                        .toLowerCase()
                        + "|"
                        + ticket.getDestination()
                                .toLowerCase();

                if (acceptedBookings.contains(key)) {

                    duplicates++;

                } else {

                    acceptedBookings.add(key);
                    valid++;
                }

            } catch (IllegalArgumentException e) {

                rejected++;
            }
        }

        System.out.println(
                "Valid: " + valid
                + " | Rejected: " + rejected
                + " | Duplicates skipped: "
                + duplicates);
    }

    public static void main(String[] args) {

        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        processBatch(bookings);

        System.out.println();

        BusTicket ticket =
                new BusTicket(
                        "Divya",
                        "Chennai");

        ticket.markCheckedIn();
        ticket.markCheckedIn();
    }
}
