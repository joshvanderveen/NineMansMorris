package game.actors;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.board.GameBoard;
import engine.game.Menu;

public class HumanPlayer extends Player {
    private static final String NAME = "PLAYER";
    private static final char DISPLAY_CHAR = 'Y';

    public HumanPlayer() {
        super(NAME, DISPLAY_CHAR);
    }

    @Override
    public Action playTurn(ActionList actions, GameBoard gameboard) {
        return Menu.getInstance().displayMenu(this, actions, gameboard);
    }
}