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

    /**
     * Places a piece on the board.
     * @param piece The piece to place.
     * @param destinationInteresection The {@link Intersection} to place the piece on.
     * @return whether the piece was placed successfully.
     */
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

    /**
     * Checks if a player has a mill on the board.
     * @param player The player to check a mill for.
     * @return A 2D Arraylist of Intersections that represent all mills on the board.
     */
    public ArrayList<ArrayList<Intersection>> checkForMills(Player player) {
        ArrayList<ArrayList<Intersection>> mills = new ArrayList<>();

        for (Intersection intersection : intersections) {
            if (intersection.getPiece() == null) continue;
            if (intersection.getPiece().getOwner() != player) continue;

            ArrayList<Intersection> intersectionsInMill = new ArrayList<>();
            ArrayList<Intersection> mill = intersection.checkIfConnectedMill(intersectionsInMill, MILL_LENGTH);

            if (mill == null) continue;

            boolean doesMillMatch = false;

            for (ArrayList<Intersection> existingMill : mills) {
                // check if the mill exists and if the mill has the same pieces
                if (existingMill.containsAll(mill)) {
                    doesMillMatch = true;
                    break;
                }
            }
            if (doesMillMatch) continue;

            mills.add(mill);
        }

        return mills;
    }

    /**
     * Removes a piece from the board.
     * @param piece The piece to remove.
     * @return whether the piece was removed successfully.
     */
    public void removeFromBoard(Piece piece) {
        if (piece == null) return;

        if (placedPieces.contains(piece)) {
            placedPieces.remove(piece);
            removedPieces.add(piece);
        } else {
            return;
        }

        for (Intersection intersection : intersections) {
            if (intersection.getPiece() == piece) {
                intersection.removePiece();
                break;
            }
        }
    }
}
