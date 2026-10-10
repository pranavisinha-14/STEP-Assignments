package week8;
import java.util.*;

public class SwiftShipParcelTracker {
    interface ShippingType {
        double calculateCharge(double weight);
        String getName();
    }

    static class Standard implements ShippingType {
        public double calculateCharge(double weight) {
            return 40 + 10 * weight;
        }

        public String getName() {
            return "Standard";
        }
    }

    static class Express implements ShippingType {
        public double calculateCharge(double weight) {
            return 80 + 15 * weight;
        }

        public String getName() {
            return "Express";
        }
    }

    static class Fragile implements ShippingType {
        public double calculateCharge(double weight) {
            return new Standard().calculateCharge(weight) + 50;
        }

        public String getName() {
            return "Fragile";
        }
    }

    interface NotificationChannel {
        void notifyStatus(String id, String status);
    }

    static class SmsChannel implements NotificationChannel {
        public void notifyStatus(String id, String status) {
            System.out.println("[SMS] " + id + " is now " + status + ".");
        }
    }

    static class EmailChannel implements NotificationChannel {
        public void notifyStatus(String id, String status) {
            System.out.println("[Email] " + id + " is now " + status + ".");
        }
    }

    static class Parcel {
        enum Status {
            BOOKED, PICKED_UP, IN_TRANSIT,
            OUT_FOR_DELIVERY, DELIVERED, CANCELLED
        }

        String id;
        double weight, charge;
        ShippingType shipping;
        Status status = Status.BOOKED;
        List<NotificationChannel> channels = new ArrayList<>();

        Parcel(String id, double weight, ShippingType shipping,
               List<NotificationChannel> channels) {
            if (weight <= 0)
                throw new IllegalArgumentException("Weight must be positive.");

            this.id = id;
            this.weight = weight;
            this.shipping = shipping;
            this.charge = shipping.calculateCharge(weight);
            this.channels.addAll(channels);
        }

        void notifyChannels() {
            for (NotificationChannel channel : channels)
                channel.notifyStatus(id, status.name());
        }

        boolean cancel() {
            if (status != Status.BOOKED) {
                System.out.println("Cancellation failed: " + id
                        + " can be cancelled only while BOOKED.");
                return false;
            }

            status = Status.CANCELLED;
            notifyChannels();
            return true;
        }

        boolean updateStatus(Status next) {
            Status expected = null;

            switch (status) {
                case BOOKED:
                    expected = Status.PICKED_UP;
                    break;
                case PICKED_UP:
                    expected = Status.IN_TRANSIT;
                    break;
                case IN_TRANSIT:
                    expected = Status.OUT_FOR_DELIVERY;
                    break;
                case OUT_FOR_DELIVERY:
                    expected = Status.DELIVERED;
                    break;
                default:
                    break;
            }

            if (next != expected) {
                System.out.println("Invalid transition: " + status
                        + " → " + next + " is not allowed.");
                return false;
            }

            status = next;
            notifyChannels();
            return true;
        }
    }

    public static void main(String[] args) {
        List<NotificationChannel> channels = Arrays.asList(
                new SmsChannel(), new EmailChannel());

        Parcel parcel = new Parcel(
                "P101", 2, new Express(), channels);

        System.out.println("Parcel " + parcel.id + " booked ("
                + parcel.shipping.getName() + ", "
                + parcel.weight + " kg).");

        System.out.printf(Locale.US, "Charge: ₹%.2f.%n",
                parcel.charge);

        parcel.notifyChannels();
        parcel.updateStatus(Parcel.Status.PICKED_UP);
        parcel.cancel();
        parcel.updateStatus(Parcel.Status.IN_TRANSIT);
        parcel.updateStatus(Parcel.Status.DELIVERED);
    }
}