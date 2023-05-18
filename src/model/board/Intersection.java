package model.board;

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
     * @return The x coordinate of the {@link Intersection}
     */
    public int getXCoordinate() {
        return coordinate.x;
    }

    /**
     * Get the y coordinate of the {@link Intersection}
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
    public boolean checkDirectlyConnected(Intersection otherIntersection) {
        for (Path path : paths) {
            if (path.getOtherIntersection(this) == otherIntersection) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param targetIntersection {@link Intersection} to move to
     * @return whether current intersection is connected to other intersection and there are no {@link Piece}s in between
     */
    public boolean checkConnectedInALine(Intersection targetIntersection) {
        if (this.checkDirectlyConnected(targetIntersection)) return true;
        ArrayList<Intersection> checked = new ArrayList<>();
        checked.add(this);
        return checkConnectedInALineHelper(targetIntersection, checked);
    }

    /**
     * A helper function for the {@link Intersection#checkConnectedInALine(Intersection)} function that calls itself recursively to check whether the two intersections are connected in a line
     * @param targetIntersection {@link Intersection} to move to
     * @param checked an {@link ArrayList<Intersection>} to store which intersections have already been checked
     * @return whether current intersection is connected to other intersection and there are no {@link Piece}s in between
     */
    private boolean checkConnectedInALineHelper(Intersection targetIntersection, ArrayList<Intersection> checked) {
        double currentAngleToTarget = this.getAngleToOtherIntersection(targetIntersection);

        for (Path path : this.paths) {
            Intersection nextIntersection = path.getOtherIntersection(this);
            if (nextIntersection.getPiece() != null) continue;
            if (checked.contains(nextIntersection)) continue;
            if (nextIntersection == targetIntersection) return true;

            double nextIntersectionAngleToTarget = nextIntersection.getAngleToOtherIntersection(targetIntersection);

            if (nextIntersectionAngleToTarget != currentAngleToTarget) continue;

            checked.add(nextIntersection);
            if (nextIntersection.checkConnectedInALineHelper(targetIntersection, checked)) return true;
            checked.remove(nextIntersection);
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

            // Can start comparing angles once there is 2 or more intersections
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
            ArrayList<Intersection> nextIntersectionCheckResult = otherIntersection.checkIfConnectedMill(intersectionsInMill, millLength);

            // If next intersection check includes a mill, then return it
            // Otherwise remove the next intersection, and go to the next iteration of the current intersection
            if (nextIntersectionCheckResult != null) {
                return nextIntersectionCheckResult;
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
        //        https://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
        double angle = Math.toDegrees(Math.atan2(otherIntersection.getYCoordinate() - this.getYCoordinate(), otherIntersection.getXCoordinate() - this.getXCoordinate()));
        return angle < 0 ? angle + 360 : angle;
    }
}