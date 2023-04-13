package game.actions;

import engine.action.Action;
import engine.Player;
import engine.action.MenuAction;
import engine.game.Game;
import engine.game.InputManager;

public class HelpAction extends MenuAction {
    @Override
    public void execute() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("NINE MEN'S MORRIS – GAME CONTROLS");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Placing: Type the grid reference of the location you want");
        System.out.println("To place your piece, then press enter.");
        System.out.println("Sliding/Jumping: Type the grid reference of current location of the piece you want to move, then press enter.");
        System.out.println("Then type the grid reference of the location you want to move that piece to, then press enter.");
        System.out.println("Removing a piece: The game will prompt you when you can remove an opponent’s piece.");
        System.out.println("Type the grid reference of the location of the piece you want to remove, then press enter.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        InputManager.getInstance().waitForEnter();
    }

    @Override
    public String menuDescription(Player player) {
        return "Type " + this.menuKey() + " to view the game controls";
    }

    @Override
    public String menuKey() {
        return "HELP";
    }
}
