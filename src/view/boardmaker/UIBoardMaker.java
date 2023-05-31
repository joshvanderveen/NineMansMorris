package view.boardmaker;

import model.board.Coordinate;
import model.board.Intersection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIBoardMaker extends JPanel {
    private final int GRID_WIDTH = 10;
    private final int GRID_HEIGHT = 10;
    private final int GRID_GAP_SIZE = 80;
    private final int NODE_SIZE = GRID_GAP_SIZE / 2;
    private static final int X_OFFSET = 80;
    private static final int Y_OFFSET = 80;
    private ArrayList<Intersection> intersections;
    private JComboBox<ArrayList<String>> actions;

    public UIBoardMaker() {
        this.setPreferredSize(new Dimension(1500, 1000));
        this.intersections = new ArrayList<>();

        actions = new JComboBox(BoardMakerClickAction.values());

        this.add(actions);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (actions.getSelectedItem() == null) return;

                switch ((BoardMakerClickAction) actions.getSelectedItem()) {
                    case DRAW_INTERSECTION -> handleDrawIntersectionClick(e);
                    case DRAW_LINE -> handleDrawLineClick(e);
                    case ERASE_INTERSECTION -> handleEraseIntersectionClick(e);
                    default -> handleNothingClick(e);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawIntersections(g);
    }

    public void drawGrid(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        for (int i = 0; i <= GRID_WIDTH * GRID_GAP_SIZE; i += GRID_GAP_SIZE) {
            graphics.drawLine(i + X_OFFSET, 0 + X_OFFSET, i + Y_OFFSET, GRID_HEIGHT * GRID_GAP_SIZE + Y_OFFSET);
        }
        for (int i = 0; i <= GRID_HEIGHT * GRID_GAP_SIZE; i += GRID_GAP_SIZE) {
            graphics.drawLine(0 + X_OFFSET, i + X_OFFSET, GRID_WIDTH * GRID_GAP_SIZE + Y_OFFSET, i + Y_OFFSET);
        }
    }

    public void drawIntersections(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        for (Intersection intersection : intersections) {
            graphics.fillOval(intersection.getXCoordinate() * GRID_GAP_SIZE - (NODE_SIZE / 2) + X_OFFSET, intersection.getYCoordinate() * GRID_GAP_SIZE - (NODE_SIZE / 2) + Y_OFFSET, NODE_SIZE, NODE_SIZE);
        }
    }

    public void handleNothingClick(MouseEvent e) {
        return;
    }

    public void handleDrawIntersectionClick(MouseEvent e) {
        Coordinate moveCoordinate = getClosestCoordinate(e);

        if (moveCoordinate == null) return;

        // Check no other intersection exists at coordinate
        for (Intersection intersection : intersections) {
            if (intersection.getCoordinate().compareCoordinate(moveCoordinate)) return;
        }

        int intersectionIndex = intersections.size() == 0 ? 0 : intersections.get(intersections.size() - 1).getId() + 1;

        Intersection newIntersection = new Intersection(intersectionIndex, moveCoordinate);

        intersections.add(newIntersection);
        repaint();
    }

    public void handleDrawLineClick(MouseEvent e) {
        return;
    }

    public void handleEraseIntersectionClick(MouseEvent e) {
        Coordinate clickCoordinate = getClosestCoordinate(e);

        if (clickCoordinate == null) return;

        intersections.removeIf(intersection -> intersection.getCoordinate().compareCoordinate(clickCoordinate));

        repaint();
    }

    public Coordinate getClosestCoordinate(MouseEvent e) {
        Point movePoint = e.getPoint();
        movePoint.x = (movePoint.x - X_OFFSET) / GRID_GAP_SIZE;
        movePoint.y = (movePoint.y - Y_OFFSET) / GRID_GAP_SIZE;

        int circleX = movePoint.x * GRID_GAP_SIZE + X_OFFSET;
        int circleY = movePoint.y * GRID_GAP_SIZE + Y_OFFSET;
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
        circleX = movePoint.x * GRID_GAP_SIZE + X_OFFSET;
        circleY = movePoint.y * GRID_GAP_SIZE + Y_OFFSET;

        // Calculate the distance between the adjusted clicked point and the center of the circle
        double distance = Math.sqrt(Math.pow(clickedX - circleX, 2) + Math.pow(clickedY - circleY, 2));
        if (distance <= radius + buffer) {
            Coordinate moveCoordinate = new Coordinate(movePoint);

           return moveCoordinate;
        }

        return null;
    }
}
