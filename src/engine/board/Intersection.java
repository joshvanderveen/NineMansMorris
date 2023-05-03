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

    public ArrayList<Intersection> checkIfConnectedMill(ArrayList<Intersection> intersectionsInMill, Integer millLength) {
//        if no piece on this return null
        if (this.getPiece() == null) return null;
//        if no intersections in intersectionsInMill, add this (given its checking for mill starting with each piece)
        if (intersectionsInMill.isEmpty()) {
            intersectionsInMill.add(this);
        }

//        for each connected intersection,
        for (Path path : paths) {
            Intersection otherIntersection = path.getOtherIntersection(this);
            if (otherIntersection.getPiece() == null) continue;
            if (otherIntersection.getPiece().getOwner() != this.getPiece().getOwner()) continue;
            if (intersectionsInMill.contains(otherIntersection)) continue;

            if (intersectionsInMill.size() >= 2) {
                Intersection lastIntersection = intersectionsInMill.get(intersectionsInMill.size() - 1);
                Intersection secondLastIntersection = intersectionsInMill.get(intersectionsInMill.size() - 2);

                double previousAngle = secondLastIntersection.getAngleToOtherIntersection(lastIntersection);
                double currentAngle = lastIntersection.getAngleToOtherIntersection(otherIntersection);

                if (previousAngle != currentAngle) continue;
            }

            intersectionsInMill.add(otherIntersection);

            if (intersectionsInMill.size() == millLength) return intersectionsInMill;

            ArrayList<Intersection> nextIntersectionCheck = otherIntersection.checkIfConnectedMill(intersectionsInMill, millLength);

            if (nextIntersectionCheck != null) {
                return nextIntersectionCheck;
            } else {
                intersectionsInMill.remove(otherIntersection);
            }
        }

        return null;
    }

    public double getAngleToOtherIntersection(Intersection otherIntersection) {
        return Math.toDegrees(Math.atan2(otherIntersection.getyCoordinate() - this.getyCoordinate(), otherIntersection.getxCoordinate() - this.getxCoordinate()));
    }
}