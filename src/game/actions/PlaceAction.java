package game.actions;

import engine.action.Action;
import engine.Player;
import engine.action.TurnAction;
import engine.game.Game;

public class PlaceAction extends TurnAction {
    @Override
    public String execute(Player player, Game game) {
        return null;
    }

    @Override
    public String menuDescription(Player player) {
        return "Place a piece";
    }
}
