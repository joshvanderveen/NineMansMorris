package engine.board;

import java.util.ArrayList;

public class Intersection {

    private int id;
    private Coordinate coordinate;
    private Piece piece;

    private ArrayList<Path> paths = new ArrayList<>();

    public Intersection(int id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
        this.piece = null;
    }

    public int getId() {
        return this.id;
    }

    public int getxCoordinate() {
        return coordinate.x;
    }

    public int getyCoordinate() {
        return coordinate.y;
    }

    public Coordinate getCoordinate() {
        return coordinate;
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

    public void removePiece() {
        this.piece = null;
    }

    public boolean checkConnected(Intersection intersection) {
        for (Path path : paths) {
            if (path.getSourceIntersection() == intersection || path.getDestinationIntersection() == intersection) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param coordinate Coordinate to compare against current intersection location
     * @return a boolean whether the intersection is at the given coordinate
     */
    public boolean intersectionAtCoordinate(Coordinate coordinate) {
        return this.coordinate.compareCoordinate(coordinate);
    }
}