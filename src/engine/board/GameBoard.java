package engine.board;

import engine.Player;
import engine.action.Action;
import engine.board.Intersection;
import engine.board.Path;
import engine.game.Game;
import engine.game.InputManager;
import engine.game.Menu;
import game.actions.MoveAction;

import java.awt.*;
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
        int xWidth = 0;
        int yWidth = 0;

        String ROW_GAP = "     ";


        for (Intersection intersection : intersections) {
            int xCoordinate = intersection.getxCoordinate();
            int yCoordinate = intersection.getyCoordinate();

            if (xCoordinate > xWidth) xWidth = xCoordinate;
            if (yCoordinate > yWidth) yWidth = yCoordinate;
        }

        System.out.print("\n" + ROW_GAP);
        for (int i = 0; i < xWidth + 1; i++) {
            System.out.print(InputManager.getInstance().characterAtIndex(i));
            System.out.print("     ");
        }
        System.out.print("\n\n");

        for (int i = 0; i < yWidth + 1; i++) {
            System.out.print(ROW_GAP);
            for (int j = 0; j < xWidth + 1; j++) {
                Intersection intersectionAtCoordinate = this.getIntersectionAtCoordinate(new Coordinate(i, j));
                if (intersectionAtCoordinate == null) {
                    System.out.print(" ");
                }
                else if (intersectionAtCoordinate.getPiece() == null) {
                    System.out.print("*");
                } else {
                    System.out.print(intersectionAtCoordinate.getPiece().displayChar);
                }
                System.out.print(ROW_GAP);
            }
            System.out.print(i + 1);
            System.out.print("\n\n");
        }

        System.out.print("\n");
    }

    private Intersection getIntersectionAtCoordinate(Coordinate coordinate) {
        for (Intersection intersection : intersections) {
            if (intersection.intersectionAtCoordinate(coordinate)) {
                return intersection;
            }
        }
        return null;
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

    public boolean allPiecesHaveBeenPlaced() {
        return unplacedPieces.size() == 0;
    }

    public boolean playerHasPieceToPlace(Player player) {
        for (Piece piece : unplacedPieces) {
            if (piece.getOwner() == player) {
                return true;
            }
        }
        return false;
    }
    // piece: source
    // point: destination
    public boolean isValidMove(Player player, Piece piece, Coordinate coordinate) {
        if (!(piece.getOwner() == player)) return false;
        // is there a piece at the destination - is the destination a neighbour of the source
        Intersection intersectionAtPiece = null;

        for (Intersection intersection: intersections) {
            if (intersection.getPiece() != null && intersection.getPiece() == piece) {
                intersectionAtPiece = intersection;
            }
        }

        if (intersectionAtPiece == null) return false;

        // check if there is an intersection at coordinate

        Intersection intersectionAtDestinationPoint = null;

        for (Intersection intersection: intersections) {
            // TODO: make a method that compares a coordinate to an intersection
            if (intersection.getxCoordinate() == coordinate.x && intersection.getyCoordinate() == coordinate.y) {
                intersectionAtDestinationPoint = intersection;
                break;
            }
        }

        if (intersectionAtDestinationPoint == null) return false;

        if (intersectionAtDestinationPoint.getPiece() != null) return false;

        // check if intersectionAtPiece is a neighbour of intersectionAtPoint
        return intersectionAtPiece.checkConnected(intersectionAtDestinationPoint);
    }
}
