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

    public boolean compareCoordinate(Coordinate otherCoordinate) {
        return this.getX() == otherCoordinate.getX() && this.getY() == otherCoordinate.getY();
    }
}
