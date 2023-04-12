package game.actions;

import engine.Actor;
import engine.game.Game;
import engine.action.Action;

public class SelectPieceAction extends Action {
    @Override
    public String execute(Actor actor, Game game) {
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Select piece to move";
    }
}
