package engine.board;

import engine.Player;

import java.awt.*;

public class Piece {
    Player owner;
    char displayChar;
    public Piece(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
