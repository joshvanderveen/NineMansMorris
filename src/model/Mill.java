package model;

import model.board.Intersection;

import java.util.ArrayList;

public class Mill {
    private ArrayList<Intersection> intersections;
    private boolean isUsed = false;

    public Mill() {
        this.intersections = new ArrayList<>();
    }

    public Mill(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
    }

    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public boolean changeIsUsed() {
        isUsed = !isUsed;
        return isUsed;
    }
}
