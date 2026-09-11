package week3.Practice;

public class HostelRoomNullSafety {

    static class HostelRoom {

        String roomNo;
        int beds;
        int occupied;

        HostelRoom(
                String roomNo,
                int beds,
                int occupied) {

            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        void allot(String name) {

            if (occupied < beds) {

                occupied++;

                System.out.println(
                        name
                        + " allotted to room "
                        + roomNo);

            } else {

                System.out.println(
                        "Room " + roomNo
                        + " is full.");
            }
        }
    }

    static HostelRoom findAvailableRoom(
            HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {

            if (room != null &&
                    room.occupied < room.beds) {

                return room;
            }
        }

        return null;
    }

    static void safeAllot(
            HostelRoom[] rooms,
            String studentName) {

        HostelRoom room =
                findAvailableRoom(rooms);

        if (room == null) {

            System.out.println(
                    "No rooms available for "
                    + studentName);

        } else {

            room.allot(studentName);
        }
    }

    public static void main(String[] args) {

        HostelRoom[] rooms = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };

        safeAllot(rooms, "Divya");

        /*
         * Java passes the array reference by value.
         * The array is not copied, and the HostelRoom
         * objects inside it are not copied either.
         * Therefore changes to a room are visible through
         * the original array.
         */

        System.out.println();

        safeAllot(rooms, "Divya");
    }
}