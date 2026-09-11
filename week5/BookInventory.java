package week5;

public class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                    "Construction rejected");
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public static void main(String[] args) {

        try {

            new BookInventory(0);

        } catch (IllegalArgumentException e) {

            System.out.println("Construction rejected");
        }

        BookInventory b =
                new BookInventory(3);

        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut();

        System.out.println(
                "Available after 4 checkouts: "
                + b.getCopiesAvailable());

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn();

        System.out.println(
                "Available after 4 check-ins: "
                + b.getCopiesAvailable());
    }
}