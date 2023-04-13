package engine.action;

import engine.Player;
import engine.game.Game;

public abstract class TurnAction extends Action{
    public abstract String execute(Player player, Game game);
    @Override
    public String menuDescription(Player actor) {
        return null;
    }
}
