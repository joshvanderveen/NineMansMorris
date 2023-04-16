import engine.game.Game;
import game.actors.HumanPlayer;

import java.awt.*;

public class Application {
    public static void main(String[] args) {
        Game game = new Game();

        HumanPlayer player1 = new HumanPlayer("Player 1", 'X');
        HumanPlayer player2 = new HumanPlayer("Player 2", 'Y', Color.GREEN);

        game.addPlayer(player1);
        game.addPlayer(player2);
        
        game.run();
    }
}
