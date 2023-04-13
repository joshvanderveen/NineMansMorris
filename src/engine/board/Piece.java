package engine.board;

import engine.Player;

public class Piece {
    Player owner;
    char displayChar;

    public Piece(Player owner, char displayChar) {
        this.owner = owner;
        this.displayChar = displayChar;
    }

    public Player getOwner() {
        return owner;
    }
}
