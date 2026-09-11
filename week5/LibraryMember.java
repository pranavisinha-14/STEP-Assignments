package week5;

public class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash;

    // No-argument constructor
    public LibraryMember() {
        this(null, null);
    }

    // Name-only constructor
    public LibraryMember(String name) {
        this(null, name);
    }

    // ID + name constructor
    public LibraryMember(String membershipId,
                         String name) {

        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    // JavaBean getter
    public String getMembershipId() {
        return membershipId;
    }

    // Write-once setter
    public void setMembershipId(String id) {

        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    // JavaBean boolean getter
    public boolean isPremiumMember() {
        return premiumMember;
    }

    // JavaBean setter
    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Name getter
    public String getName() {
        return name;
    }

    // Name setter
    public void setName(String name) {
        this.name = name;
    }

    // Write-only security answer
    public void setSecurityAnswer(String answer) {

        if (answer == null) {
            securityAnswerHash = null;
        } else {
            securityAnswerHash =
                    Integer.toHexString(answer.hashCode());
        }
    }

    public static void main(String[] args) {

        LibraryMember member1 =
                new LibraryMember("Priya Nair");

        System.out.println(
                "Name-only ID: "
                + member1.getMembershipId());

        LibraryMember member2 =
                new LibraryMember(
                        "LIB-8841",
                        "Priya Nair");

        System.out.println(
                "ID: "
                + member2.getMembershipId());

        LibraryMember member3 =
                new LibraryMember();

        member3.setMembershipId("LIB-8841");
        member3.setMembershipId("FAKE-0000");

        System.out.println(
                "Write-once ID: "
                + member3.getMembershipId());

        member3.setPremiumMember(true);

        System.out.println(
                "Premium member: "
                + member3.isPremiumMember());

        member3.setSecurityAnswer("blue");

        System.out.println(
                "Security answer has been stored.");
    }
}
