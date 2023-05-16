package engine.board;

import engine.Player;

public class Piece {
    /**
     * {@link engine.Player} that the Piece belongs to
     */
    private final Player owner;

    public Piece(Player owner) {
        this.owner = owner;
    }

    /**
     * Getter for returning the owner of the piece
     * @return {@link engine.Player}
     */
    public Player getOwner() {
        return owner;
    }
}
