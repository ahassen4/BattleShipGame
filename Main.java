import javax.swing.*;
import java.awt.*;

/**
 * This class takes care of all the GUI elements
 */
public class Main {
    public static void main(String[] args) {
        // Create the frame which acts as the outer shell for the components on the screen
        JFrame frame = new JFrame("Battleship!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
            Now we need to create a panel to hold all of our components.
            This includes the boards as well as the button to control the game.
         */
        JPanel wholeScreen = new JPanel();
        wholeScreen.setLayout(new BorderLayout(10, 10));
        frame.add(wholeScreen);

        placeComponents(frame, wholeScreen);

        // Make sure the window is sized correctly
        frame.pack();
        // Don't forget to show the frame!
        frame.setVisible(true);
    }

    /**
     * This function creates all of the components that go onto the screen.
     * This includes the boards for both the player and the enemy as well
     * as the button necessary to play the game.
     */
    private static void placeComponents(JFrame frame, JPanel wholeScreen) {
        // First lets tell the player how to play
        JTextArea instructions = new JTextArea("Place your ships by entering the required information below the board. " +
                "Then clicking on the \"Add Ship\" button. " +
                "When you are done click on the \"GO!\" button.");
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        float fontSize = 20;
        instructions.setFont(instructions.getFont().deriveFont(fontSize));
        wholeScreen.add(instructions, BorderLayout.PAGE_START);

        /*
            Now we need to create a panel to hold both the player and enemies
            boards. The players board shows where they have placed their
            ships and what shots the enemy has taken. Whereas the enemy board
            shows the shots the player has taken.
         */
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.PAGE_AXIS));
        wholeScreen.add(boardPanel, BorderLayout.CENTER);

        // Now we need to create a panel to show the enemies board on the screen
        JPanel enemyPanel = new JPanel();
        // Set the layout manager to be a grid layout.
        enemyPanel.setLayout(new GridLayout(10, 10));
        // Now add the panel to the frame
        boardPanel.add(enemyPanel);

        // Add a line in between the two boards
        JPanel separator = new JPanel();
        boardPanel.add(separator);

        // Now we need to create a panel to show the players board on the screen
        JPanel playerPanel = new JPanel();
        // Set the layout manager to be a grid layout.
        playerPanel.setLayout(new GridLayout(10, 10));
        // Now add the panel to the frame
        boardPanel.add(playerPanel);

        // Now we need to create our initialization components
        JPanel initializeControls = new JPanel();
        initializeControls.setLayout(new GridLayout(0, 2));
        wholeScreen.add(initializeControls, BorderLayout.PAGE_END);

        // Controls the bounds of the JSpinner
        SpinnerNumberModel xCoordModel =
                new SpinnerNumberModel(0, 0, 9, 1);

        JLabel shipXLabel = new JLabel("Ship's X:");
        JSpinner shipX = new JSpinner(xCoordModel);
        initializeControls.add(shipXLabel);
        initializeControls.add(shipX);

        SpinnerNumberModel yCoordModel =
                new SpinnerNumberModel(0, 0, 9, 1);

        JLabel shipYLabel = new JLabel("Ship's Y:");
        JSpinner shipY = new JSpinner(yCoordModel);
        initializeControls.add(shipYLabel);
        initializeControls.add(shipY);

        SpinnerNumberModel lenModel =
                new SpinnerNumberModel(2, 2, 5, 1);

        JLabel shipLenLabel = new JLabel("Ship's Length:");
        JSpinner shipLen = new JSpinner(lenModel);
        initializeControls.add(shipLenLabel);
        initializeControls.add(shipLen);

        JLabel shipDirLabel = new JLabel("Ship's Direction:");
        JComboBox<Direction> shipDir = new JComboBox<>(Direction.values());
        initializeControls.add(shipDirLabel);
        initializeControls.add(shipDir);

        JButton addButton = new JButton("Add Ship");
        initializeControls.add(addButton);

