package week7;

public class ArenaBattleSimulator {

    // Attack interface
    interface Attackable {

        String attack();

        String attack(String weaponName);
    }

    // Defense interface
    interface Defendable {

        String defend();
    }

    // Abstract character
    static abstract class GameCharacter {

        private static int characterCounter = 1000;

        private final String characterId;

        public GameCharacter() {

            characterCounter++;

            characterId =
                    "GC-" + characterCounter;
        }

        public abstract String getSpecialMove();

        public String getCharacterId() {

            return characterId;
        }
    }

    /*
     * Warrior:
     * IS-A GameCharacter
     * CAN-DO Attackable
     * CAN-DO Defendable
     */
    static class Warrior
            extends GameCharacter
            implements Attackable, Defendable {

        private String name;

        public Warrior(
                String name) {

            if (name == null
                    || name.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Name cannot be blank");
            }

            this.name =
                    name.trim();
        }

        @Override
        public String attack() {

            return name
                    + " strikes with a blade";
        }

        /*
         * Overloaded attack method.
         */
        @Override
        public String attack(
                String weaponName) {

            return name
                    + " strikes with an "
                    + weaponName;
        }

        @Override
        public String defend() {

            return name
                    + " raises a shield";
        }

        @Override
        public String getSpecialMove() {

            return name
                    + " unleashes Whirlwind Slash";
        }
    }

    /*
     * Trap has NO relationship with
     * GameCharacter.
     *
     * It only implements Defendable.
     */
    static class Trap
            implements Defendable {

        private String trapType;

        public Trap(
                String trapType) {

            if (trapType == null
                    || trapType.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Trap type cannot be blank");
            }

            this.trapType =
                    trapType.trim();
        }

        @Override
        public String defend() {

            return trapType
                    + " triggers automatically";
        }
    }

    /*
     * Interface polymorphism.
     *
     * Both Warrior and Trap can be stored
     * because both implement Defendable.
     */
    static void resolveDefense(
            Defendable[] combatants) {

        for (Defendable combatant
                : combatants) {

            System.out.println(
                    combatant.defend());
        }
    }

    public static void main(String[] args) {

        Warrior w =
                new Warrior("Kael");

        System.out.println(
                w.attack());

        System.out.println(
                w.attack("Iron Sword"));

        System.out.println(
                w.defend());

        System.out.println(
                w.getSpecialMove());

        System.out.println(
                "Character ID: "
                + w.getCharacterId());

        System.out.println();

        Trap t =
                new Trap("Spike Pit");

        System.out.println(
                t.defend());

        System.out.println();

        resolveDefense(
                new Defendable[] {
                    w,
                    t
                });
    }
}
