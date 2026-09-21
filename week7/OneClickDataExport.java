package week7;

public class OneClickDataExport {

    // Common interface
    interface Exportable {

        String exportData();
    }

    /*
     * Shared counter for ALL exports.
     *
     * It is stored in the outer class because
     * ReportGenerator and UserProfile have no
     * common parent class.
     */
    private static int totalExports = 0;

    // Report class
    static class ReportGenerator
            implements Exportable {

        private String reportName;

        public ReportGenerator(
                String reportName) {

            if (reportName == null
                    || reportName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Report name cannot be blank");
            }

            this.reportName =
                    reportName.trim();
        }

        @Override
        public String exportData() {

            totalExports++;

            return "Exported report: "
                    + reportName;
        }
    }

    // User profile class
    static class UserProfile
            implements Exportable {

        private String username;

        public UserProfile(
                String username) {

            if (username == null
                    || username.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Username cannot be blank");
            }

            this.username =
                    username.trim();
        }

        @Override
        public String exportData() {

            totalExports++;

            return "Exported profile: "
                    + username;
        }
    }

    // Static getter
    static int getTotalExports() {

        return totalExports;
    }

    /*
     * Polymorphic method.
     */
    static void exportAll(
            Exportable[] items) {

        for (Exportable item : items) {

            System.out.println(
                    item.exportData());
        }
    }

    public static void main(String[] args) {

        ReportGenerator r =
                new ReportGenerator(
                        "Sales Q1");

        System.out.println(
                r.exportData());

        UserProfile u =
                new UserProfile(
                        "jane_doe");

        System.out.println(
                u.exportData());

        /*
         * Upcasting:
         * ReportGenerator object stored
         * in an Exportable reference.
         */
        Exportable ref = r;

        System.out.println();

        exportAll(
                new Exportable[] {
                    ref,
                    u
                });

        System.out.println();

        System.out.println(
                "Total exports: "
                + getTotalExports());
    }
}