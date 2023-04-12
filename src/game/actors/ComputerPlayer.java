package game.actors;

import engine.Actor;
import engine.action.Action;
import engine.board.GameBoard;

import java.util.ArrayList;

public class ComputerPlayer extends Actor {
    private static final String NAME = "COMPUTER";
    private static final char DISPLAY_CHAR = 'X';
    public ComputerPlayer() {
        super(NAME, DISPLAY_CHAR);
    }

    @Override
    public Action playTurn(ArrayList<Action> actions, GameBoard gameBoard) {
        return null;
    }
}
