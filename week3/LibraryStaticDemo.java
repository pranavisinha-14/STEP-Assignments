package week3;

class LibraryMember {

    // Broken version:
    // static fields are shared by every object.
    static String brokenName;
    static String brokenMemberId;
    static int brokenBooksIssued;

    // Correct design
    String name;
    String memberId;
    int booksIssued;

    static String libraryName = "Central Library";
    static int memberCount = 0;

    LibraryMember(String name) {

        this.name = name;

        memberCount++;

        this.memberId =
            "LM-" + (1000 + memberCount);
    }

    void printMemberCard() {

        System.out.println(
            name + " | " + memberId
        );
    }

    static void printTotalMembers() {

        System.out.println(
            "Total members: " + memberCount
        );
    }
}

public class LibraryStaticDemo {

    public static void main(String[] args) {

        System.out.println("Broken version:");

        LibraryMember.brokenName = "Aditi";
        LibraryMember.brokenMemberId = "LM-1001";

        LibraryMember.brokenName = "Rohan";
        LibraryMember.brokenMemberId = "LM-1002";

        System.out.println(LibraryMember.brokenName);
        System.out.println(LibraryMember.brokenName);

        System.out.println("\nFixed version:");

        LibraryMember a =
            new LibraryMember("Aditi");

        LibraryMember r =
            new LibraryMember("Rohan");

        a.printMemberCard();
        r.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}
