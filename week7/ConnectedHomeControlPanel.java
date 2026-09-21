package week7;

public class ConnectedHomeControlPanel {

    // Abstract parent class
    static abstract class HomeDevice {

        private static int serialCounter = 1000;

        private final String serialNumber;

        public HomeDevice() {

            serialCounter++;

            serialNumber =
                    "HD-" + serialCounter;
        }

        // Abstract method
        public abstract String activate();

        public String getSerialNumber() {

            return serialNumber;
        }
    }

    // Remote control interface
    interface RemoteControllable {

        String connect(String appId);
    }

    // Energy interface
    interface EnergyTrackable {

        double getConsumptionWatts();
    }

    /*
     * WashingMachine:
     * IS-A HomeDevice
     * CAN-DO RemoteControllable
     * CAN-DO EnergyTrackable
     */
    static class WashingMachine
            extends HomeDevice
            implements RemoteControllable,
                       EnergyTrackable {

        private double consumptionWatts;

        public WashingMachine(
                double consumptionWatts) {

            if (consumptionWatts <= 0) {

                throw new IllegalArgumentException(
                        "Consumption must be positive");
            }

            this.consumptionWatts =
                    consumptionWatts;
        }

        @Override
        public String activate() {

            return "Washing machine "
                    + getSerialNumber()
                    + " started a cycle";
        }

        @Override
        public String connect(
                String appId) {

            if (appId == null
                    || appId.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "App ID cannot be blank");
            }

            return getSerialNumber()
                    + " connected to "
                    + appId;
        }

        @Override
        public double getConsumptionWatts() {

            return consumptionWatts;
        }
    }

    /*
     * Refrigerator:
     * IS-A HomeDevice
     * CAN-DO EnergyTrackable
     *
     * It does NOT implement
     * RemoteControllable.
     */
    static class Refrigerator
            extends HomeDevice
            implements EnergyTrackable {

        private double consumptionWatts;

        public Refrigerator(
                double consumptionWatts) {

            if (consumptionWatts <= 0) {

                throw new IllegalArgumentException(
                        "Consumption must be positive");
            }

            this.consumptionWatts =
                    consumptionWatts;
        }

        @Override
        public String activate() {

            return "Refrigerator "
                    + getSerialNumber()
                    + " activated";
        }

        @Override
        public double getConsumptionWatts() {

            return consumptionWatts;
        }
    }

    /*
     * MobileApp is NOT a HomeDevice.
     *
     * It only implements
     * RemoteControllable.
     */
    static class MobileApp
            implements RemoteControllable {

        private String appName;

        public MobileApp(
                String appName) {

            if (appName == null
                    || appName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "App name cannot be blank");
            }

            this.appName =
                    appName.trim();
        }

        @Override
        public String connect(
                String appId) {

            if (appId == null
                    || appId.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "App ID cannot be blank");
            }

            return appName
                    + " connected to "
                    + appId;
        }
    }

    /*
     * Polymorphic connection method.
     */
    static void connectAll(
            RemoteControllable[] items,
            String appId) {

        for (RemoteControllable item
                : items) {

            System.out.println(
                    item.connect(appId));
        }
    }

    /*
     * Safe instanceof checking.
     */
    static double getConsumptionIfTrackable(
            HomeDevice d) {

        if (d instanceof EnergyTrackable) {

            EnergyTrackable trackable =
                    (EnergyTrackable) d;

            return trackable
                    .getConsumptionWatts();
        }

        return 0.0;
    }

    public static void main(String[] args) {

        WashingMachine wm =
                new WashingMachine(500.0);

        System.out.println(
                wm.activate());

        System.out.println(
                wm.connect("HomeConnect"));

        System.out.println(
                "Consumption: "
                + wm.getConsumptionWatts()
                + " W");

        System.out.println();

        Refrigerator fridge =
                new Refrigerator(150.0);

        System.out.println(
                "Refrigerator consumption: "
                + getConsumptionIfTrackable(
                        fridge)
                + " W");

        System.out.println();

        MobileApp app =
                new MobileApp(
                        "HomeConnect App");

        System.out.println(
                app.connect("HomeConnect"));

        System.out.println();

        /*
         * Upcasting:
         * WashingMachine → HomeDevice
         */
        HomeDevice ref = wm;

        System.out.println(
                "Upcast consumption: "
                + getConsumptionIfTrackable(
                        ref)
                + " W");

        System.out.println();

        /*
         * Mixed RemoteControllable array.
         */
        connectAll(
                new RemoteControllable[] {
                    wm,
                    app
                },
                "HomeConnect");
    }
}