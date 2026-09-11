package week5;

public class ReferenceDeskSubclassReach {

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

                    if (accessorContext.equals(
                            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                        return "ALLOWED";
                    }

                    if (accessorContext.equals(
                            "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")) {
                        return "DENIED";
                    }

                    return "DENIED";

                case "public":
                    return "ALLOWED";

                default:
                    return "DENIED";
            }
        }

        static String describeContext(String accessorContext) {

            String[] words = accessorContext.split("_");

            StringBuilder result = new StringBuilder();

            for (String word : words) {

                if (word.isEmpty()) {
                    continue;
                }

                if (result.length() > 0) {
                    result.append(" ");
                }

                result.append(
                        word.substring(0, 1).toUpperCase())
                      .append(
                        word.substring(1).toLowerCase());
            }

            return result.toString();
        }
    }

    public static void main(String[] args) {

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println(
                AccessChecker.describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}