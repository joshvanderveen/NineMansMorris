package game.actors;

import engine.Action;
import engine.Actor;
import engine.GameBoard;

import java.util.ArrayList;

public class HumanPlayer extends Actor {
    private static final String NAME = "PLAYER";
    private static final char DISPLAY_CHAR = 'Y';
    public HumanPlayer() {
        super(NAME, DISPLAY_CHAR);
    }

    @Override
    public Action playTurn(ArrayList<Action> actions, GameBoard gameBoard) {
        return null;
    }
}
