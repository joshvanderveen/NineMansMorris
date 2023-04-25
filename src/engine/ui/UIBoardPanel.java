package engine.ui;

import engine.board.Coordinate;
import engine.board.GameBoard;
import engine.board.Intersection;
import engine.board.Piece;
import engine.game.PieceListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIBoardPanel extends JPanel implements MouseListener {

    private static final Color BOARD_COLOR = Color.WHITE;
    private static final Color NODE_COLOR = Color.BLACK;
    private static final int NODE_SIZE = 10;
    public static final int GAP_SIZE = 100;
    public static final int X_OFFSET = 30;
    public static final int Y_OFFSET = 30;

    private PieceListener pieceListener;

    private Intersection selectedIntersection;

    private GameBoard gameBoard = null;

    public UIBoardPanel() {
        super();
        addMouseListener(this);
    }

    public void redraw() {
       repaint();
    }

    public void setSelectedIntersection(Intersection intersection) {
        this.selectedIntersection = intersection;
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

        int numIntersections = gameBoard.getIntersectionsSize();

        graphics.setStroke(new BasicStroke(3));
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < numIntersections; i++) {
            for (int j = 0; j < numIntersections; j++) {
                Intersection intersection1 = gameBoard.getIntersection(i);
                Intersection intersection2 = gameBoard.getIntersection(j);

                if (intersection1.checkConnected(intersection2)) {
                    int x1 = intersection1.getxCoordinate();
                    int y1 = intersection1.getyCoordinate();

                    int x2 = intersection2.getxCoordinate();
                    int y2 = intersection2.getyCoordinate();

                    graphics.drawLine((x1 * GAP_SIZE) + X_OFFSET, (y1 * GAP_SIZE) + Y_OFFSET, (x2 * GAP_SIZE) + X_OFFSET, (y2 * GAP_SIZE) + Y_OFFSET);
                }
            }
        }

        for (int i = 0; i < numIntersections; i++) {

            Intersection intersection = gameBoard.getIntersection(i);

            int x = intersection.getxCoordinate();
            int y = intersection.getyCoordinate();

            int nodeSize = NODE_SIZE;

            Piece piece = intersection.getPiece();

            if (intersection == selectedIntersection && intersection.getPiece() != null) {
                graphics.setColor(Color.RED);
                nodeSize = NODE_SIZE * 3;
            } else if (piece != null) {
                graphics.setColor(piece.getOwner().getPlayerColor());
                nodeSize = NODE_SIZE * 3;
            } else {
                graphics.setColor(Color.BLACK);
            }

            graphics.fillOval(x * GAP_SIZE + X_OFFSET - nodeSize / 2, y * GAP_SIZE + Y_OFFSET - nodeSize / 2, nodeSize, nodeSize);
        }

    }

    public void addPieceListener(PieceListener pieceListener) {
        this.pieceListener = pieceListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xCoordinate = e.getX();
        int yCoordinate = e.getY();

        Intersection sourceIntersection = getIntersection(e);

        if (sourceIntersection == null) return;
        pieceListener.positionSelected(sourceIntersection);
    }

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
