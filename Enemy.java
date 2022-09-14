import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class implement the most basic AI for battleship.
 * Namely it places its boats randomly and chooses places
 * to shoot at randomly. You can change how many boats
 * it generates up to the maximum allowed by the board.
 */
public class Enemy {
    private final List<Point> POINTS_SHOT_AT;
    private final Board BOARD;

    public Enemy(Board board) {
        this.POINTS_SHOT_AT = new ArrayList<>();
        this.BOARD = board;
    }

    /**
     * Create a completely random boat
     */
    private Boat getRandomBoat() {
        Random randCoords = new Random();

        int x = randCoords.nextInt(10);
        int y = randCoords.nextInt(10);

        // Get random length between [2,5]
        int len = randCoords.nextInt(4) + 2;

        int randDir = randCoords.nextInt(Direction.values().length);
        Direction dir = Direction.values()[randDir];

        return new Boat(x, y, len, dir);
    }

    /**
     * Add the numBoats to the BOARD.
     * Each boat is randomly created.
     * Then the boat tries to be put on the
     * BOARD and if it does not fit or goes out
     * of bounds then a new random boat is created
     * and the process repeats.
     * This is repeated until the numBoats have been
     * successfully added to the BOARD.
     * @param numBoats
     */
    public void addBoats(int numBoats) {
        for (int i = 0; i < numBoats; i++) {
            Boat boat = getRandomBoat();
            while (!BOARD.addBoat(boat)) {
                boat = getRandomBoat();
            }
        }
    }

    /**
     * Creates a completely random shot to take at the player
     */
    private Point getRandomShot() {
        Random randCoords = new Random();
        int x = randCoords.nextInt(10);
        int y = randCoords.nextInt(10);

        return new Point(x, y);
    }

    /**
     * Creates a random shot using getRandomShot().
     * This shot is then made sure to have not been
     * tried before if it hasn't then it is returned.
     * Otherwise a new shot is created and the process
     * is repeated.
     */
    public Point getShot() {
        Point point = getRandomShot();

        while (POINTS_SHOT_AT.contains(point)) {
            point = getRandomShot();
        }

        POINTS_SHOT_AT.add(point);
        return point;
    }
}

