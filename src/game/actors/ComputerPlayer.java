package game.actors;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.board.GameBoard;

import java.awt.*;

public class ComputerPlayer extends Player {
    private static final String DEFAULT_NAME = "COMPUTER";
    public static final Color DEFAULT_COLOR = Color.GREEN;

    public ComputerPlayer() {
        super(DEFAULT_NAME, DEFAULT_COLOR, true);
    }

    public ComputerPlayer(String name, Color playerColor) {
        super(name, playerColor, true);
    }

    @Override
    public Action playTurn(ActionList actions, GameBoard gameboard) {
        return null;
    }
}
