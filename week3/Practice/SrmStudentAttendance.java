package week3.Practice;

public class SrmStudentAttendance {

    static class SrmStudent {

        String name;
        String regNo;
        int attendance;

        SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            this.attendance = attendance;
        }

        void addAttendanceUpdate(int newAttendance) {
            attendance = newAttendance;
        }

        boolean isEligible() {
            return attendance >= 75;
        }

        /*
         * classAverage() is static because it calculates the average
         * of many students and does not belong to one particular student.
         *
         * isEligible() is not static because it checks the attendance
         * of one particular SrmStudent object.
         */
        static double classAverage(SrmStudent[] students) {

            int total = 0;

            for (SrmStudent student : students) {
                total += student.attendance;
            }

            return (double) total / students.length;
        }
    }

    public static void main(String[] args) {

        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA231100301001", 82),
            new SrmStudent("Anitha", "RA231100301002", 68),
            new SrmStudent("Karthik", "RA231100301003", 91),
            new SrmStudent("Meera", "RA231100301004", 74),
            new SrmStudent("Suresh", "RA231100301005", 60)
        };

        for (SrmStudent student : students) {

            String status = student.isEligible()
                    ? "Eligible"
                    : "Detained";

            System.out.println(
                    student.name + " - "
                    + student.attendance + "% - "
                    + status);
        }

        double average =
                SrmStudent.classAverage(students);

        System.out.printf(
                "Class average: %.1f%%%n",
                average);
    }
}