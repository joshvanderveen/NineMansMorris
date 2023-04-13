package game.actors;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.board.GameBoard;

public class ComputerPlayer extends Player {
    private static final String DEFAULT_NAME = "COMPUTER";
    private static final char DEFAULT_DISPLAY_CHAR = 'Y';
    public ComputerPlayer() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, true);
    }

    public ComputerPlayer(String name, char displayChar) {
        super(name, displayChar, true);
    }

    @Override
    public Action playTurn(ActionList actions, GameBoard gameboard) {
        return null;
    }
}
