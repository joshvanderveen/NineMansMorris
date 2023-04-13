package engine.board;

import engine.Player;
import engine.action.Action;
import engine.board.Intersection;
import engine.board.Path;
import engine.game.Game;
import game.actions.MoveAction;

import java.util.ArrayList;

public class GameBoard {
    protected ArrayList<Intersection> intersections = new ArrayList<>();
    protected ArrayList<Path> paths = new ArrayList<>();

    protected ArrayList<Piece> unplacedPieces = new ArrayList<>();
    protected ArrayList<Piece> placedPieces = new ArrayList<>();
    protected ArrayList<Piece> removedPieces = new ArrayList<>();

    public GameBoard() {}
    public GameBoard(ArrayList<Intersection> intersections, ArrayList<Path> paths) {
        this.intersections = intersections;
        this.paths = paths;
    }

    public ArrayList<Piece> getUnplacedPieces() {
        return unplacedPieces;
    }

    public ArrayList<Piece> getPlacedPieces() {
        return placedPieces;
    }

    public ArrayList<Piece> getRemovedPieces() {
        return removedPieces;
    }

    public void addUnplacedPiece(Piece piece) {
        unplacedPieces.add(piece);
    }

    public void addPlacedPiece(Piece piece) {
        placedPieces.add(piece);
    }

    public void addRemovedPiece(Piece piece) {
        removedPieces.add(piece);
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

    // TODO: Implement
    public void printBoard() {
        System.out.println("\nPrint board implementation...\n");
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

    public boolean playerHasPlacedAPiece(Player player) {
        for (Piece piece : placedPieces) {
            if (piece.getOwner() == player) {
                return true;
            }
        }
        return false;
    }

    public boolean playerHasPieceToPlace(Player player) {
        for (Piece piece : unplacedPieces) {
            if (piece.getOwner() == player) {
                return true;
            }
        }
        return false;
    }
}
