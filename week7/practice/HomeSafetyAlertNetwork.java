package week7.practice;

public class HomeSafetyAlertNetwork {

    // Interface
    interface Alertable {

        String sendAlert(String message);
    }

    // Base class
    static class SecuritySensor {

        protected String zoneName;

        public SecuritySensor(
                String zoneName) {

            if (zoneName == null
                    || zoneName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Zone name cannot be blank");
            }

            this.zoneName =
                    zoneName.trim();
        }

        public String getZoneName() {

            return zoneName;
        }
    }

    // Extends SecuritySensor AND implements Alertable
    static class MotionSensor
            extends SecuritySensor
            implements Alertable {

        public MotionSensor(
                String zoneName) {

            super(zoneName);
        }

        @Override
        public String sendAlert(
                String message) {

            return "["
                    + zoneName
                    + "] "
                    + message;
        }
    }

    /*
     * Multilevel inheritance:
     *
     * SecuritySensor
     *       ↓
     * MotionSensor
     *       ↓
     * DualZoneMotionSensor
     */
    static class DualZoneMotionSensor
            extends MotionSensor {

        private String secondZoneName;

        public DualZoneMotionSensor(
                String zoneName,
                String secondZoneName) {

            super(zoneName);

            if (secondZoneName == null
                    || secondZoneName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Second zone cannot be blank");
            }

            this.secondZoneName =
                    secondZoneName.trim();
        }

        @Override
        public String sendAlert(
                String message) {

            /*
             * Reuse the parent's implementation.
             */
            return super.sendAlert(message)
                    + " [also covering "
                    + secondZoneName
                    + "]";
        }
    }

    /*
     * Completely independent class.
     *
     * It implements Alertable but does not
     * extend SecuritySensor.
     */
    static class SmokeDetector
            implements Alertable {

        private String deviceId;

        public SmokeDetector(
                String deviceId) {

            if (deviceId == null
                    || deviceId.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Device ID cannot be blank");
            }

            this.deviceId =
                    deviceId.trim();
        }

        @Override
        public String sendAlert(
                String message) {

            return "["
                    + deviceId
                    + "] "
                    + message;
        }
    }

    /*
     * Interface polymorphism.
     *
     * Every object only needs to implement
     * Alertable.
     */
    static void broadcastAll(
            Alertable[] devices,
            String message) {

        for (Alertable device : devices) {

            System.out.println(
                    device.sendAlert(message));
        }
    }

    /*
     * Safe downcasting using instanceof.
     */
    static String getZoneIfMotionSensor(
            Alertable a) {

        if (a instanceof MotionSensor) {

            MotionSensor motion =
                    (MotionSensor) a;

            return motion.getZoneName();
        }

        return "Not a motion sensor";
    }

    public static void main(String[] args) {

        MotionSensor m =
                new MotionSensor(
                        "Living Room");

        System.out.println(
                m.sendAlert(
                        "Motion detected"));

        DualZoneMotionSensor d =
                new DualZoneMotionSensor(
                        "Hallway",
                        "Stairwell");

        System.out.println(
                d.sendAlert(
                        "Motion detected"));

        SmokeDetector s =
                new SmokeDetector("SD-01");

        System.out.println(
                s.sendAlert(
                        "Smoke detected"));

        System.out.println();

        Alertable[] devices = {
            m,
            d,
            s
        };

        broadcastAll(
                devices,
                "Security alert");

        System.out.println();

        System.out.println(
                getZoneIfMotionSensor(m));

        System.out.println(
                getZoneIfMotionSensor(s));
    }
}
