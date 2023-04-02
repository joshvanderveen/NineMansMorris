package engine;

import engine.board.Intersection;

import java.util.ArrayList;

public class GameBoard {
    protected ArrayList<Intersection> intersections = new ArrayList<Intersection>();
    public GameBoard(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
    }
}
