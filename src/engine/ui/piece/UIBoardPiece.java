package engine.ui.piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

abstract class UIBoardPiece extends JPanel {
    private int xPosition;
    private int yPosition;
    private int radius = 10;

    public UIBoardPiece(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    protected void draw(Graphics2D graphics) {
        graphics.fillOval(xPosition - radius / 2, yPosition - radius / 2, radius, radius);
    }
}
