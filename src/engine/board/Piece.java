package engine.board;

import engine.Player;

public class Piece {
    /**
     * {@link engine.Player} that the Piece belongs to
     */
    private final Player owner;
    private boolean isInMill = false;

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

    public void setMillInvolvement(boolean isInMill) {
        this.isInMill = isInMill;
    }

    public boolean getMillInvolvement() {
        return isInMill;
    }
}
