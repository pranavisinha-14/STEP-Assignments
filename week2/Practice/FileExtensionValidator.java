package week2.Practice;

public class FileExtensionValidator {

    static String validateFileExtension(String filename) {

        int dotPosition =
                filename.lastIndexOf('.');

        if (dotPosition == -1 ||
                dotPosition == filename.length() - 1) {

            return "Rejected — invalid file type";
        }

        String extension =
                filename.substring(dotPosition + 1);

        if (extension.equalsIgnoreCase("pdf")
                || extension.equalsIgnoreCase("docx")
                || extension.equalsIgnoreCase("zip")) {

            return "Accepted";
        }

        return "Rejected — invalid file type";
    }

    public static void main(String[] args) {

        System.out.println(
                validateFileExtension("Assignment1.PDF"));

        System.out.println(
                validateFileExtension("notes.txt"));
    }
}