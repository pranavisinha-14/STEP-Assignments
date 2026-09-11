package week4.Practice;

public class RemainderFairFareSplitter {

    static class FareSplitter {

        private String tripId;
        private double totalFare;
        private int passengerCount;

        public FareSplitter(
                String tripId,
                double totalFare,
                int passengerCount) {

            if (tripId == null
                    || tripId.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Invalid trip ID");
            }

            if (totalFare < 0) {

                throw new IllegalArgumentException(
                        "Fare cannot be negative");
            }

            if (passengerCount <= 0) {

                throw new IllegalArgumentException(
                        "Passenger count must be positive");
            }

            this.tripId = tripId.trim();
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        // Constructor chaining
        public FareSplitter(
                String tripId,
                double totalFare) {

            this(tripId, totalFare, 2);
        }

        // Constructor chaining
        public FareSplitter(String tripId) {

            this(tripId, 0.0, 2);
        }

        public double[] fareBreakdown() {

            double[] result =
                    new double[passengerCount];

            long totalPaise =
                    Math.round(totalFare * 100);

            long basePaise =
                    totalPaise / passengerCount;

            long remainder =
                    totalPaise % passengerCount;

            for (int i = 0; i < passengerCount; i++) {

                result[i] = basePaise / 100.0;

                /*
                 * Extra paisa goes to the LAST shares,
                 * not the first shares.
                 */
                if (i >= passengerCount - remainder) {
                    result[i] += 0.01;
                }
            }

            return result;
        }

        public boolean isConfirmationOverdue(
                int confirmed,
                int expected) {

            return confirmed < expected;
        }
    }

    public static void main(String[] args) {

        FareSplitter trip1 =
                new FareSplitter(
                        "TRIP001",
                        100000,
                        3);

        double[] breakdown1 =
                trip1.fareBreakdown();

        System.out.print(
                "TRIP001: ");

        for (double share : breakdown1) {
            System.out.printf("%.2f ", share);
        }

        System.out.println();

        FareSplitter trip2 =
                new FareSplitter("TRIP003");

        double[] breakdown2 =
                trip2.fareBreakdown();

        System.out.print(
                "TRIP003: ");

        for (double share : breakdown2) {
            System.out.printf("%.2f ", share);
        }

        System.out.println();

        System.out.println(
                "Confirmation overdue: "
                + trip1.isConfirmationOverdue(
                        2, 3));
    }
}