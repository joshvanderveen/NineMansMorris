package model.board;

import model.mill.Mill;
import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Returns a list of all the unplaced pieces.
     * @return An unmodifiable list of all the unplaced pieces.
     */
    public List<Piece> getUnplacedPieces() {
        return Collections.unmodifiableList(unplacedPieces);
    }

    /**
     * Returns a list of all the unplaced pieces for a specific player.
     * @param player The player to get the unplaced pieces for.
     * @return An unmodifiable list of all the unplaced pieces for the player.
     */
    public List<Piece> getUnplacedPieces(Player player) {
        ArrayList<Piece> playersUnplacedPieces = new ArrayList<>();

        for (Piece piece : unplacedPieces) {
            if (piece.getOwner() == player) {
                playersUnplacedPieces.add(piece);
            }
        }

        return Collections.unmodifiableList(playersUnplacedPieces);
    }

    /**
     * Returns a list of all the placed pieces.
     * @return An unmodifiable list of all the placed pieces.
     */
    public List<Piece> getPlacedPieces() {
        return Collections.unmodifiableList(placedPieces);
    }

    /**
     * Returns a list of all the placed pieces for a specific player.
     * @param player The player to get the placed pieces for.
     * @return An unmodifiable list of all the placed pieces for the player.
     */
    public List<Piece> getPlacedPieces(Player player) {
        ArrayList<Piece> playersPlacedPieces = new ArrayList<>();

        for (Piece piece : placedPieces) {
            if (piece.getOwner() == player) {
                playersPlacedPieces.add(piece);
            }
        }

        return Collections.unmodifiableList(playersPlacedPieces);
    }

    /**
     * Returns a list of all the removed pieces.
     * @return An unmodifiable list of all the removed pieces.
     */
    public List<Piece> getRemovedPieces() {
        return Collections.unmodifiableList(removedPieces);
    }

    /**
     * Returns a list of all the removed pieces for a specific player.
     * @param player The player to get the removed pieces for.
     * @return An unmodifiable list of all the removed pieces for the player.
     */
    public List<Piece> getRemovedPieces(Player player) {
        ArrayList<Piece> playersRemovedPieces = new ArrayList<>();

        for (Piece piece : removedPieces) {
            if (piece.getOwner() == player) {
                playersRemovedPieces.add(piece);
            }
        }

        return Collections.unmodifiableList(playersRemovedPieces);
    }

    /**
     * Add a piece to the unplaced pieces.
     * @param piece The piece to add.
     */
    public void addUnplacedPiece(Piece piece) {
        unplacedPieces.add(piece);
    }

    /**
     * Add a piece to the placed pieces.
     * @param piece The piece to add.
     */
    public void addPlacedPiece(Piece piece) {
        placedPieces.add(piece);
    }

    /**
     * Add a piece to the removed pieces.
     * @param piece The piece to add.
     */
    public void addRemovedPiece(Piece piece) {
        removedPieces.add(piece);
    }

    /**
     * Add an intersection to the board.
     * @param intersection The intersection to add.
     */
    public void addIntersection(Intersection intersection) {
        this.intersections.add(intersection);
    }

    /**
     * Checks if a path exists between two intersections.
     * @param source The source intersection.
     * @param destination The destination intersection.
     * @return Whether a path exists between the two intersections.
     */
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

    /**
     * Gets the intersection at a specified index.
     * @param index The index of the intersection to get.
     * @return The intersection at the specified index.
     */
    public Intersection getIntersection(int index) {
        return intersections.get(index);
    }

    /**
     * Gets the intersection at a specified coordinate.
     * @param coordinate The coordinate of the intersection to get.
     * @return The intersection at the specified coordinate.
     */
    public Intersection getIntersection(Coordinate coordinate) {
        return getIntersectionAtCoordinate(coordinate);
    }

    /**
     * Gets all the intersections on the GameBoard.
     * @return An unmodifiable list of all the intersections on the GameBoard.
     */
    public List<Intersection> getIntersections() {
        return Collections.unmodifiableList(intersections);
    }

    /**
     * Gets the number of intersections on the board.
     * @return The number of intersections on the board.
     */
    public int getIntersectionsSize() {
        return intersections.size();
    }

    /**
     * Gets the intersection at a specified coordinate.
     * @param coordinate The coordinate of the intersection to get.
     * @return The intersection at the specified coordinate.
     */
    private Intersection getIntersectionAtCoordinate(Coordinate coordinate) {
        for (Intersection intersection : intersections) {
            if (intersection.intersectionAtCoordinate(coordinate)) {
                return intersection;
            }
        }
        return null;
    }

    /**
     * Makes a move on the board.
     * @param player The player making the move.
     * @param pieceToMove The piece to move.
     * @param sourceIntersection The intersection location of the piece.
     * @param destinationIntersection The desired destination intersection.
     * @return Whether the move was successful.
     */
    public boolean makeMove(Player player, Piece pieceToMove, Intersection sourceIntersection, Intersection destinationIntersection) {
        if (!isValidMove(player, pieceToMove, sourceIntersection, destinationIntersection)) {
            return false;
        }
        return placePiece(pieceToMove, destinationIntersection);
    }

    /**
     * Checks if a move is valid.
     * @param player The player making the move.
     * @param piece The piece to move.
     * @param sourceIntersection The intersection location of the piece.
     * @param destinationIntersection The desired destination intersection.
     * @return Whether the move is valid.
     */
    public boolean isValidMove(Player player, Piece piece, Intersection sourceIntersection, Intersection destinationIntersection) {
        int numPlayersPieces = 0;

        if (piece.getOwner() != player) return false;
        if (sourceIntersection.getPiece() != piece) return false;
        if (destinationIntersection.getPiece() != null) return false;

        for (Piece p : placedPieces) {
            if (p.getOwner() == player) numPlayersPieces++;
        }

        if (numPlayersPieces <= 3) return true;

        if (!sourceIntersection.checkConnectedInALine(destinationIntersection)) return false;

        // If the rule is only for the directly connected intersections, use the following line instead
        // if (!sourceIntersection.checkDirectlyConnected(destinationIntersection)) return false;

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
     * Checks for all mills on the board.
     * @return A 2D Arraylist of Intersections that represent all mills on the board.
     */
    public ArrayList<Mill> checkForMills() {
        ArrayList<Mill> mills = new ArrayList<>();

        for (Intersection intersection : intersections) {
            if (intersection.getPiece() == null) continue;

            ArrayList<Intersection> intersectionsInMill = new ArrayList<>();
            ArrayList<Intersection> mill = intersection.checkIfConnectedMill(intersectionsInMill, MILL_LENGTH);

            if (mill == null) continue;

            Mill newMill = new Mill(mill);

            boolean doesMillMatch = false;

            for (Mill existingMill : mills) {
                // check if the mill exists and if the mill has the same pieces
                if (existingMill.equals(newMill)) {
                    doesMillMatch = true;
                    break;
                }
            }
            if (doesMillMatch) continue;

            mills.add(newMill);
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

    /**
     * Returns whether the player has a move that they could make, or whether they are blocked off by the opponent.
     * @param player The player to check.
     * @return Whether the player has a move that they could make.
     */
    private boolean canPlayerMove(Player player) {
        for (Piece piece : getPlacedPieces(player)) {
            for (Intersection intersection : intersections) {
                for (Intersection otherIntersection : intersections) {
                    if (isValidMove(player, piece, intersection, otherIntersection)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether the game is over for the current players turn.
     * @param player The current player.
     * @return Whether the game is over or not.
     */
    public boolean isGameOver(Player player) {
        if (getUnplacedPieces(player).size() > 0) return false;
        return !canPlayerMove(player) || getPlacedPieces(player).size() < 3;
    }

    public void setMillLength(int length) {
        MILL_LENGTH = length;
    }
}
