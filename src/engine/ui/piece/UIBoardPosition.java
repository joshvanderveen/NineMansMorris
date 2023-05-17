package engine.ui.piece;

import javax.swing.*;
import java.awt.*;

public abstract class UIBoardPosition extends JPanel {
    private int xPosition;
    private int yPosition;
    protected int radius = 5;

    protected Color selectedColor = Color.RED;

    public UIBoardPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }
    public UIBoardPosition(int x, int y, int radius) {
        this.xPosition = x;
        this.yPosition = y;
        this.radius = radius;
    }

    /**
     * Draws the piece on the board
     * @param graphics The graphics object to use to draw the piece
     */
    public void draw(Graphics2D graphics) {
        graphics.fillOval(xPosition - radius, yPosition - radius, radius * 2, radius * 2);
    }
}
