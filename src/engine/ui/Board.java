package engine.ui;

import engine.board.GameBoard;
import engine.board.Intersection;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private static final Color BOARD_COLOR = Color.WHITE;
    private static final Color NODE_COLOR = Color.BLACK;
    private static final int NODE_SIZE = 10;
    public static final int GAP_SIZE = 100;
    public static final int X_OFFSET = 30;
    public static final int Y_OFFSET = 30;

    GameBoard gameBoard = null;

    public Board(GameBoard gameBoard) {
        super();
        this.gameBoard = gameBoard;
        repaint();
    }

    public Board() {
        super();
    }

    public void redraw() {
       repaint();
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        redraw();
    }

    @Override
    public void paintComponent(Graphics _graphics) {

//        if (gameBoard != null) return;

        super.paintComponent(_graphics);

        Graphics2D graphics = (Graphics2D) _graphics;

        setBackground(BOARD_COLOR);

        graphics.setColor(NODE_COLOR);

        int numIntersections = gameBoard.getIntersectionsSize();

        for (int i = 0; i < numIntersections; i++) {

            Intersection intersection = gameBoard.getIntersection(i);

            int x = intersection.getxCoordinate();
            int y = intersection.getyCoordinate();

            int nodeSize = NODE_SIZE;

            if (intersection.getPiece() != null) {
                nodeSize = NODE_SIZE * 2;

            }

            graphics.fillOval(x * GAP_SIZE + X_OFFSET, y * GAP_SIZE + Y_OFFSET, NODE_SIZE, NODE_SIZE);
        }

        graphics.setStroke(new BasicStroke(3));

        for (int i = 0; i < numIntersections; i++) {
            for (int j = 0; j < numIntersections; j++) {
                Intersection intersection1 = gameBoard.getIntersection(i);
                Intersection intersection2 = gameBoard.getIntersection(j);

                if (intersection1.checkConnected(intersection2)) {
                    int x1 = intersection1.getxCoordinate();
                    int y1 = intersection1.getyCoordinate();

                    int x2 = intersection2.getxCoordinate();
                    int y2 = intersection2.getyCoordinate();

                    graphics.drawLine((x1 * GAP_SIZE) + X_OFFSET + NODE_SIZE / 2, (y1 * GAP_SIZE) + Y_OFFSET + NODE_SIZE / 2, (x2 * GAP_SIZE) + X_OFFSET + NODE_SIZE / 2, (y2 * GAP_SIZE) + Y_OFFSET + NODE_SIZE / 2);
                }
            }
        }
    }
}
