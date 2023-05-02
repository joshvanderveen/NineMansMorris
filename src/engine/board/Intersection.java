package engine.board;

import java.util.ArrayList;
import java.util.List;

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

    public boolean checkIfConnectedMill(ArrayList<Intersection> checkedIntersections, Integer millLengthLeft, double angle) {
        if (this.getPiece() == null) return false;
//        if (checkedIntersections.contains(this)) return false;

        double angleChecked = angle;
        for (Path path : paths) {
            Intersection otherIntersection = path.getOtherIntersection(this);

            if (otherIntersection.getPiece() == null) return false;
            if (otherIntersection.getPiece().getOwner() != this.getPiece().getOwner()) return false;

            if (angleChecked < 0) {
                angleChecked = getAngleToOtherIntersection(otherIntersection);
            }

            if (!(getAngleToOtherIntersection(otherIntersection) == angleChecked)) {
                angleChecked = -1;
            } else {
                checkedIntersections.add(this);
                int newMillLengthLeft = millLengthLeft - 1;
                if (newMillLengthLeft == 0) {
                    System.out.println(angleChecked);
                    return true;
                }
                return checkIfConnectedMill(checkedIntersections, newMillLengthLeft, angleChecked);
            }
        }

        return false;
    }

    private double getAngleToOtherIntersection(Intersection otherIntersection) {
        return Math.toDegrees(Math.atan2(otherIntersection.getyCoordinate() - this.getyCoordinate(), otherIntersection.getxCoordinate() - this.getxCoordinate()));
    }
}