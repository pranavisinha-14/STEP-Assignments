package week4;

class FoodOrder {

    private String studentName;
    private String dishName;
    private boolean delivered;

    FoodOrder(String studentName, String dishName) {

        if (studentName == null ||
            studentName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Invalid student name"
            );
        }

        if (dishName == null ||
            dishName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Invalid dish name"
            );
        }

        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    void markDelivered() {

        if (!delivered) {

            delivered = true;

            System.out.println(
                "Order marked as delivered."
            );

        } else {

            System.out.println(
                "Order was already delivered."
            );
        }
    }

    static void processBatch(String[][] rawOrders) {

        int valid = 0;
        int rejected = 0;

        for (String[] order : rawOrders) {

            try {

                new FoodOrder(
                    order[0],
                    order[1]
                );

                valid++;

            } catch (Exception e) {

                rejected++;
            }
        }

        System.out.println(
            "Valid: " + valid +
            " | Rejected: " + rejected
        );
    }
}

public class GhostOrderValidator {

    public static void main(String[] args) {

        String[][] orders = {

            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", "   "},
            {"Divya", "Veg Biryani"}
        };

        FoodOrder.processBatch(orders);

        FoodOrder order =
            new FoodOrder("Ravi", "Dosa");

        order.markDelivered();
        order.markDelivered();
    }
}
