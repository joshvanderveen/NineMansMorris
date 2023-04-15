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
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBoard {
    protected ArrayList<Intersection> intersections = new ArrayList<>();
    protected ArrayList<Piece> unplacedPieces = new ArrayList<>();
    protected ArrayList<Piece> placedPieces = new ArrayList<>();
    protected ArrayList<Piece> removedPieces = new ArrayList<>();

    public GameBoard() {}
    public GameBoard(ArrayList<Intersection> intersections, ArrayList<Path> paths) {
        this.intersections = intersections;
    }

    public List<Piece> getUnplacedPieces() {
        return Collections.unmodifiableList(unplacedPieces);
    }

    public List<Piece> getPlacedPieces() {
        return Collections.unmodifiableList(placedPieces);
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

    public boolean doesPathExist(Intersection source, Intersection destination) {
        // check if there is a path to intersection2 from intersection1
        // since paths are added to both Intersections, we only need to check one case
        for (Path path: source.getPaths()) {
            if (path.getSourceIntersection() == source && path.getDestinationIntersection() == destination) {
                return true;
            }
        }
        return false;
    }

    public Intersection getIntersection(int index) {
        return intersections.get(index);
    }

    public int getIntersectionsSize() {
        return intersections.size();
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
            for (Path path: intersection.getPaths()) {
                System.out.println(path.getSourceIntersection().getId() + "<->" + path.getDestinationIntersection().getId());
            }
        }
        System.out.println("Number of intersections: " + intersections.size());
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

    public boolean placePiece(Piece piece, Intersection intersection) {
        if (!unplacedPieces.contains(piece)) return false;
        if (intersection.getPiece() != null) return false;

        Piece pieceToMove = unplacedPieces.get(unplacedPieces.indexOf(piece));

        unplacedPieces.remove(pieceToMove);
        placedPieces.add(pieceToMove);

        intersection.setPiece(pieceToMove);

        return true;
    }

}
