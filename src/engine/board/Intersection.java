package engine.board;

import java.awt.*;
import java.util.ArrayList;

public class Intersection {

    private int id;
    private Point coordinates;

    private ArrayList<Path> paths = new ArrayList<>();

    public Intersection(int id, Point coordinate) {
        this.id = id;
        this.coordinates = coordinate;
    }

    public int getId() {
        return this.id;
    }

    public int getxCoordinate() {
        return coordinates.x;
    }

    public int getyCoordinate() {
        return coordinates.y;
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
}