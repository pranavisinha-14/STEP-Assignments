package week8;

import java.util.*;

public class CampusCanteenSmartCard {
    interface PricingPlan {
        double price(double amount);
    }

    static class DayScholarPlan implements PricingPlan {
        public double price(double amount) {
            return amount;
        }
    }

    static class HostellerPlan implements PricingPlan {
        public double price(double amount) {
            return amount * 0.90;
        }
    }

    static class StaffPlan implements PricingPlan {
        public double price(double amount) {
            return amount * 0.80;
        }
    }

    static class Transaction {
        String description;
        double amount;

        Transaction(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
    }

    static class Purchase {
        String item;
        double charged;
        boolean refunded;

        Purchase(String item, double charged) {
            this.item = item;
            this.charged = charged;
        }
    }

    static class SmartCard {
        String id;
        PricingPlan plan;
        private double balance = 0;
        private boolean blocked = false;
        private List<Transaction> transactions = new ArrayList<>();
        private List<Purchase> purchases = new ArrayList<>();

        SmartCard(String id, PricingPlan plan) {
            this.id = id;
            this.plan = plan;
        }

        private void record(String description, double amount) {
            transactions.add(new Transaction(description, amount));
            balance += amount;
        }

        void topUp(double amount) {
            if (blocked) {
                System.out.println("Top-up failed: Card is blocked.");
                return;
            }
            if (amount < 100) {
                System.out.println("Top-up failed: Minimum top-up is ₹100.00.");
                return;
            }
            if (balance + amount > 5000) {
                System.out.println("Top-up failed: Maximum balance is ₹5,000.00.");
                return;
            }

            record("Top-up", amount);
            System.out.printf(Locale.US,
                    "%s topped up with ₹%.2f.%nBalance: ₹%.2f.%n",
                    id, amount, balance);
        }

        void purchase(String item, double basePrice) {
            if (blocked) {
                System.out.println("Purchase failed: Card is blocked.");
                return;
            }

            double charged = Math.round(plan.price(basePrice) * 100) / 100.0;

            if (charged > balance) {
                System.out.printf(Locale.US,
                        "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                        charged, balance);
                return;
            }

            record(item, -charged);
            purchases.add(new Purchase(item, charged));

            System.out.printf(Locale.US,
                    "%s purchased for ₹%.2f.%nBalance: ₹%.2f.%n",
                    item, charged, balance);
        }

        void refund(String item) {
            for (Purchase p : purchases) {
                if (p.item.equals(item)) {
                    if (p.refunded) {
                        System.out.println("Refund rejected: " + item
                                + " has already been refunded.");
                        return;
                    }

                    p.refunded = true;
                    record("Refund: " + item, p.charged);

                    System.out.printf(Locale.US,
                            "Refund of ₹%.2f for %s processed.%nBalance: ₹%.2f.%n",
                            p.charged, item, balance);
                    return;
                }
            }

            System.out.println("Refund rejected: purchase not found.");
        }

        void block() {
            blocked = true;
            System.out.println(id + " blocked.");
        }

        void unblock() {
            blocked = false;
            System.out.println(id + " unblocked.");
        }

        void statement() {
            System.out.print("Mini-statement for " + id + ": ");
            double total = 0;

            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                total += t.amount;

                System.out.printf(Locale.US, "%s%.2f",
                        t.amount >= 0 ? "+" : "", t.amount);

                if (i < transactions.size() - 1)
                    System.out.print(", ");
            }

            System.out.printf(Locale.US, " = ₹%.2f.%n", total);
        }
    }

    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());

        card.topUp(500);
        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Items worth ₹400", 400);
        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.statement();
    }
}