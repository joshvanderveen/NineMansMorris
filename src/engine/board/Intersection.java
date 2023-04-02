package engine.board;

import java.util.ArrayList;

public class Intersection {

    private int id;
    private int xCoordinate;
    private int yCoordinate;

    private ArrayList<Path> paths;

    public Intersection(int id, int xCoordinate, int yCoordinate) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getId() {
        return this.id;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public ArrayList<Path> getEdges() {
        return paths;
    }

    public void addEdge(Path path) {
        this.paths.add(path);
    }

    public String getFriendlyLocation() {
        return "Node " + id;
    }
}