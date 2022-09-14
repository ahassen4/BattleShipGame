import java.util.Arrays;

/**
 * This class represents one of the boat pieces
 * used in battleship. It is created by specifying
 * a starting position, length and direction.
 * The corresponding coordinates are then calculated.
 */
public class Boat {
    private final int startX;
    private final int startY;
    private final int len;
    private final Direction direction;

    private Point[] points;

    public Boat(int startX, int startY, int len, Direction direction) {
        this.startX = startX;
        this.startY = startY;
        this.len = len;
        this.direction = direction;
        generatePoints();
    }

    // Here are some test cases to make sure things are working correctly
    public static void main(String[] args) {
        // Example given above generatePoints function
        Boat boat1 = new Boat(1, 1, 4, Direction.SOUTH);
        // Should have the points 1,1 1,2 1,3 1,4
        System.out.println(boat1);
        // Should return true
        System.out.println(boat1.allPointsInBounds());

        // Now lets make an invalid boat
        Boat boat2 = new Boat(0, 0, 3, Direction.NORTH);
        // It should still be constructed and have the points 0,0 0,-1 0,-2
        System.out.println(boat2);
        // But this should return false
        System.out.println(boat2.allPointsInBounds());

        // Now lets make one on the edge
        Boat boat3 = new Boat(9, 9, 5, Direction.WEST);
        // Should have the points 9,9 8,9 7,9 6,9 5,9
        System.out.println(boat3);
        // Should be true
        System.out.println(boat3.allPointsInBounds());

        // Now lets test our overlapping function
        // Should return false
        System.out.println(boat1.overlapsWith(boat2));

        Boat boat4 = new Boat(1, 3, 4, Direction.NORTH);
        // Should have the points 1,3 1,2 1,1 1,0
        System.out.println(boat4);
        // Should return true
        System.out.println(boat1.overlapsWith(boat4));
        // Should return true
        System.out.println(boat4.overlapsWith(boat1));
    }

    // TODO: Implement the following function

    public Point[] getPoints() {
        return points;
    }

    // TODO: Implement the following function

    /**
     * This function fills in the points array with
     * the coordinates this ship will take up.
     * For example a ship that starts at 1,1 with
     * length 4 and direction south would occupy
     * the following coordinates:
     * 1,1 1,2 1,3 1,4
     * Hint use the dx and dy part of the Direction enum!
     */
    private void generatePoints() {
        // TO DO: FIN

        int y = startY;
        int x = startX;
        points = new Point[len];
        points[0] = new Point(x, y);
        for (int i = 1; i < len; i++) {
            x = x + direction.getDx();
            y = y + direction.getDy();
            points[i] = new Point(x, y);

        }

    }

    // TODO: Implement the following function

    /**
     * This function checks to see whether this boat
     * overlaps with another boat. It does this by checking
     * all of the points this boat occupies against
     * all of the points the other boat occupies.
     * If one of the points exists in both boats
     * points array then it returns true otherwise
     * it returns false.
     */
    public boolean overlapsWith(Boat boat) {
        // TO DO: FIN
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < boat.points.length; j++) {
                if (points[i].equals(boat.points[j])) {
                    return true;

                }

            }

        }
        return false;
    }

    /**
     * This function verifies that all the points in
     * the points array are in bounds. The bounds of
     * the board are [0,0] to [9,9]. The brackets mean
     * inclusive (includes the number in the range.
     * It should return true if all points satisfy
     * this requirement and false otherwise.
     */
    public boolean allPointsInBounds() {
        //TO DO: FIN
        for (Point point : points) {
            if (!(point.getX() >= 0 && point.getX() <= 9 && point.getY() >= 0 && point.getY() <= 9)) {
                return false;
            }

        }

        return true;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", len=" + len +
                ", direction=" + direction +
                ", points=" + Arrays.toString(points) +
                '}';
    }
}

