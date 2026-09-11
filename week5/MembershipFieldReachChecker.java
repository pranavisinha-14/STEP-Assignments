package week5;

import java.util.LinkedHashMap;
import java.util.Map;

public class MembershipFieldReachChecker {

    static class AccessChecker {

        static String classifyAccess(String fieldModifier,
                                     String accessorContext) {

            switch (fieldModifier) {

                case "private":
                    if (accessorContext.equals("SAME_CLASS")) {
                        return "ALLOWED";
                    }
                    return "DENIED";

                case "default":
                    if (accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE")) {
                        return "ALLOWED";
                    }
                    return "DENIED";

                case "protected":
                    if (accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE")) {
                        return "ALLOWED";
                    }
                    return "DENIED";

                case "public":
                    return "ALLOWED";

                default:
                    return "DENIED";
            }
        }

        static String summarizeByModifier(String[][] attempts) {

            Map<String, int[]> result = new LinkedHashMap<>();

            result.put("private", new int[]{0, 0});
            result.put("default", new int[]{0, 0});
            result.put("protected", new int[]{0, 0});
            result.put("public", new int[]{0, 0});

            for (String[] attempt : attempts) {

                String modifier = attempt[0];
                String context = attempt[1];

                String access =
                        classifyAccess(modifier, context);

                if (access.equals("ALLOWED")) {
                    result.get(modifier)[0]++;
                } else {
                    result.get(modifier)[1]++;
                }
            }

            StringBuilder output = new StringBuilder();

            for (Map.Entry<String, int[]> entry
                    : result.entrySet()) {

                if (output.length() > 0) {
                    output.append(" | ");
                }

                output.append(entry.getKey())
                      .append(": ")
                      .append(entry.getValue()[0])
                      .append(" allowed / ")
                      .append(entry.getValue()[1])
                      .append(" denied");
            }

            return output.toString();
        }
    }

    static class LibraryMember {

        private String membershipId;
        private String branchCode;
        private double finesOwed;
        private String displayName;

        public LibraryMember(String membershipId,
                             String branchCode,
                             double finesOwed,
                             String displayName) {

            if (membershipId == null
                    || membershipId.trim().length() < 4) {

                throw new IllegalArgumentException(
                        "Construction rejected");
            }

            this.membershipId = membershipId.trim();
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }
    }

    public static void main(String[] args) {

        System.out.println(
                AccessChecker.classifyAccess(
                        "private",
                        "SAME_CLASS"));

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                AccessChecker.summarizeByModifier(attempts));

        try {

            new LibraryMember(
                    "LB9",
                    "BR1",
                    0,
                    "Priya Nair");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Construction rejected");
        }

        new LibraryMember(
                "LB94",
                "BR1",
                0,
                "Priya Nair");

        System.out.println(
                "Valid member created successfully");
    }
}