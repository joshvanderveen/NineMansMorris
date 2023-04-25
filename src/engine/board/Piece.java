package engine.board;

import engine.Player;

import java.awt.*;

public class Piece {
    /**
     * {@link engine.Player} that the Piece belongs to
     */
    private final Player owner;
    char displayChar;
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
