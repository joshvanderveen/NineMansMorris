package engine.action;

import engine.Player;
import engine.board.GameBoard;

public abstract class TurnAction extends Action {
    public abstract String execute(Player player, GameBoard gameBoard);

    private Action nextAction = null;
    @Override
    public String menuDescription(Player actor) {
        return null;
    }

    public Action getNextAction() {
        return null;
    }

    private void setNextAction(Action action) {
        this.nextAction = action;
    }
}
