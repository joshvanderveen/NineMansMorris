package view;

import model.Mill;
import model.MillManager;
import model.board.Coordinate;
import model.board.GameBoard;
import model.board.Intersection;
import model.board.Piece;
import controller.PieceListener;
import view.piece.UIBoardPosition;
import view.piece.UIBoardPositionEmpty;
import view.piece.UIBoardPositionFull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class UIBoardPanel extends JPanel implements MouseListener {

    private static final Color BOARD_COLOR = Color.WHITE;
    private static final Color NODE_COLOR = Color.BLACK;
    private static final int NODE_SIZE = 10;
    public static final int GAP_SIZE = 100;
    public static final int X_OFFSET = 80;
    public static final int Y_OFFSET = 80;

    private PieceListener pieceListener;

    private Intersection selectedIntersection;

    private GameBoard gameBoard = null;

    public UIBoardPanel() {
        super();
        addMouseListener(this);
    }

    /**
     * Calls the repaint method of the JPanel when the board needs to be redrawn
     *
     * @see javax.swing.JPanel#repaint()
     */
    public void redraw() {
       repaint();
    }

    /**
     * Sets the selectedIntersection to the intersection that was clicked on
     * @param intersection the intersection that was selected
     *
     * @see UIBoardPanel#selectedIntersection
     */
    public void setSelectedIntersection(Intersection intersection) {
        this.selectedIntersection = intersection;
    }

    /**
     * Sets the GameBoard that will be drawn and used
     * @param gameBoard the GameBoard that will be used
     *
     * @see model.board.GameBoard
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        redraw();
    }

    @Override
    public void paintComponent(Graphics _graphics) {

        if (gameBoard == null) return;

        super.paintComponent(_graphics);

        Graphics2D graphics = (Graphics2D) _graphics;

        setBackground(BOARD_COLOR);

        int numIntersections = gameBoard.getIntersectionsSize();

        graphics.setStroke(new BasicStroke(3));
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < numIntersections; i++) {
            for (int j = 0; j < numIntersections; j++) {
                Intersection intersection1 = gameBoard.getIntersection(i);
                Intersection intersection2 = gameBoard.getIntersection(j);

                if (intersection1.checkDirectlyConnected(intersection2)) {
                    int x1 = intersection1.getXCoordinate();
                    int y1 = intersection1.getYCoordinate();

                    int x2 = intersection2.getXCoordinate();
                    int y2 = intersection2.getYCoordinate();

                    graphics.drawLine((x1 * GAP_SIZE) + X_OFFSET, (y1 * GAP_SIZE) + Y_OFFSET, (x2 * GAP_SIZE) + X_OFFSET, (y2 * GAP_SIZE) + Y_OFFSET);
                }
            }
        }

        for (int i = 0; i < numIntersections; i++) {

            Intersection intersection = gameBoard.getIntersection(i);

            int intersectionXCoordinate = intersection.getXCoordinate();
            int intersectionYCoordinate = intersection.getYCoordinate();

            Piece piece = intersection.getPiece();

            UIBoardPosition position;

            if (piece != null) {
                position = new UIBoardPositionFull(intersectionXCoordinate * GAP_SIZE + X_OFFSET, intersectionYCoordinate * GAP_SIZE + Y_OFFSET, piece, intersection == selectedIntersection, MillManager.intersectionIsInUnusedMill(intersection));
            }
            else {
                position = new UIBoardPositionEmpty(intersectionXCoordinate * GAP_SIZE + X_OFFSET, intersectionYCoordinate * GAP_SIZE + Y_OFFSET);
            }

            position.draw(graphics);
        }

        int maxXCoordinate = 0;
        int maxYCoordinate = 0;

        for (int i = 0; i < numIntersections; i++) {
            Intersection intersection = gameBoard.getIntersection(i);
            int intersectionXCoordinate = intersection.getXCoordinate();
            int intersectionYCoordinate = intersection.getYCoordinate();

            if (intersectionXCoordinate > maxXCoordinate) {
                maxXCoordinate = intersectionXCoordinate;
            }
            if (intersectionYCoordinate > maxYCoordinate) {
                maxYCoordinate = intersectionYCoordinate;
            }
        }

        graphics.setFont(new Font("Arial", 1, 18));
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < maxXCoordinate + 1; i++) {
            graphics.drawString(String.valueOf(i + 1), i * GAP_SIZE + X_OFFSET, Y_OFFSET / 2);
        }

        for (int i = 0; i < maxYCoordinate + 1; i++) {
            graphics.drawString(String.valueOf(i + 1), X_OFFSET / 2 - NODE_SIZE, i * GAP_SIZE + Y_OFFSET);
        }
    }

    /**
     * Sets the PieceListener that will be used for listening to clicks on the board
     * @param pieceListener the PieceListener to use
     */
    public void addPieceListener(PieceListener pieceListener) {
        this.pieceListener = pieceListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Intersection sourceIntersection = getIntersection(e);

        if (sourceIntersection == null) return;

        pieceListener.positionSelected(sourceIntersection);
    }

    /**
     * Gets the intersection that was clicked on
     * @param e the MouseEvent that was created
     * @return the Intersection that was clicked on or null if no intersection was at the clicked location
     */
    private Intersection getIntersection(MouseEvent e) {

        Point movePoint = e.getPoint();
        movePoint.x = (movePoint.x - X_OFFSET) / GAP_SIZE;
        movePoint.y = (movePoint.y - Y_OFFSET) / GAP_SIZE;

        int circleX = movePoint.x * GAP_SIZE + X_OFFSET;
        int circleY = movePoint.y * GAP_SIZE + Y_OFFSET;
        int radius = 20;
        int buffer = 10;

        // Adjust the clicked point's coordinates to be within +/- radius from the center of the circle
        int clickedX = e.getX();
        int clickedY = e.getY();
        if (clickedX < circleX - radius - buffer) {
            movePoint.x--;
        } else if (clickedX > circleX + radius + buffer) {
            movePoint.x++;
        }
        if (clickedY < circleY - radius - buffer) {
            movePoint.y--;
        } else if (clickedY > circleY + radius + buffer) {
            movePoint.y++;
        }

        // Recalculate the center of the circle based on the adjusted movePoint
        circleX = movePoint.x * GAP_SIZE + X_OFFSET;
        circleY = movePoint.y * GAP_SIZE + Y_OFFSET;

        // Calculate the distance between the adjusted clicked point and the center of the circle
        double distance = Math.sqrt(Math.pow(clickedX - circleX, 2) + Math.pow(clickedY - circleY, 2));
        if (distance <= radius + buffer) {
            // the point is within the circle
            // get Intersection with these coordinates
            Coordinate moveCoordinate = new Coordinate(movePoint);
//            System.out.println("closest intersection coords: " + closestIntersection.getCoordinate());
            return this.gameBoard.getIntersection(moveCoordinate);
        }

        return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
