package week4;

class Canteen {

    private String canteenCode;
    private String canteenName;
    private int trustScore;

    Canteen(
        String canteenCode,
        String canteenName,
        int trustScore
    ) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    Canteen(
        String canteenCode,
        String canteenName
    ) {
        this(canteenCode, canteenName, 3);
    }

    String getCanteenCode() {
        return canteenCode;
    }

    int compareTo(Canteen other) {

        // Higher trust score comes first.
        if (this.trustScore != other.trustScore) {

            return other.trustScore - this.trustScore;
        }

        // Codes are compared without changing stored values.
        int result =
            this.canteenCode.compareToIgnoreCase(
                other.canteenCode
            );

        if (result != 0) {
            return result;
        }

        // Final tie-breaker: shorter name first.
        return this.canteenName.length()
            - other.canteenName.length();
    }

    static Canteen[] rankCanteens(
        Canteen[] canteens
    ) {

        // Manual selection sort.
        for (int i = 0; i < canteens.length - 1; i++) {

            int best = i;

            for (int j = i + 1;
                 j < canteens.length;
                 j++) {

                if (canteens[j].compareTo(
                        canteens[best]) < 0) {

                    best = j;
                }
            }

            Canteen temp = canteens[i];
            canteens[i] = canteens[best];
            canteens[best] = temp;
        }

        return canteens;
    }
}

public class CanteenRanking {

    public static void main(String[] args) {

        Canteen[] canteens = {

            new Canteen(
                "HB3-C",
                "Spice Junction",
                3
            ),

            new Canteen(
                "hb1-c",
                "Grand Mess",
                5
            ),

            new Canteen(
                "HB2-C",
                "Southern Treats"
            )
        };

        Canteen.rankCanteens(canteens);

        for (Canteen c : canteens) {
            System.out.println(c.getCanteenCode());
        }
    }
}