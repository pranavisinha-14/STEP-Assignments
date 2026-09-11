package week5.Practice;

import java.util.regex.Pattern;

public class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    private static final Pattern MEDICATION_PATTERN =
            Pattern.compile("MED-[A-Z]");

    // One-time shared initialization
    static {
        MEDICATION_PATTERN.toString();
    }

    public DischargeSummary(String patientId,
                            String[] medicationCodes) {

        if (patientId == null
                || medicationCodes == null) {

            throw new IllegalArgumentException(
                    "construction rejected");
        }

        if (medicationCodes.length > 20) {

            throw new IllegalArgumentException(
                    "Too many medication codes");
        }

        for (String code : medicationCodes) {

            if (code == null
                    || !MEDICATION_PATTERN
                            .matcher(code)
                            .matches()) {

                throw new IllegalArgumentException(
                        "construction rejected");
            }
        }

        this.patientId = patientId;

        // Defensive copy
        this.medicationCodes =
                medicationCodes.clone();
    }

    public String getPatientId() {

        return patientId;
    }

    public String[] getMedicationCodes() {

        // Defensive copy
        return medicationCodes.clone();
    }

    public DischargeSummary
    withCorrectedMedication(
            int index,
            String newCode) {

        if (index < 0
                || index >= medicationCodes.length
                || newCode == null
                || !MEDICATION_PATTERN
                        .matcher(newCode)
                        .matches()) {

            throw new IllegalArgumentException(
                    "Invalid medication code or index");
        }

        String[] correctedCodes =
                medicationCodes.clone();

        correctedCodes[index] = newCode;

        return new DischargeSummary(
                patientId,
                correctedCodes);
    }

    static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries == null) {

            return "0 processed | 0 null skipped | "
                    + "0 critical-care | 0 routine";
        }

        for (DischargeSummary summary : summaries) {

            if (summary == null) {

                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof
                    CriticalCareDischargeSummary) {

                criticalCare++;

            } else {

                routine++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + criticalCare
                + " critical-care | "
                + routine
                + " routine";
    }

    public static void main(String[] args) {

        // Test invalid medication code
        try {

            new DischargeSummary(
                    "MT2026-0142",
                    new String[]{
                            "MED-A",
                            "bad"
                    });

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected");
        }

        // Valid discharge summary
        DischargeSummary d =
                new DischargeSummary(
                        "MT2026-0142",
                        new String[]{
                                "MED-A",
                                "MED-B"
                        });

        // Test defensive copy
        String[] codes =
                d.getMedicationCodes();

        codes[0] = "TAMPERED";

        System.out.println(
                "Original first code: "
                + d.getMedicationCodes()[0]);

        // Test with-style method
        DischargeSummary corrected =
                d.withCorrectedMedication(
                        0,
                        "MED-X");

        System.out.println(
                "Corrected first code: "
                + corrected.getMedicationCodes()[0]);

        // Test nightly batch
        DischargeSummary[] batch = {

                new CriticalCareDischargeSummary(
                        "MT001",
                        new String[]{"MED-X"},
                        4),

                null,

                new DischargeSummary(
                        "MT002",
                        new String[]{"MED-Y"})
        };

        System.out.println(
                processNightlyBatch(batch));
    }
}


// Critical-care variant
class CriticalCareDischargeSummary
        extends DischargeSummary {

    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);

        this.icuDays = icuDays;
    }

    public int getIcuDays() {

        return icuDays;
    }
}