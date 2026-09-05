package week3;

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

    void allot(String vehicleNo) {

        if (occupiedCount < capacity) {

            occupiedCount++;

            System.out.println(
                vehicleNo +
                " allotted to slot " +
                slotNo
            );
        }
    }
}

public class ParkingAllocation {

    static ParkingSlot findAvailableSlot(
        ParkingSlot[] slots
    ) {

        for (ParkingSlot slot : slots) {

            if (slot != null &&
                slot.occupiedCount < slot.capacity) {

                return slot;
            }
        }

        return null;
    }

    static void safeAllot(
        ParkingSlot[] slots,
        String vehicleNo
    ) {

        ParkingSlot slot =
            findAvailableSlot(slots);

        if (slot != null) {

            slot.allot(vehicleNo);

        } else {

            System.out.println(
                "No slots available for " +
                vehicleNo
            );
        }
    }

    public static void main(String[] args) {

        ParkingSlot[] slots = {

            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };

        safeAllot(slots, "TN09AB1234");

        safeAllot(slots, "TN09AB1234");

        // The array contains references to ParkingSlot objects,
        // so passing the array does not copy the objects themselves.
    }
}
