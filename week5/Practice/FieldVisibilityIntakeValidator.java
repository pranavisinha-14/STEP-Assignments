package week5.Practice;

import java.util.LinkedHashMap;
import java.util.Map;

public class FieldVisibilityIntakeValidator {

    static class AccessRuleEngine {

        static String classifyAccess(String fieldModifier,
                                     String accessorContext) {

            switch (fieldModifier) {

                case "private":
                    return accessorContext.equals("SAME_CLASS")
                            ? "ALLOWED"
                            : "DENIED";

                case "default":
                    return accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE")
                            ? "ALLOWED"
                            : "DENIED";

                case "protected":
                    return accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE")
                            ? "ALLOWED"
                            : "DENIED";

                case "public":
                    return "ALLOWED";

                default:
                    return "DENIED";
            }
        }

        static String summarizeBatch(String[][] attempts) {

            int allowed = 0;
            int denied = 0;

            for (String[] attempt : attempts) {

                String result = classifyAccess(
                        attempt[0],
                        attempt[1]);

                if (result.equals("ALLOWED")) {
                    allowed++;
                } else {
                    denied++;
                }
            }

            return "Allowed: " + allowed
                    + " | Denied: " + denied;
        }
    }

    static class PatientRecord {

        private String patientId;
        String wardCode;
        protected double vitalsScore;
        public String facilityName;

        public PatientRecord(String patientId,
                             String wardCode,
                             double vitalsScore,
                             String facilityName) {

            if (patientId == null
                    || patientId.trim().length() < 4) {

                throw new IllegalArgumentException(
                        "construction rejected");
            }

            this.patientId = patientId.trim();
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }
    }

    public static void main(String[] args) {

        System.out.println(
                AccessRuleEngine.classifyAccess(
                        "private",
                        "SAME_CLASS"));

        System.out.println(
                AccessRuleEngine.classifyAccess(
                        "default",
                        "DIFFERENT_PACKAGE"));

        String[][] attempts = {
                {"protected", "SAME_PACKAGE"},
                {"protected", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                AccessRuleEngine.summarizeBatch(attempts));

        try {

            new PatientRecord(
                    "MT9",
                    "W3",
                    98.2,
                    "MediTrack Central");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected");
        }

        PatientRecord validRecord =
                new PatientRecord(
                        "MT94",
                        "W3",
                        98.2,
                        "MediTrack Central");

        System.out.println(
                "Valid patient record created: "
                + validRecord.patientId);
    }
}