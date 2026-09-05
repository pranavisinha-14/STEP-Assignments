package week3;

public class BookIssue {

    String title;
    String borrowerName;
    int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    double fineAmount() {

        if (daysOverdue > 0) {
            return daysOverdue * 5;
        }

        return 0;
    }

    boolean isSeverelyOverdue() {

        return daysOverdue > 14;
    }

    // Static because this calculates the total for many objects.
    // fineAmount() is not static because it uses one book's data.
    static double totalFineCollected(BookIssue[] issues) {

        double total = 0;

        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }

        return total;
    }

    public static void main(String[] args) {

        BookIssue[] issues = {

            new BookIssue("Clean Code", "Aditi", 18),
            new BookIssue("Effective Java", "Rohan", 5),
            new BookIssue("Refactoring", "Meera", 0),
            new BookIssue("DSA Handbook", "Karan", 21),
            new BookIssue("Design Patterns", "Divya", 9)
        };

        for (BookIssue issue : issues) {

            if (issue.isSeverelyOverdue()) {

                System.out.println(
                    issue.title + " - " +
                    issue.daysOverdue +
                    " days - Severely overdue"
                );

            } else {

                System.out.println(
                    issue.title + " - " +
                    issue.daysOverdue +
                    " days - OK"
                );
            }
        }

        System.out.println(
            "Total fine collected: Rs " +
            BookIssue.totalFineCollected(issues)
        );
    }
}
