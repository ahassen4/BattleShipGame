import javax.swing.*;

/**
 * This class represents the laptop like board that
 * you play Battleship with. The PLAYER_BOARD represents
 * the area where you place your ships and keep track
 * of where the enemy has tried to hit. Whereas the
 * ENEMY_BOARD represents the top part of the "laptop"
 * which shows where you have tried to strike and what
 * the result was.
 */
public class Board {
    private final BoardSpace[][] PLAYER_BOARD;
    private final BoardSpace[][] ENEMY_BOARD;

    private final Boat[] BOATS;
    private final int MAX_BOATS = 5;
    private int numBoats = 0;

    // This is used to create the enemies view of the board
    public Board(int spaceSize) {
        PLAYER_BOARD = createBoard(spaceSize);
        ENEMY_BOARD = createBoard(spaceSize);
        BOATS = new Boat[MAX_BOATS];
    }

    // This is used to create the players view of the board
    public Board(JPanel playerPanel, JPanel enemyPanel, int spaceSize) {
        PLAYER_BOARD = createBoard(spaceSize);
        addSpacesToPanel(playerPanel, PLAYER_BOARD);

        ENEMY_BOARD = createBoard(spaceSize);
        addSpacesToPanel(enemyPanel, ENEMY_BOARD);

        BOATS = new Boat[MAX_BOATS];
    }

    public BoardSpace[][] getPLAYER_BOARD() {
        return PLAYER_BOARD;
    }

    public BoardSpace[][] getENEMY_BOARD() {
        return ENEMY_BOARD;
    }

    // TODO: Implement the following function

    /**
     * This function creates a 10 by 10 2d array of BoardSpaces.
     * Each space will have the NEITHER status, will not
     * contain a boat and will have the given size.
     */
    private BoardSpace[][] createBoard(int spaceSize) {
        BoardSpace[][] board = new BoardSpace[10][10];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                //TO DO: FIN
                board[i][j]= new BoardSpace (BoardSpace.Status.NEITHER,false,spaceSize);

            }
        }

        return board;
    }

    // TODO: Implement the following function

    /**
     * This function checks the players board to see if all there
     * ships have been sunk. In other words if every BoardSpace
     * that contains a boat also has a status of HIT then the player
     * is defeated.
     */
    public boolean isDefeated() {
        //TO DO: FIN
        for (int i = 0; i < PLAYER_BOARD.length; i++) {
            for (int j = 0; j < ENEMY_BOARD.length; j++)
                if (PLAYER_BOARD[i][j].containsBoat()) {
                    if (PLAYER_BOARD[i][j].getStatus() != BoardSpace.Status.HIT) {
                        return false;
                    }
                }
        }
        return true;

    }

    // TODO: Implement the following function

    /*
        This function checks to see whether or not we can
        add another boat. This is determined by whether or
        not the numBoats is less than the MAX_BOATS.
     */
    public boolean canAddBoat() {
        // TO DO: FIN

        return numBoats < MAX_BOATS;


    }

    /**
     * This function simply add the given 2d BoardSpaces array
     * into the given panel row by row. This needs to be separate
     * from the createBoard function since the enemy AI does not
     * need to show anything to the screen.
     */
    private void addSpacesToPanel(JPanel panel, BoardSpace[][] spaces) {
        for (BoardSpace[] row : spaces) {
            for (BoardSpace space : row) {
                panel.add(space);
            }
        }
    }

    /**
     * This function handles all of the logic for attempting a strike
     * against an opponent. The tricky part here is that parity must
     * be maintained between your view of the enemies board and the
     * enemies board.
     */
    public void attemptStrike(Point point, Board against) {
        // The actual space on the enemies board
        BoardSpace enemySpace = against.PLAYER_BOARD[point.getY()][point.getX()];
        // Your view of the board
        BoardSpace playerEnemySpace = ENEMY_BOARD[point.getY()][point.getX()];

        // Keep parity between the two spaces
        if (enemySpace.containsBoat()) {
            enemySpace.setStatus(BoardSpace.Status.HIT);
            playerEnemySpace.setStatus(BoardSpace.Status.HIT);
        }
        else {
            enemySpace.setStatus(BoardSpace.Status.MISS);
            playerEnemySpace.setStatus(BoardSpace.Status.MISS);
        }
    }

    /*
       First checks to make sure that the given boat does not overlap
       with any other boats. If it does then the function returns false.
       If it doesn't then it adds the given boat to the array and
       then increments the numBoats variable by 1. Then adds the boat to
       the BoardSpaces and returns true.
     */
    public boolean addBoat(Boat boat) {
        // First check to make sure we can add another boat
        // And that it is in bounds
        if (canAddBoat() && boat.allPointsInBounds()) {
            // Make sure we are not overlapping with other boats
            for (Boat other : BOATS) {
                if (other != null && boat.overlapsWith(other)) {
                    return false;
                }
            }

            // Save the boat into the array and increment numBoats
            BOATS[numBoats] = boat;
            numBoats += 1;

            for (Point point : boat.getPoints()) {
                BoardSpace space = PLAYER_BOARD[point.getY()][point.getX()];
                space.setContainsBoat(true);
            }

            return true;
        }
        else {
            return false;
        }
    }
}
