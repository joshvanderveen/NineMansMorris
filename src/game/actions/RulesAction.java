package game.actions;

import engine.action.Action;
import engine.Player;
import engine.action.MenuAction;
import engine.game.Game;
import engine.game.InputManager;

public class RulesAction extends MenuAction {
    @Override
    public void execute() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("NINE MEN'S MORRIS - RULES");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("- Each player starts with 9 pieces.");
        System.out.println("- In the first phase of the game, players take turns to place their pieces on the board.");
        System.out.println("- Once you have placed all of your pieces, you can begin to move them, sliding them to an empty adjacent intersection.");
        System.out.println("- When placing and moving your pieces, your aim is to form Mills. A Mill is a straight row of 3 of your pieces.");
        System.out.println("- When you form a Mill, you can remove one of your opponent’s pieces from the board. The piece you remove cannot be part of an existing Mill.");
        System.out.println("- Once a piece has been removed from the board, it cannot be played again.");
        System.out.println("- If you only have 3 pieces left, you can jump your pieces. This means that on your turn, rather than sliding your pieces, you can move them to any free intersection on the board.");
        System.out.println("- Your aim is to leave your opponent with less than 3 pieces on the board, or no legal moves on their turn. If you successfully achieve either of these conditions, you win the game.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        InputManager.getInstance().waitForEnter();
    }

    @Override
    public String menuDescription(Player player) {
        return "Type " + this.menuKey() + " to view the game rules";
    }

    @Override
    public String menuKey() {
        return "RULES";
    }
}
