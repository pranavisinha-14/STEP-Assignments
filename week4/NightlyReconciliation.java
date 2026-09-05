package week4;

class DeliveryAccount {

    protected String studentId;
    protected double orderValue;

    static double minimumSurgePercent;

    static {
        minimumSurgePercent = 1.0;
    }

    DeliveryAccount(
        String studentId,
        double orderValue
    ) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    DeliveryAccount(String studentId) {

        this(studentId, 0);
    }

    final double calculateSurgeFee(
        int delayMinutes
    ) {

        if (orderValue < 0 ||
            delayMinutes < 0) {

            throw new IllegalArgumentException(
                "Invalid values"
            );
        }

        if (delayMinutes == 0) {
            return 0;
        }

        int first =
            Math.min(delayMinutes, 5);

        int second =
            Math.min(
                Math.max(delayMinutes - 5, 0),
                10
            );

        int third =
            Math.max(delayMinutes - 15, 0);

        double fee =
            first * orderValue * 0.005;

        fee +=
            second * orderValue * 0.01;

        fee +=
            third * orderValue * 0.02;

        double floor =
            orderValue *
            minimumSurgePercent / 100;

        return Math.max(fee, floor);
    }

    void processAccount(
        DeliveryAccount account,
        double amount,
        int delayMinutes
    ) {

        System.out.println(
            account.studentId +
            " | Surge Fee: Rs " +
            account.calculateSurgeFee(delayMinutes)
        );
    }
}

class PremiumDeliveryAccount
        extends DeliveryAccount {

    PremiumDeliveryAccount(
        String studentId,
        double orderValue
    ) {
        super(studentId, orderValue);
    }
}

public class NightlyReconciliation {

    static void processBatch(
        DeliveryAccount[] accounts,
        double[] amounts,
        int[] delayMinutesArray
    ) {

        if (accounts.length != amounts.length ||
            accounts.length != delayMinutesArray.length) {

            throw new IllegalArgumentException(
                "Parallel arrays must have equal lengths."
            );
        }

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double totalFee = 0;

        for (int i = 0; i < accounts.length; i++) {

            if (accounts[i] == null) {

                nullSkipped++;
                continue;
            }

            double fee =
                accounts[i].calculateSurgeFee(
                    delayMinutesArray[i]
                );

            if (accounts[i]
                    instanceof PremiumDeliveryAccount) {

                premium++;

                // Premium accounts receive a 50% settlement adjustment.
                fee *= 0.5;

            } else {

                regular++;
            }

            totalFee += fee;
            processed++;
        }

        System.out.println(
            processed + " processed | " +
            nullSkipped + " null skipped | " +
            premium + " premium | " +
            regular + " regular | " +
            "grand total surge fees = Rs " +
            totalFee
        );
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {

            new PremiumDeliveryAccount(
                "STU001", 500
            ),

            null,

            new DeliveryAccount(
                "STU002", 300
            )
        };

        double[] amounts = {
            500, 400, 300
        };

        int[] delays = {
            10, 5, 0
        };

        processBatch(
            accounts,
            amounts,
            delays
        );
    }
}
