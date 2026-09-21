package week7.practice;

public class QuarterlyBonusCalculator {

    // Abstract parent
    static abstract class StaffMember {

        private double baseSalary;

        protected double bonusRate;

        /*
         * Simple constructor.
         *
         * Constructor chaining using this().
         */
        public StaffMember(
                double baseSalary) {

            this(baseSalary, 0.10);
        }

        /*
         * Richer constructor.
         */
        public StaffMember(
                double baseSalary,
                double bonusRate) {

            if (baseSalary < 0) {

                throw new IllegalArgumentException(
                        "Salary cannot be negative");
            }

            if (bonusRate < 0) {

                throw new IllegalArgumentException(
                        "Bonus rate cannot be negative");
            }

            this.baseSalary =
                    baseSalary;

            this.bonusRate =
                    bonusRate;
        }

        // Abstract method
        public abstract double calculateBonus();

        // JavaBean getter
        public double getSalary() {

            return baseSalary;
        }

        // JavaBean setter
        public void setSalary(
                double baseSalary) {

            /*
             * Negative salary is rejected.
             */
            if (baseSalary < 0) {

                return;
            }

            this.baseSalary =
                    baseSalary;
        }
    }

    // Separate interface
    interface Auditable {

        String auditRecord();
    }

    /*
     * TeamLead extends StaffMember
     * AND implements Auditable.
     */
    static class TeamLead
            extends StaffMember
            implements Auditable {

        private int teamSize;

        /*
         * Constructor using default
         * bonus rate of 10%.
         */
        public TeamLead(
                double baseSalary,
                int teamSize) {

            super(
                    baseSalary);

            this.teamSize =
                    teamSize;
        }

        /*
         * Constructor with custom rate.
         */
        public TeamLead(
                double baseSalary,
                double bonusRate,
                int teamSize) {

            super(
                    baseSalary,
                    bonusRate);

            this.teamSize =
                    teamSize;
        }

        @Override
        public double calculateBonus() {

            return getSalary()
                    * bonusRate;
        }

        @Override
        public String auditRecord() {

            return "TeamLead audit: "
                    + teamSize
                    + " team members, salary $"
                    + getSalary();
        }
    }

    /*
     * Accepts parent type.
     *
     * Checks for the Auditable capability
     * rather than checking specifically for
     * TeamLead.
     */
    static String getAuditIfApplicable(
            StaffMember s) {

        if (s instanceof Auditable) {

            Auditable auditable =
                    (Auditable) s;

            return auditable.auditRecord();
        }

        return "No audit required";
    }

    public static void main(String[] args) {

        TeamLead t =
                new TeamLead(
                        60000,
                        5);

        System.out.println(
                t.calculateBonus());

        TeamLead t2 =
                new TeamLead(
                        60000,
                        0.20,
                        5);

        System.out.println(
                t2.calculateBonus());

        /*
         * Negative salary should be rejected.
         * Original salary remains unchanged.
         */
        t.setSalary(-5000);

        System.out.println(
                t.getSalary());

        /*
         * Upcasting:
         * TeamLead → StaffMember
         */
        StaffMember ref = t;

        System.out.println(
                getAuditIfApplicable(ref));
    }
}
