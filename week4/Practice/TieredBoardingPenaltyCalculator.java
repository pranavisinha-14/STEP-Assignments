package week4.Practice;

public final class TieredBoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public TieredBoardingPenaltyCalculator(
            double minimumPenaltyPercent) {

        if (minimumPenaltyPercent < 0) {

            throw new IllegalArgumentException(
                    "Minimum penalty cannot be negative");
        }

        this.minimumPenaltyPercent =
                minimumPenaltyPercent;
    }

    public final double calculatePenalty(
            double ticketFare,
            int minutesLate) {

        if (ticketFare < 0) {

            throw new IllegalArgumentException(
                    "Ticket fare cannot be negative");
        }

        if (minutesLate < 0) {

            throw new IllegalArgumentException(
                    "Minutes late cannot be negative");
        }

        // On-time boarding = zero penalty.
        if (minutesLate == 0) {
            return 0.0;
        }

        double penalty = 0.0;

        // First 5 minutes at 0.5% per minute.
        int firstTier =
                Math.min(minutesLate, 5);

        penalty += firstTier
                * ticketFare
                * 0.005;

        // Minutes 6-15 at 1% per minute.
        if (minutesLate > 5) {

            int secondTier =
                    Math.min(minutesLate, 15) - 5;

            penalty += secondTier
                    * ticketFare
                    * 0.01;
        }

        // Minutes 16 onward at 2% per minute.
        if (minutesLate > 15) {

            int thirdTier =
                    minutesLate - 15;

            penalty += thirdTier
                    * ticketFare
                    * 0.02;
        }

        // Minimum flat-fee floor.
        double minimumPenalty =
                ticketFare
                * minimumPenaltyPercent
                / 100.0;

        return Math.max(
                penalty,
                minimumPenalty);
    }

    public static void main(String[] args) {

        TieredBoardingPenaltyCalculator calculator =
                new TieredBoardingPenaltyCalculator(1.0);

        System.out.println(
                "Penalty for 0 minutes: Rs "
                + calculator.calculatePenalty(
                        1000, 0));

        System.out.println(
                "Penalty for 1 minute: Rs "
                + calculator.calculatePenalty(
                        1000, 1));

        System.out.println(
                "Penalty for 16 minutes: Rs "
                + calculator.calculatePenalty(
                        1000, 16));
    }
}
