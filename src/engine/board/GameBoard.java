package engine.board;

import engine.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard {

    private Integer MILL_LENGTH = 3;
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

    public List<Piece> getUnplacedPieces(Player player) {
        ArrayList<Piece> playersUnplacedPieces = new ArrayList<>();

        for (Piece piece : unplacedPieces) {
            if (piece.getOwner() == player) {
                playersUnplacedPieces.add(piece);
            }
        }

        return Collections.unmodifiableList(playersUnplacedPieces);
    }

    public List<Piece> getPlacedPieces() {
        return Collections.unmodifiableList(placedPieces);
    }

    public List<Piece> getPlacedPieces(Player player) {
        ArrayList<Piece> playersPlacedPieces = new ArrayList<>();

        for (Piece piece : placedPieces) {
            if (piece.getOwner() == player) {
                playersPlacedPieces.add(piece);
            }
        }

        return Collections.unmodifiableList(playersPlacedPieces);
    }

    public List<Piece> getRemovedPieces() {
        return Collections.unmodifiableList(removedPieces);
    }

    public List<Piece> getRemovedPieces(Player player) {
        return removedPieces.stream()
                .filter(piece -> piece.getOwner() == player)
                .collect(Collectors.toList());
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

    public Intersection getIntersection(Coordinate coordinate) {
        return getIntersectionAtCoordinate(coordinate);
    }

    public int getIntersectionsSize() {
        return intersections.size();
    }

    public void setIntersection(int index, Intersection newIntersection) {
        intersections.set(index, newIntersection);
    }

    private Intersection getIntersectionAtCoordinate(Coordinate coordinate) {
        for (Intersection intersection : intersections) {
            if (intersection.intersectionAtCoordinate(coordinate)) {
                return intersection;
            }
        }
        return null;
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

    public boolean makeMove(Player player, Piece pieceToMove, Intersection sourceIntersection, Intersection destinationIntersection) {
        if (!isValidMove(player, pieceToMove, sourceIntersection, destinationIntersection)) {
            return false;
        }
        return placePiece(pieceToMove, destinationIntersection);
    }

    // piece: source
    // point: destination
    public boolean isValidMove(Player player, Piece piece, Intersection sourceIntersection, Intersection destinationIntersection) {
        int numPlayersPieces = 0;

        if (piece.getOwner() != player) return false;
        if (destinationIntersection.getPiece() != null) return false;

        for (Piece p : placedPieces) {
            if (p.getOwner() == player) numPlayersPieces++;
        }

        if (numPlayersPieces <= 3) return true;

        if (!sourceIntersection.checkConnectedInALine(destinationIntersection)) return false;

        return true;

    }

    public boolean placePiece(Piece piece, Intersection destinationInteresection) {
        if (destinationInteresection.getPiece() != null) return false;

        if (unplacedPieces.contains(piece)) {
            unplacedPieces.remove(piece);
            placedPieces.add(piece);
        }

        for (Intersection intersection : intersections) {
            if (intersection.getPiece() == piece) {
                intersection.removePiece();
                break;
            }
        }

        destinationInteresection.setPiece(piece);

        return true;
    }

    public ArrayList<Intersection> checkForMills(Player player) {
        for (Intersection intersection : intersections) {
            if (intersection.getPiece() == null) continue;
            if (intersection.getPiece().getOwner() != player) continue;

            ArrayList<Intersection> intersectionsInMill = new ArrayList<>();
            ArrayList<Intersection> mill = intersection.checkIfConnectedMill(intersectionsInMill, MILL_LENGTH);

            if (mill != null) {
                System.out.println("Mill found at:");
                for (Intersection i : mill) {
                    System.out.println("x: " + (i.getXCoordinate() + 1) + ", y: " + (i.getYCoordinate() + 1));
                }
                return mill;
            }
        }
        return null;
    }

}
