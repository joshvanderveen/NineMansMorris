package engine.board;

import java.util.ArrayList;

public class GameBoard {
    private static GameBoard instance;
    private ArrayList<Intersection> intersections;

    private int xWidth;
    private int yHeight;

    public static GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    //    TODO implement printBoard as generic function for any board
    public String printBoard() {
        return null;
    }

    public boolean addNode(Intersection intersection) {
        for (Intersection _intersection : this.intersections) {
            if (_intersection.getId() == intersection.getId()) {
                return false;
            }
        }
        this.intersections.add(intersection);
        return true;
    }

    public boolean removeNode(int id) {
        for (Intersection _intersection : this.intersections) {
            if (_intersection.getId() == id) {
                this.intersections.remove(id);
                return true;            }
        }
        return false;
    }
}
