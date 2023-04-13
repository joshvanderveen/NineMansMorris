package engine.action;

import engine.Player;
import engine.board.GameBoard;
import engine.game.Game;
import game.actions.DoNothingAction;

public abstract class TurnAction extends Action{
    public abstract String execute(Player player, GameBoard gameBoard);
    @Override
    public String menuDescription(Player actor) {
        return null;
    }

    public Action getNextAction() {
        return new DoNothingAction();
    }
}
