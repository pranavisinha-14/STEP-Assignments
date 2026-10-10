package week8;

import java.util.*;

public class ElectiveSeatRush {
    interface CreditPolicy {
        int getLimit();
    }

    static class RegularPolicy implements CreditPolicy {
        public int getLimit() {
            return 24;
        }
    }

    static class HonorsPolicy implements CreditPolicy {
        public int getLimit() {
            return 28;
        }
    }

    static class ExchangePolicy implements CreditPolicy {
        public int getLimit() {
            return 20;
        }
    }

    static class Student {
        String name, type;
        int credits;
        CreditPolicy policy;
        Set<String> enrolled = new HashSet<>();
        Set<String> waiting = new HashSet<>();

        Student(String name, String type, int credits,
                CreditPolicy policy) {
            this.name = name;
            this.type = type;
            this.credits = credits;
            this.policy = policy;
        }

        boolean canEnroll(int courseCredits) {
            return credits + courseCredits <= policy.getLimit();
        }
    }

    static class Elective {
        String name;
        int credits, capacity;
        List<Student> enrolled = new ArrayList<>();
        Queue<Student> waitlist = new LinkedList<>();

        Elective(String name, int credits, int capacity) {
            this.name = name;
            this.credits = credits;
            this.capacity = capacity;
        }
    }

    static class EnrollmentService {
        void enroll(Student s, Elective e) {
            if (s.enrolled.contains(e.name)
                    || s.waiting.contains(e.name)) {
                System.out.println("Enrollment failed: " + s.name
                        + " already enrolled or waitlisted.");
                return;
            }

            if (!s.canEnroll(e.credits)) {
                System.out.println("Enrollment failed: " + s.name
                        + " would exceed the " + s.type
                        + " credit limit (" + (s.credits + e.credits)
                        + "/" + s.policy.getLimit() + ").");
                return;
            }

            if (e.enrolled.size() < e.capacity) {
                addStudent(s, e);
            } else {
                e.waitlist.add(s);
                s.waiting.add(e.name);

                System.out.println(e.name + " is full.");
                System.out.println(s.name
                        + " added to waitlist (position "
                        + e.waitlist.size() + ").");
            }
        }

        void addStudent(Student s, Elective e) {
            e.enrolled.add(s);
            s.enrolled.add(e.name);
            s.waiting.remove(e.name);
            s.credits += e.credits;

            System.out.println(s.name + " enrolled in " + e.name
                    + " (credits: " + s.credits + "/"
                    + s.policy.getLimit() + ").");
        }

        void drop(Student s, Elective e) {
            if (!e.enrolled.remove(s)) {
                System.out.println("Drop failed: " + s.name
                        + " is not enrolled.");
                return;
            }

            s.enrolled.remove(e.name);
            s.credits -= e.credits;

            System.out.println(s.name + " dropped " + e.name
                    + " (credits: " + s.credits + "/"
                    + s.policy.getLimit() + ").");

            int size = e.waitlist.size();

            while (size-- > 0 && !e.waitlist.isEmpty()) {
                Student next = e.waitlist.poll();

                if (!next.canEnroll(e.credits)) {
                    next.waiting.remove(e.name);
                    System.out.println("Promotion skipped: "
                            + next.name + " would exceed the credit limit.");
                    continue;
                }

                addStudent(next, e);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);
        EnrollmentService service = new EnrollmentService();

        Student asha = new Student("Asha", "Regular", 20,
                new RegularPolicy());
        Student ravi = new Student("Ravi", "Honors", 22,
                new HonorsPolicy());
        Student neha = new Student("Neha", "Exchange", 12,
                new ExchangePolicy());
        Student kiran = new Student("Kiran", "Regular", 22,
                new RegularPolicy());

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);

        service.drop(asha, cloud);
    }
}