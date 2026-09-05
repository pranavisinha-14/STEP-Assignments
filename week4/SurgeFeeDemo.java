package week4;

final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    SurgeFeeCalculator(double minimumSurgePercent) {

        this.minimumSurgePercent =
            minimumSurgePercent;
    }

    final double calculateSurgeFee(
        double orderValue,
        int delayMinutes
    ) {

        if (orderValue < 0 ||
            delayMinutes < 0) {

            throw new IllegalArgumentException(
                "Order value and delay cannot be negative."
            );
        }

        if (delayMinutes == 0) {
            return 0;
        }

        int firstTier =
            Math.min(delayMinutes, 5);

        int secondTier =
            Math.min(
                Math.max(delayMinutes - 5, 0),
                10
            );

        int thirdTier =
            Math.max(delayMinutes - 15, 0);

        double fee =
            firstTier * orderValue * 0.005;

        fee +=
            secondTier * orderValue * 0.01;

        fee +=
            thirdTier * orderValue * 0.02;

        double minimumFee =
            orderValue *
            minimumSurgePercent / 100;

        return Math.max(fee, minimumFee);
    }
}

public class SurgeFeeDemo {

    public static void main(String[] args) {

        SurgeFeeCalculator calculator =
            new SurgeFeeCalculator(1.0);

        System.out.println(
            "Rs " +
            calculator.calculateSurgeFee(500, 0)
        );

        System.out.println(
            "Rs " +
            calculator.calculateSurgeFee(500, 1)
        );

        System.out.println(
            "Rs " +
            calculator.calculateSurgeFee(500, 16)
        );
    }
}
