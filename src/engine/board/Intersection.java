package engine.board;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Get the id of the {@link Intersection}
     *
     * @return The id of the {@link Intersection}
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the x coordinate of the {@link Intersection}
     *
     * @return The x coordinate of the {@link Intersection}
     */
    public int getXCoordinate() {
        return coordinate.x;
    }

    /**
     * Get the y coordinate of the {@link Intersection}
     *
     * @return The y coordinate of the {@link Intersection}
     */
    public int getYCoordinate() {
        return coordinate.y;
    }

    /**
     * Get the {@link Coordinate} of the {@link Intersection}
     *
     * @return The {@link Coordinate} of the {@link Intersection}
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Get the {@link Path}s that are connected to the {@link Intersection}
     *
     * @return An unmodifiable list of {@link Path}s connected to the {@link Intersection}
     */
    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }

    /**
     * Adds a {@link Path} to the {@link Intersection}
     *
     * @param path  The {@link Path} to add to the {@link Intersection}
     */
    public void addPath(Path path) {
        this.paths.add(path);
    }

    /**
     * Get a friendly name for the {@link Intersection}
     *
     * @return  A {@link Intersection} node id
     */
    public String getFriendlyLocation() {
        return "Node " + id;
    }

    /**
     * Set the {@link Piece} at the {@link Intersection}
     *
     * @param piece The {@link Piece} to place at the intersection
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * @return  The {@link Piece} at the intersection
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Remove the current piece from the intersection
     */
    public void removePiece() {
        this.piece = null;
    }

    /**
     * @param otherIntersection The {@link Intersection} to compare against
     * @return                  Whether the {@link Intersection}s are connected
     */
    public boolean checkConnected(Intersection otherIntersection) {
        for (Path path : paths) {
            if (path.getSourceIntersection() == otherIntersection || path.getDestinationIntersection() == otherIntersection) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param coordinate    The {@link Coordinate} to compare against current {@link Intersection} location
     * @return              Whether the {@link Intersection} is at the given coordinate
     */
    public boolean intersectionAtCoordinate(Coordinate coordinate) {
        return this.coordinate.compareCoordinate(coordinate);
    }

    /**
     * A recursive function that checks through the paths connected to the current intersection to see if there is a mill
     *
     * @param intersectionsInMill   An Arraylist of {@link Intersection}s that contains the current intersections in the mill being checked
     * @param millLength            The configured length of a mill that is being checked
     * @return                      The mill as an Arraylist of type {@link Intersection} of the mill, or null if there is no mill
     */
    public ArrayList<Intersection> checkIfConnectedMill(ArrayList<Intersection> intersectionsInMill, Integer millLength) {
        if (this.getPiece() == null) return null;
        if (intersectionsInMill.isEmpty()) {
            intersectionsInMill.add(this);
        }

        // Interate through each path connected to the current intersection, and check whether the other intersection could be part of a mill
        for (Path path : paths) {
            Intersection otherIntersection = path.getOtherIntersection(this);

            // Initial checking to count out incorrect pieces or already checked intersections
            if (otherIntersection.getPiece() == null) continue;
            if (otherIntersection.getPiece().getOwner() != this.getPiece().getOwner()) continue;
            if (intersectionsInMill.contains(otherIntersection)) continue;

            // Can start comparing angles once there is 2 or more interesctions
            if (intersectionsInMill.size() >= 2) {
                Intersection lastIntersection = intersectionsInMill.get(intersectionsInMill.size() - 1);
                Intersection secondLastIntersection = intersectionsInMill.get(intersectionsInMill.size() - 2);

                double previousAngle = secondLastIntersection.getAngleToOtherIntersection(lastIntersection);
                double currentAngle = lastIntersection.getAngleToOtherIntersection(otherIntersection);

                if (previousAngle != currentAngle) continue;
            }

            intersectionsInMill.add(otherIntersection);

            // If there is a mill, return it
            if (intersectionsInMill.size() == millLength) return intersectionsInMill;

            // Otherwise recursively check through the other intersection
            ArrayList<Intersection> nextIntersectionCheck = otherIntersection.checkIfConnectedMill(intersectionsInMill, millLength);

            // If next intersection check includes a mill, then return it
            // Otherwise remove the next intersection, and go to the next iteration of the current intersection
            if (nextIntersectionCheck != null) {
                return nextIntersectionCheck;
            } else {
                intersectionsInMill.remove(otherIntersection);
            }
        }
        return null;
    }

    /**
     * Check the angle of another intersection in relation to the current intersection
     *
     * @param otherIntersection     The other {@link Intersection} to compare to
     * @return                      The degrees of the compared angle
     */
    public double getAngleToOtherIntersection(Intersection otherIntersection) {
        return Math.toDegrees(Math.atan2(otherIntersection.getYCoordinate() - this.getYCoordinate(), otherIntersection.getXCoordinate() - this.getXCoordinate()));
    }
}