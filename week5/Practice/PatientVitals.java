package week5.Practice;

public class PatientVitals {

    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {

        readings = new double[500];
        count = 0;

        if (initialReadings != null) {

            for (double reading : initialReadings) {

                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {

        if (reading <= 0 || reading > 45) {
            return;
        }

        if (count < readings.length) {

            readings[count] = reading;
            count++;
        }
    }

    public double getAverage() {

        if (count == 0) {
            return 0.0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {

            sum += readings[i];
        }

        return sum / count;
    }

    public double[] getAllReadings() {

        double[] copy =
                new double[count];

        for (int i = 0; i < count; i++) {

            copy[i] = readings[i];
        }

        return copy;
    }

    public static void main(String[] args) {

        PatientVitals v =
                new PatientVitals(
                        new double[]{
                                36.5,
                                -2,
                                37.1
                        });

        printArray(v.getAllReadings());

        double[] copy =
                v.getAllReadings();

        copy[0] = 999;

        System.out.println(
                "After modifying copy: "
                + v.getAllReadings()[0]);

        v.recordReading(38.2);
        v.recordReading(50);

        System.out.println(
                "Average: "
                + v.getAverage());
    }

    static void printArray(double[] values) {

        System.out.print("[");

        for (int i = 0; i < values.length; i++) {

            System.out.print(values[i]);

            if (i < values.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}