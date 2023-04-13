package game.actors;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.action.MenuAction;
import engine.board.GameBoard;
import engine.game.Menu;

public class HumanPlayer extends Player {
    private static final String DEFAULT_NAME = "PLAYER";
    private static final char DEFAULT_DISPLAY_CHAR = 'X';

    public HumanPlayer() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR);
    }

    public HumanPlayer(String name, char displayChar) {
        super(name, displayChar, false);
    }


    @Override
    public Action playTurn(ActionList actions, GameBoard gameboard) {
        return Menu.getInstance().displayMenu(this, actions, gameboard);
    }
}