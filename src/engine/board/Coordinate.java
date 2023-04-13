package engine.board;

import java.awt.*;

public class Coordinate extends Point {
    public Coordinate() {
        super();
    }

    public Coordinate(Point p) {
        super(p);
    }

    public Coordinate(int x, int y) {
        super(x, y);
    }

    public boolean comparePoint(Point otherPoint) {
        return this.getX() == otherPoint.getX() && this.getY() == otherPoint.getY();
    }
}
