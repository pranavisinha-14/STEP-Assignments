package week3.Practice;

public class InstanceStaticBoundary {

    // BROKEN VERSION
    static class BrokenSrmStudent {

        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(
                String name,
                String regNo,
                int attendance) {

            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }
    }

    // FIXED VERSION
    static class SrmStudent {

        // These belong to each individual student.
        String name;
        String regNo;
        int attendance;

        // These belong to the class as a whole.
        static String university =
                "SRM Institute of Science and Technology";

        static int admissionCount = 0;

        SrmStudent(
                String name,
                int attendance) {

            this.name = name;
            this.attendance = attendance;

            admissionCount++;

            this.regNo =
                    "RA2311003010"
                    + admissionCount;
        }

        void printIdCard() {

            System.out.println(
                    name + " | " + regNo);
        }

        static void printTotalAdmissions() {

            System.out.println(
                    "Students admitted so far: "
                    + admissionCount);
        }
    }

    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenSrmStudent student1 =
                new BrokenSrmStudent(
                        "Ravi",
                        "RA231100301011",
                        82);

        BrokenSrmStudent student2 =
                new BrokenSrmStudent(
                        "Meera",
                        "RA231100301012",
                        91);

        System.out.println(
                student1.name);

        System.out.println(
                student2.name);

        /*
         * name, regNo and attendance should NOT be static.
         * They describe individual students.
         *
         * Since static fields are shared by the class,
         * creating Meera overwrites Ravi's data.
         */

        System.out.println();
        System.out.println("Fixed version:");

        SrmStudent ravi =
                new SrmStudent("Ravi", 82);

        SrmStudent meera =
                new SrmStudent("Meera", 91);

        ravi.printIdCard();
        meera.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}