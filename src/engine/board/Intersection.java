package engine.board;

import java.awt.*;
import java.util.ArrayList;

public class Intersection {

    private int id;
    private Coordinate coordinates;
    private Piece piece;

    private ArrayList<Path> paths = new ArrayList<>();

    public Intersection(int id, Coordinate coordinate) {
        this.id = id;
        this.coordinates = coordinate;
        this.piece = null;
    }

    public int getId() {
        return this.id;
    }

    public int getxCoordinate() {
        return coordinates.x;
    }

    public int getyCoordinate() {
        return coordinates.y;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public void addPath(Path path) {
        this.paths.add(path);
    }

    public String getFriendlyLocation() {
        return "Node " + id;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean checkConnected(Intersection intersection) {
        // compare coordinates x or y
        // check intersection, then intersection of next (only ever be 1 or 2 adjacent)
        return false;
    }
}