        JLabel error = new JLabel();
        error.setForeground(Color.RED);
        initializeControls.add(error);

        SpinnerNumberModel enemyShipModel =
                new SpinnerNumberModel(5, 1, 5, 1);

        JLabel enemyShipsLabel = new JLabel("Num Enemy Ships:");
        JSpinner enemyShips = new JSpinner(enemyShipModel);
        initializeControls.add(enemyShipsLabel);
        initializeControls.add(enemyShips);

        JLabel oracleModeLabel = new JLabel("Oracle Mode?");
        JCheckBox oracleMode = new JCheckBox();
        oracleMode.setSelected(false);
        initializeControls.add(oracleModeLabel);
        initializeControls.add(oracleMode);

        JButton goButton = new JButton("GO!");
        initializeControls.add(goButton);

        // Now we need to create a panel for our gameplay controls
        JPanel gameplayControls = new JPanel();
        gameplayControls.setLayout(new GridLayout(0, 2));

        JLabel xStrikeLabel = new JLabel("Strike At X:");
        JSpinner xStrike = new JSpinner(xCoordModel);
        gameplayControls.add(xStrikeLabel);
        gameplayControls.add(xStrike);

        JLabel yStrikeLabel = new JLabel("Strike At Y:");
        JSpinner yStrike = new JSpinner(yCoordModel);
        gameplayControls.add(yStrikeLabel);
        gameplayControls.add(yStrike);

        JButton submitButton = new JButton("Submit");
        gameplayControls.add(submitButton);

        // Now create winning/losing labels
        JLabel winner = new JLabel("YOU WIN!");
        JLabel loser = new JLabel("You Lose :'(");

        int spaceSize = 40;
        Board playerBoard = new Board(playerPanel, enemyPanel, spaceSize);
        Board enemyBoard = new Board(spaceSize);
        Enemy enemy = new Enemy(enemyBoard);

        submitButton.addActionListener(e -> {
            int x = (int) xStrike.getValue();
            int y = (int) yStrike.getValue();

            Point point = new Point(x, y);
            playerBoard.attemptStrike(point, enemyBoard);
            if (enemyBoard.isDefeated()) {
                gameplayControls.setVisible(false);
                wholeScreen.add(winner, BorderLayout.PAGE_END);
            }

            enemyBoard.attemptStrike(enemy.getShot(), playerBoard);
            if (playerBoard.isDefeated()) {
                gameplayControls.setVisible(false);
                wholeScreen.add(loser, BorderLayout.PAGE_END);
            }
        });

        addButton.addActionListener(e -> {
            int x = (int) shipX.getValue();
            int y = (int) shipY.getValue();
            int len = (int) shipLen.getValue();
            Direction dir = (Direction) shipDir.getSelectedItem();

            Boat boat = new Boat(x, y, len, dir);
            boolean result = playerBoard.addBoat(boat);

            if (!result) {
                error.setText("Invalid Boat Placement!");
            } else {
                error.setText("");
            }
        });

        goButton.addActionListener(e -> {
            initializeControls.setVisible(false);
            instructions.setText("Choose the x and y of where you want to strike then click \"Submit\".");

            enemy.addBoats((int) enemyShips.getValue());

            if (oracleMode.isSelected()) {
                for (int i = 0; i < enemyBoard.getPLAYER_BOARD().length; i++) {
                    for (int j = 0; j < enemyBoard.getPLAYER_BOARD()[i].length; j++) {
                        BoardSpace space = enemyBoard.getPLAYER_BOARD()[i][j];
                        if (space.containsBoat()) {
                            BoardSpace myEnemySpace = playerBoard.getENEMY_BOARD()[i][j];
                            myEnemySpace.setStatus(BoardSpace.Status.REVEALED);
                        }
                    }
                }
            }

            wholeScreen.add(gameplayControls, BorderLayout.PAGE_END);
        });
    }
}

