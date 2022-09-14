import javax.swing.*;
import java.awt.*;

public class BoardSpace extends JComponent {
    public enum Status { HIT, MISS, NEITHER, REVEALED }

    private Status status;
    private boolean containsBoat;

    private final int SPACE_SIZE;

    public BoardSpace(Status status, boolean containsBoat, int spaceSize) {
        this.status = status;
        this.containsBoat = containsBoat;
        this.SPACE_SIZE = spaceSize;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        repaint();
    }

    public boolean containsBoat() {
        return containsBoat;
    }

    public void setContainsBoat(boolean containsBoat) {
        this.containsBoat = containsBoat;
        repaint();
    }

    /**
     * We need to override this because otherwise the default layout manager
     * sets our size to 0 and no window will be shown.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SPACE_SIZE, SPACE_SIZE);
    }

    /**
     * This function is called by Swing as part of the process to repaint the screen.
     * I am representing each cell in the grid, its corresponding status and whether
     * or not it has a boat on it using colors.
     * If the space has been hit then the rectangle representing the space will be red.
     * If the space has been missed then the rectangle representing the space will be green.
     * If the space has neither been hit or missed then the rectangle representing the space will be black.
     * If the space has been revealed which is only used when the player wants to see where the bots
     * boats are then the space will cyan.
     * If the space has a boat on it then a gray circle will be shown within the space.
     * @param g the graphics object we can use to draw on the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        // Should always call our parents paintComponent method to preserve the paint chain
        super.paintComponent(g);

        // Change the color based on the status
        if (status == Status.HIT) {
            g.setColor(Color.RED);
        }
        else if (status == Status.MISS) {
            g.setColor(Color.GREEN);
        }
        else if (status == Status.NEITHER) {
            g.setColor(Color.BLACK);
        }
        else {
            g.setColor(Color.CYAN);
        }

        int rectSize = SPACE_SIZE - 2;

        // Draw the rectangle to represent the status.
        // Leaves some white on all sides to separate spaces
        g.drawRect(1, 1, rectSize, rectSize);
        g.fillRect(1, 1, rectSize, rectSize);

        // Now draw the oval for the boat if this space contains one
        if (containsBoat) {
            g.setColor(Color.GRAY);
            g.drawOval(rectSize / 4, rectSize / 4, rectSize / 2, rectSize / 2);
            g.fillOval(rectSize / 4, rectSize / 4, rectSize / 2, rectSize / 2);
        }
    }
}

