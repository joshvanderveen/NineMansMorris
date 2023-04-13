package engine.action;

import engine.Player;
import engine.game.Game;

public abstract class Action {
//    public abstract String execute(Player actor, Game game);
    public abstract String menuDescription(Player actor);
    public String menuKey() {
        return null;
    }
}
