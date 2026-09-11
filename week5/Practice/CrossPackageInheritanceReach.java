package week5.Practice;

public class CrossPackageInheritanceReach {

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

                    if (accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE")) {

                        return "ALLOWED";
                    }

                    if (accessorContext.equals(
                            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {

                        return "ALLOWED";
                    }

                    return "DENIED";

                case "public":

                    return "ALLOWED";

                default:

                    return "DENIED";
            }
        }

        static String describeContext(String accessorContext) {

            String[] words =
                    accessorContext.split("_");

            StringBuilder result =
                    new StringBuilder();

            for (String word : words) {

                if (word.isEmpty()) {
                    continue;
                }

                if (result.length() > 0) {
                    result.append(" ");
                }

                result.append(
                        word.substring(0, 1).toUpperCase());

                result.append(
                        word.substring(1).toLowerCase());
            }

            return result.toString();
        }
    }

    public static void main(String[] args) {

        System.out.println(
                AccessRuleEngine.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println(
                AccessRuleEngine.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println(
                AccessRuleEngine.describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}