package engine.board;

import engine.board.Intersection;
import engine.board.Path;

import java.util.ArrayList;

public class GameBoard {
    protected ArrayList<Intersection> intersections = new ArrayList<>();
    protected ArrayList<Path> paths = new ArrayList<>();

    public GameBoard() {}
    public GameBoard(ArrayList<Intersection> intersections, ArrayList<Path> paths) {
        this.intersections = intersections;
        this.paths = paths;
    }

    public void addIntersection(Intersection intersection) {
        this.intersections.add(intersection);
    }
    public void addPath(Path path) {
        this.paths.add(path);
    }

    public boolean doesPathExist(Intersection intersection1, Intersection intersection2) {
        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);
            if (
                    path.getSourceIntersection() == intersection1 && path.getDestinationIntersection() == intersection2 ||
                            path.getSourceIntersection() == intersection2 && path.getDestinationIntersection() == intersection1
            ) return true;
        }
        return false;
    }

    public Intersection getIntersection(int index) {
        return intersections.get(index);
    }

    public void setIntersection(int index, Intersection newIntersection) {
        intersections.set(index, newIntersection);
    }

    // TODO: Remove when submitting
    public void printIntersections() {
        for (Intersection intersection : intersections) {
            System.out.print(intersection.getFriendlyLocation() + " " + intersection.getxCoordinate() + " " + intersection.getyCoordinate() + "\n");
        }
        System.out.println("Number of intersections: " + intersections.size());
    }

    // TODO: Remove when submitting
    public void printPaths() {
        for (Path path : paths) {
            System.out.print("(x1:" + path.getSourceIntersection().getxCoordinate() + " y1:" + path.getSourceIntersection().getyCoordinate() + ")");
            System.out.print(" - ");
            System.out.println("(x2:" + path.getDestinationIntersection().getxCoordinate() + " y2:" + path.getDestinationIntersection().getyCoordinate() + ")");
        }
        System.out.println("Number of paths: " + paths.size());
    }
}
