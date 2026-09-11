package week5;

import java.util.regex.Pattern;

public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    private static final Pattern BOOK_ID_PATTERN =
            Pattern.compile("BK-\\d{3}");

    // One-time shared initialization
    static {
        BOOK_ID_PATTERN.toString();
    }

    public LoanReceipt(String memberId,
                       String[] bookIds) {

        if (memberId == null || bookIds == null) {
            throw new IllegalArgumentException(
                    "Construction rejected");
        }

        if (bookIds.length > 20) {
            throw new IllegalArgumentException(
                    "Too many book IDs");
        }

        for (String id : bookIds) {

            if (id == null
                    || !BOOK_ID_PATTERN.matcher(id).matches()) {

                throw new IllegalArgumentException(
                        "Construction rejected");
            }
        }

        this.memberId = memberId;

        // Defensive copy
        this.bookIds = bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {

        // Defensive copy
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0
                || index >= bookIds.length
                || newId == null
                || !BOOK_ID_PATTERN.matcher(newId).matches()) {

            throw new IllegalArgumentException(
                    "Invalid book ID or index");
        }

        String[] correctedIds =
                bookIds.clone();

        correctedIds[index] = newId;

        return new LoanReceipt(
                memberId,
                correctedIds);
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | "
                    + "0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        // Invalid receipt
        try {

            new LoanReceipt(
                    "LIB-8841",
                    new String[]{
                            "BK-100",
                            "bad"
                    });

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Construction rejected");
        }

        // Normal receipt
        LoanReceipt receipt =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{
                                "BK-100",
                                "BK-101"
                        });

        // Test defensive copying
        String[] ids =
                receipt.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
                "Original first ID: "
                + receipt.getBookIds()[0]);

        // Test with-style correction
        LoanReceipt corrected =
                receipt.withCorrectedBookId(
                        0,
                        "BK-999");

        System.out.println(
                "Corrected first ID: "
                + corrected.getBookIds()[0]);

        // Test nightly circulation
        LoanReceipt[] receipts = {

            new ReferenceOnlyLoanReceipt(
                    "LIB-001",
                    new String[]{"BK-200"},
                    "Reading Room 3"),

            null,

            new LoanReceipt(
                    "LIB-002",
                    new String[]{"BK-201"})
        };

        System.out.println(
                processNightlyCirculation(receipts));
    }
}


// Reference-only receipt
class ReferenceOnlyLoanReceipt
        extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}