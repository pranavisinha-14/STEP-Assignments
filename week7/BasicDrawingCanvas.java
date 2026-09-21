package week7;

public class BasicDrawingCanvas {

    // Abstract parent class
    static abstract class Shape {

        private static int shapeCounter = 1000;

        private final String shapeId;

        public Shape() {

            shapeCounter++;

            shapeId = "SH-" + shapeCounter;
        }

        // Abstract method
        public abstract double calculateArea();

        // One-argument scale
        public abstract void scale(double factor);

        // Two-argument overloaded scale
        public abstract void scale(
                double xFactor,
                double yFactor);

        public String getShapeId() {

            return shapeId;
        }
    }

    // Circle
    static class CircleShape extends Shape {

        private double radius;

        public CircleShape(double radius) {

            if (radius <= 0) {

                throw new IllegalArgumentException(
                        "Radius must be positive");
            }

            this.radius = radius;
        }

        @Override
        public double calculateArea() {

            return Math.PI * radius * radius;
        }

        @Override
        public void scale(double factor) {

            if (factor <= 0) {

                throw new IllegalArgumentException(
                        "Scale factor must be positive");
            }

            radius *= factor;
        }

        @Override
        public void scale(
                double xFactor,
                double yFactor) {

            /*
             * Reuse the one-argument method twice.
             */
            scale(xFactor);
            scale(yFactor);
        }
    }

    // Square
    static class SquareShape extends Shape {

        private double side;

        public SquareShape(double side) {

            if (side <= 0) {

                throw new IllegalArgumentException(
                        "Side must be positive");
            }

            this.side = side;
        }

        @Override
        public double calculateArea() {

            return side * side;
        }

        @Override
        public void scale(double factor) {

            if (factor <= 0) {

                throw new IllegalArgumentException(
                        "Scale factor must be positive");
            }

            side *= factor;
        }

        @Override
        public void scale(
                double xFactor,
                double yFactor) {

            scale(xFactor);
            scale(yFactor);
        }
    }

    /*
     * Polymorphic method.
     *
     * No checking of the actual subclass.
     */
    static void printArea(Shape s) {

        System.out.println(
                "Shape ID: "
                + s.getShapeId());

        System.out.println(
                "Area: "
                + s.calculateArea());
    }

    public static void main(String[] args) {

        CircleShape c =
                new CircleShape(5.0);

        System.out.println(
                "Circle area: "
                + c.calculateArea());

        SquareShape sq =
                new SquareShape(4.0);

        System.out.println(
                "Square area: "
                + sq.calculateArea());

        // One-argument overload
        sq.scale(2.0);

        System.out.println(
                "Square area after scale: "
                + sq.calculateArea());

        // Two-argument overload
        sq.scale(1.5, 2.0);

        System.out.println(
                "Square area after two-factor scale: "
                + sq.calculateArea());

        System.out.println();

        printArea(c);

        /*
         * This will NOT compile:
         *
         * Shape s = new Shape();
         *
         * Shape is abstract and cannot be
         * instantiated directly.
         */
    }
}