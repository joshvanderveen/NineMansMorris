import engine.board.GameBoard;
import engine.game.Game;
import engine.ui.UIMainGui;
import game.actors.HumanPlayer;

import java.awt.*;

public class Application {
    public static void main(String[] args) {
        // Model
        GameBoard gameBoard = new GameBoard();
        // View
        UIMainGui gui = new UIMainGui();
        // Controller
        Game game = new Game(gameBoard, gui);
    }
}
