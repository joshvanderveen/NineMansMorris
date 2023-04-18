package game.actors;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.board.GameBoard;

import java.awt.*;

public class ComputerPlayer extends Player {
    private static final String DEFAULT_NAME = "COMPUTER";
    private static final char DEFAULT_DISPLAY_CHAR = 'Y';

    public static final Color DEFAULT_COLOR = Color.GREEN;

    public ComputerPlayer() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_COLOR, true);
    }

    public ComputerPlayer(String name, char displayChar, Color playerColor) {
        super(name, displayChar, playerColor, true);
    }

    @Override
    public Action playTurn(ActionList actions, GameBoard gameboard) {
        return null;
    }
}
