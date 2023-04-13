package game.actors;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.board.GameBoard;

public class ComputerPlayer extends Player {
    private static final String NAME = "COMPUTER";
    private static final char DISPLAY_CHAR = 'X';
    public ComputerPlayer() {
        super(NAME, DISPLAY_CHAR);
    }

    @Override
    public Action playTurn(ActionList actions, GameBoard gameboard) {
        return null;
    }
}
