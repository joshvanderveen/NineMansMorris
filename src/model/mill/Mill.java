package model.mill;

import model.Player;
import model.board.Intersection;

import java.util.ArrayList;

public class Mill {
    private ArrayList<Intersection> intersections;
    private boolean isUsed = false;

    public Mill(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
    }

    /**
     * Get whether the mill has been used to remove a piece
     * @return Whether the mill has been used
     */
    public boolean getIsUsed() {
        return isUsed;
    }

    /**
     * Set whether the mill has been used to remove a piece or not
     * @param isUsed Whether the mill has been used
     */
    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    /**
     * Get the intersections in the mill
     * @return The intersections that make up the mill
     */
    private ArrayList<Intersection> getIntersections() {
        return this.intersections;
    }

    /**
     * Check whether an intersection is part of the mill or not
     * @param intersection The intersection to check
     * @return Whether the intersection is part of the mill
     */
    public boolean containsIntersection(Intersection intersection) {
        return this.intersections.contains(intersection);
    }

    /**
     * Get the player that the mill belongs to
     * @return The player that owns the mill
     */
    public Player getPlayer() {
        return this.intersections.get(0).getPiece().getOwner();
    }


    /**
     * Compare the current mill to another mill to check whether they have the same intersections
     * @param object The mill to compare
     * @return Whether the mills are the same
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Mill)) return false;
        return this.getIntersections().containsAll(((Mill) object).intersections);
    }
}
