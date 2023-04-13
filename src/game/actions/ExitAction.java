package game.actions;

import engine.action.Action;
import engine.action.MenuAction;
import engine.game.Game;
import engine.Player;

public class ExitAction extends MenuAction {
    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public String menuDescription(Player player) {
        return "Type " + this.menuKey() + " to exit the game";
    }

    @Override
    public String menuKey() {
        return "EXIT";
    }
}
