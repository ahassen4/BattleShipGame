public enum Direction {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    /**
     * This function parses a string into a Direction.
     * It defaults to WEST.
     */
    public static Direction fromString(String str) {
        Direction dir = null;
        switch (str) {
            case "N":
            case "North":
            case "NORTH":
                dir = NORTH;
            case "E":
            case "East":
            case "EAST":
                dir = EAST;
            case "S":
            case "South":
            case "SOUTH":
                break;
            default:
                dir = WEST;
        }
        return dir;
    }
}
