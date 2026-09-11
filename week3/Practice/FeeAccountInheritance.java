package week3.Practice;

public class FeeAccountInheritance {

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

            super(regNo, totalFee, amountPaid);
        }

        void payInTwoInstallments(double amount) {

            if (amount <= 0) {
                return;
            }

            // The supplied amount is treated as the
            // installment payment.
            pay(amount);
        }
    }

    static class ScholarshipFeeAccount
            extends FeeAccount {

        private double scholarshipPercent;

        ScholarshipFeeAccount(
                String regNo,
                double totalFee,
                double amountPaid,
                double scholarshipPercent) {

            super(regNo, totalFee, amountPaid);

            if (scholarshipPercent < 0) {
                scholarshipPercent = 0;
            }

            if (scholarshipPercent > 100) {
                scholarshipPercent = 100;
            }

            this.scholarshipPercent =
                    scholarshipPercent;
        }

        double effectiveDue() {

            double due = getDue();

            return due -
                    (due * scholarshipPercent / 100);
        }
    }

    public static void main(String[] args) {

        FeeAccount plain =
                new FeeAccount(
                        "RA101",
                        150000,
                        0);

        HostelFeeAccount hostel =
                new HostelFeeAccount(
                        "RA102",
                        200000,
                        0);

        ScholarshipFeeAccount scholarship =
                new ScholarshipFeeAccount(
                        "RA103",
                        180000,
                        0,
                        20);

        // Normal account payment
        plain.pay(150000);

        // Hostel account payment
        hostel.payInTwoInstallments(60000);

        // Demonstrate instanceof
        FeeAccount[] accounts = {
            plain,
            hostel,
            scholarship
        };

        for (FeeAccount account : accounts) {

            if (account instanceof
                    ScholarshipFeeAccount) {

                ScholarshipFeeAccount s =
                        (ScholarshipFeeAccount) account;

                System.out.println(
                        "Scholarship account effective due: Rs "
                        + s.effectiveDue());

            } else if (account instanceof
                    HostelFeeAccount) {

                HostelFeeAccount h =
                        (HostelFeeAccount) account;

                System.out.println(
                        "Hostel account due: Rs "
                        + h.getDue());

            } else {

                System.out.println(
                        "Plain account due: Rs "
                        + account.getDue());
            }
        }
    }
}