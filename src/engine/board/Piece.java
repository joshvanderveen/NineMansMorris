package engine.board;

import engine.Player;

public class Piece {
    private final Player owner;

    public Piece(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the owner of the piece
     * @return the owner
     */
    public Player getOwner() {
        return owner;
    }
}
