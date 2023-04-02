package engine.board;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class GameBoard {
    private static GameBoard instance;
    private ArrayList<Node> nodes;

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

    public boolean addNode(Node node) {
        for (Node _node : this.nodes) {
            if (_node.getId() == node.getId()) {
                return false;
            }
        }
        this.nodes.add(node);
        return true;
    }

    public boolean removeNode(int id) {
        for (Node _node : this.nodes) {
            if (_node.getId() == id) {
                this.nodes.remove(id);
                return true;            }
        }
        return false;
    }
}
