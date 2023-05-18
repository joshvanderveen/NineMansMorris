package model;

import model.board.Intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mill {
    private ArrayList<Intersection> intersections;
    private boolean isUsed = false;

    public Mill(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public boolean toggleIsUsed() {
        isUsed = !isUsed;
        return isUsed;
    }

    private ArrayList<Intersection> getIntersections() {
        return this.intersections;
    }

    public boolean compareMill(Mill otherMill) {
        return this.getIntersections().containsAll(otherMill.intersections);
    }

    public boolean containsIntersection(Intersection intersection) {
        return this.intersections.contains(intersection);

//        for (Intersection i : this.intersections) {
//            if (i.equals(intersection)) return true;
//        }
//        return false;
    }

    public Player getPlayer() {
        return this.intersections.get(0).getPiece().getOwner();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Mill)) return false;
        if (obj == this) return true;
        return this.compareMill((Mill) obj);
    }
}
