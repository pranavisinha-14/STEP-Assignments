package week3;

class Employee {

    private String empId;
    private String empName;
    private double salary;

    Employee(
        String empId,
        String empName,
        double salary
    ) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {

    private double teamBonus;

    ManagerEmployee(
        String empId,
        String empName,
        double salary,
        double teamBonus
    ) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {

    private double stipendCap;

    InternEmployee(
        String empId,
        String empName,
        double salary,
        double stipendCap
    ) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

class ParkingSlot {

    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(
        String slotNo,
        int capacity,
        int occupiedCount
    ) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    boolean allot() {

        if (occupiedCount < capacity) {
            occupiedCount++;
            return true;
        }

        return false;
    }
}

class CompanyEmployeeRecord {

    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(
        String name,
        String empId,
        Employee employee,
        ParkingSlot slot
    ) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;

        totalRecords++;
    }

    String fullProfile() {

        double pay;

        if (employee instanceof ManagerEmployee) {

            pay = ((ManagerEmployee) employee)
                    .effectiveSalary();

        } else if (employee instanceof InternEmployee) {

            pay = ((InternEmployee) employee)
                    .effectiveSalary();

        } else {

            pay = employee.getSalary();
        }

        String parking;

        if (slot == null) {
            parking = "no parking assigned";
        } else {
            parking = slot.slotNo;
        }

        return name +
            " | Pay: Rs " + pay +
            " | Slot: " + parking;
    }
}

public class HRParkingSystem {

    public static void main(String[] args) {

        ParkingSlot a1 =
            new ParkingSlot("A1", 1, 0);

        ParkingSlot a2 =
            new ParkingSlot("A2", 1, 0);

        ManagerEmployee manager =
            new ManagerEmployee(
                "E101", "Divya", 70000, 8000
            );

        Employee employee =
            new Employee(
                "E102", "Karan", 40000
            );

        InternEmployee intern =
            new InternEmployee(
                "E103", "Meera", 12000, 10000
            );

        a1.allot();
        a2.allot();

        CompanyEmployeeRecord r1 =
            new CompanyEmployeeRecord(
                "Divya", "E101", manager, a1
            );

        CompanyEmployeeRecord r2 =
            new CompanyEmployeeRecord(
                "Karan", "E102", employee, a2
            );

        CompanyEmployeeRecord r3 =
            new CompanyEmployeeRecord(
                "Meera", "E103", intern, null
            );

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());

        System.out.println(
            "Total records: " +
            CompanyEmployeeRecord.totalRecords
        );
    }
}