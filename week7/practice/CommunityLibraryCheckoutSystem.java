package week7.practice;

public class CommunityLibraryCheckoutSystem {

    // Abstract parent
    static abstract class LibraryItem {

        private static int itemCounter = 1000;

        private final String itemId;

        public LibraryItem() {

            itemCounter++;

            itemId =
                    "LIB-" + itemCounter;
        }

        public abstract int getLoanPeriodDays();

        public String getItemId() {

            return itemId;
        }
    }

    // Renewable capability
    interface Renewable {

        String renew();
    }

    // Reservable capability
    interface Reservable {

        String reserve();
    }

    /*
     * Textbook:
     * IS-A LibraryItem
     * CAN-DO Renewable
     * CAN-DO Reservable
     */
    static class Textbook
            extends LibraryItem
            implements Renewable, Reservable {

        private String title;

        public Textbook(
                String title) {

            super();

            if (title == null
                    || title.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Title cannot be blank");
            }

            this.title =
                    title.trim();
        }

        @Override
        public int getLoanPeriodDays() {

            return 14;
        }

        @Override
        public String renew() {

            return title
                    + " renewed";
        }

        @Override
        public String reserve() {

            return title
                    + " reserved";
        }
    }

    /*
     * Magazine:
     * IS-A LibraryItem
     * CAN-DO Renewable
     *
     * It does NOT implement Reservable.
     */
    static class Magazine
            extends LibraryItem
            implements Renewable {

        private String title;

        public Magazine(
                String title) {

            super();

            if (title == null
                    || title.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Title cannot be blank");
            }

            this.title =
                    title.trim();
        }

        @Override
        public int getLoanPeriodDays() {

            return 7;
        }

        @Override
        public String renew() {

            return title
                    + " renewed";
        }
    }

    /*
     * DigitalPass has NO relationship with
     * LibraryItem.
     *
     * It only implements Renewable.
     */
    static class DigitalPass
            implements Renewable {

        private String resourceName;

        public DigitalPass(
                String resourceName) {

            if (resourceName == null
                    || resourceName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Resource name cannot be blank");
            }

            this.resourceName =
                    resourceName.trim();
        }

        @Override
        public String renew() {

            return resourceName
                    + " renewed";
        }
    }

    /*
     * Polymorphism over LibraryItem[].
     *
     * DigitalPass cannot be placed in this
     * array because it is not a LibraryItem.
     */
    static void processCheckouts(
            LibraryItem[] items) {

        for (LibraryItem item : items) {

            System.out.println(
                    item.getLoanPeriodDays()
                    + " days");
        }
    }

    /*
     * Accepts Object so it can work with ANY
     * object, not just LibraryItem.
     *
     * Then checks for the Reservable
     * capability.
     */
    static String reserveIfSupported(
            Object o) {

        if (o instanceof Reservable) {

            Reservable reservable =
                    (Reservable) o;

            return reservable.reserve();
        }

        return "Reservation not supported";
    }

    public static void main(String[] args) {

        Textbook t =
                new Textbook(
                        "Java Fundamentals");

        System.out.println(
                t.getLoanPeriodDays());

        System.out.println(
                t.renew());

        System.out.println(
                t.reserve());

        System.out.println();

        Magazine m =
                new Magazine(
                        "Tech Monthly");

        System.out.println(
                reserveIfSupported(m));

        System.out.println();

        DigitalPass d =
                new DigitalPass(
                        "E-Journal Access");

        System.out.println(
                reserveIfSupported(d));

        System.out.println();

        /*
         * Upcasting:
         * Textbook → LibraryItem
         */
        LibraryItem ref = t;

        System.out.println(
                reserveIfSupported(ref));

        System.out.println();

        /*
         * Polymorphic processing.
         */
        LibraryItem[] items = {
            t,
            m
        };

        processCheckouts(items);
    }
}