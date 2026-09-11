package week3.Practice;

public class FeeHostelManagementMiniSystem {

    static class FeeAccount {

        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(
                String regNo,
                double totalFee,
                double amountPaid) {

            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        void pay(double amount) {

            if (amount <= 0) {

                System.out.println(
                        "Payment rejected for "
                        + regNo);

                return;
            }

            amountPaid += amount;

            if (amountPaid > totalFee) {
                amountPaid = totalFee;
            }
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount
            extends FeeAccount {

        HostelFeeAccount(
                String regNo,
                double totalFee,
                double amountPaid) {

            super(
                    regNo,
                    totalFee,
                    amountPaid);
        }

        void payInTwoInstallments(
                double amount) {

            if (amount <= 0) {
                return;
            }

            pay(amount);
        }
    }

    static class HostelRoom {

        String roomNo;
        int beds;
        int occupied;

        HostelRoom(
                String roomNo,
                int beds,
                int occupied) {

            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        boolean allot(String studentName) {

            if (occupied < beds) {

                occupied++;

                System.out.println(
                        studentName
                        + " allotted to room "
                        + roomNo);

                return true;
            }

            return false;
        }
    }

    static HostelRoom findAvailableRoom(
            HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {

            if (room != null &&
                    room.occupied < room.beds) {

                return room;
            }
        }

        return null;
    }

    static class SrmStudent {

        String name;
        String regNo;
        HostelFeeAccount feeAccount;
        HostelRoom room;

        static int totalStudents = 0;

        SrmStudent(
                String name,
                String regNo,
                HostelFeeAccount feeAccount) {

            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;

            totalStudents++;
        }

        String fullStatus() {

            String roomNumber;

            if (room == null) {
                roomNumber = "unallotted";
            } else {
                roomNumber = room.roomNo;
            }

            return name
                    + " | Due: Rs "
                    + feeAccount.getDue()
                    + " | Room: "
                    + roomNumber;
        }
    }

    static void allotStudent(
            HostelRoom[] rooms,
            SrmStudent student) {

        HostelRoom available =
                findAvailableRoom(rooms);

        if (available == null) {

            System.out.println(
                    "No rooms available for "
                    + student.name);

        } else {

            if (available.allot(student.name)) {
                student.room = available;
            }
        }
    }

    public static void main(String[] args) {

        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };

        SrmStudent ravi =
                new SrmStudent(
                        "Ravi",
                        "RA231100301011",
                        new HostelFeeAccount(
                                "RA231100301011",
                                150000,
                                10000));

        SrmStudent anitha =
                new SrmStudent(
                        "Anitha",
                        "RA231100301012",
                        new HostelFeeAccount(
                                "RA231100301012",
                                180000,
                                0));

        SrmStudent karthik =
                new SrmStudent(
                        "Karthik",
                        "RA231100301013",
                        new HostelFeeAccount(
                                "RA231100301013",
                                200000,
                                0));

        // Valid payment
        ravi.feeAccount.pay(0);
        ravi.feeAccount.pay(0);

        // Valid payment
        ravi.feeAccount.pay(0);

        // Negative payment is rejected
        anitha.feeAccount.pay(-5000);

        // Allot rooms to only two students
        allotStudent(rooms, ravi);
        allotStudent(rooms, anitha);

        // Karthik intentionally remains unallotted

        System.out.println();

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());

        System.out.println(
                "Total students: "
                + SrmStudent.totalStudents);
    }
}