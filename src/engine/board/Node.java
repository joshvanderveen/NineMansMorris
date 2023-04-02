package engine.board;

import java.util.ArrayList;

public class Node {

    private int id;
    private int xCoordinate;
    private int yCoordinate;

    private ArrayList<Edge> edges;

    public Node(int id, int xCoordinate, int yCoordinate) {
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

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public String getFriendlyLocation() {
        return "Node " + id;
    }
}