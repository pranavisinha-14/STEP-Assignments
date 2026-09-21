package week7;

public class FleetMaintenanceTracker {

    // Abstract parent class
    static abstract class ServiceableVehicle {

        private double mileage;

        public ServiceableVehicle() {

            mileage = 0.0;
        }

        // Abstract method
        public abstract String performMaintenance();

        // Getter
        public double getMileage() {

            return mileage;
        }

        // Add mileage
        public void addMileage(
                double km) {

            /*
             * Reject negative distance.
             */
            if (km < 0) {

                return;
            }

            mileage += km;
        }
    }

    // Insurance interface
    interface Insurable {

        String getInsuranceInfo();
    }

    /*
     * Forklift extends the abstract class
     * and implements Insurable.
     */
    static class Forklift
            extends ServiceableVehicle
            implements Insurable {

        protected String assetTag;

        public Forklift(
                String assetTag) {

            if (assetTag == null
                    || assetTag.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Asset tag cannot be blank");
            }

            this.assetTag =
                    assetTag.trim();
        }

        @Override
        public String performMaintenance() {

            return "Forklift "
                    + assetTag
                    + ": hydraulic and fork "
                    + "inspection complete";
        }

        @Override
        public String getInsuranceInfo() {

            return "Insured under fleet policy - "
                    + "Asset "
                    + assetTag;
        }
    }

    /*
     * Multilevel inheritance:
     *
     * ServiceableVehicle
     *        ↓
     *     Forklift
     *        ↓
     * HeavyDutyForklift
     */
    static class HeavyDutyForklift
            extends Forklift {

        public HeavyDutyForklift(
                String assetTag) {

            super(assetTag);
        }

        @Override
        public String performMaintenance() {

            /*
             * Reuse parent's maintenance
             * procedure.
             */
            return super.performMaintenance()
                    + " | high-pressure hydraulic "
                    + "check complete";
        }
    }

    /*
     * Checks whether the vehicle also
     * supports the Insurable capability.
     */
    static String getInsuranceIfApplicable(
            ServiceableVehicle v) {

        if (v instanceof Insurable) {

            Insurable insurable =
                    (Insurable) v;

            return insurable.getInsuranceInfo();
        }

        return "No insurance record exists";
    }

    public static void main(String[] args) {

        Forklift f =
                new Forklift("FL-22");

        f.addMileage(120);

        System.out.println(
                "Mileage: "
                + f.getMileage());

        System.out.println(
                f.performMaintenance());

        System.out.println();

        HeavyDutyForklift hd =
                new HeavyDutyForklift("HD-9");

        System.out.println(
                hd.performMaintenance());

        System.out.println();

        System.out.println(
                getInsuranceIfApplicable(f));

        System.out.println(
                getInsuranceIfApplicable(hd));

        /*
         * Negative mileage is rejected.
         */
        f.addMileage(-50);

        System.out.println(
                "Mileage after invalid update: "
                + f.getMileage());
    }
}