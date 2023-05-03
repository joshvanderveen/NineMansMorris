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

    public ArrayList<Intersection> checkIfConnectedMill(ArrayList<Intersection> intersectionsInMill, Integer millLength, Double angle) {
        if (this.getPiece() == null) return null;

        for (Path path : paths) {
            Intersection otherIntersection = path.getOtherIntersection(this);

            if (otherIntersection.getPiece() == null) return null;
            if (otherIntersection.getPiece().getOwner() != this.getPiece().getOwner()) return null;
            if (intersectionsInMill.contains(otherIntersection)) continue;

            // If the first in being checked
            if (intersectionsInMill.isEmpty()) {
                intersectionsInMill.add(this);
                angle = this.getAngleToOtherIntersection(otherIntersection);
            } else {
                double angleWithPrevious = this.getAngleToOtherIntersection(intersectionsInMill.get(intersectionsInMill.size() - 1));

            }
        }

        return null;
    }

//    public ArrayList<Intersection> checkIfConnectedMill1(ArrayList<Intersection> intersectionsInMill, Integer millLength, Double angle) {
//        if (this.getPiece() == null) {
//            return null;
//        }
//
//        for (Path path : paths) {
//            Intersection otherIntersection = path.getOtherIntersection(this);
//
//            if (otherIntersection.getPiece() == null || otherIntersection.getPiece().getOwner() != this.getPiece().getOwner()) {
//                continue;
//            }
//
//            if (intersectionsInMill.contains(otherIntersection)) {
//                continue;
//            }
//
//            if (intersectionsInMill.isEmpty()) {
//                // First intersection in the mill, store its coordinate
//                intersectionsInMill.add(this);
//                intersectionsInMill.add(otherIntersection);
//                angle = getAngleToOtherIntersection(otherIntersection);
//            } else {
//                // Check if the current intersection is in a straight line with the previous intersection
//                double currAngle = this.getAngleToOtherIntersection(otherIntersection);
//
//                if (currAngle != 0 && currAngle != 90) {
//                    // Current intersection is not in a straight line with previous intersections
//                    continue;
//                }
//
//                intersectionsInMill.add(otherIntersection);
//
//                if (intersectionsInMill.size() == millLength) {
//                    // Check if the angle between the first and last intersections is 0 or 90 degrees
//                    Intersection firstCoordinate = intersectionsInMill.get(0);
//                    Intersection lastCoordinate = intersectionsInMill.get(intersectionsInMill.size() - 1);
//                    double lastAngle = firstCoordinate.getAngleToOtherIntersection(lastCoordinate);
//
//                    if (lastAngle != 0 && lastAngle != 90) {
//                        // Last intersection is not in a straight line with the first intersection
//                        intersectionsInMill.remove(otherIntersection);
//                        continue;
//                    }
//
//                    return intersectionsInMill;
//                }
//            }
//
//            return otherIntersection.checkIfConnectedMill(intersectionsInMill, millLength, angle);
//        }
//
//        return null;
//    }


    public double getAngleToOtherIntersection(Intersection otherIntersection) {
        return Math.toDegrees(Math.atan2(otherIntersection.getyCoordinate() - this.getyCoordinate(), otherIntersection.getxCoordinate() - this.getxCoordinate()));
    }
}