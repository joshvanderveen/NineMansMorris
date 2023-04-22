import engine.Player;
import engine.board.*;
import engine.game.Game;
import engine.ui.UIMainGui;
import game.actors.HumanPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Application {

    private static ArrayList<Player> players = new ArrayList<>();
    public static void main(String[] args) {
        // Model
        GameBoard gameBoard = initialiseGameboard();
        // View
        UIMainGui gui = new UIMainGui();
        // Controller
        new Game(gameBoard, gui, players);
    }

    public static GameBoard initialiseGameboard() {

        int NUMBER_PIECES_EACH = 9;

        GameBoard gameBoard = new GameBoard();

        players.add(new HumanPlayer("Player 1", Color.BLUE));
        players.add(new HumanPlayer("Player 2", Color.GREEN));

        /*
         * Coordinates of each intersection
         */
        Coordinate[] coords = {
                new Coordinate(0, 0), new Coordinate(3, 0), new Coordinate(6, 0),
                new Coordinate(1, 1), new Coordinate(3, 1), new Coordinate(5, 1),
                new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2),
                new Coordinate(0, 3), new Coordinate(1, 3), new Coordinate(2, 3), new Coordinate(4, 3), new Coordinate(5, 3), new Coordinate(6, 3),
                new Coordinate(2, 4), new Coordinate(3, 4), new Coordinate(4, 4),
                new Coordinate(1, 5), new Coordinate(3, 5), new Coordinate(5, 5),
                new Coordinate(0, 6), new Coordinate(3, 6), new Coordinate(6, 6)
        };

        for (int i = 0; i < coords.length; i++) {
            gameBoard.addIntersection(new Intersection(i, coords[i]));
        }

        int[][] relationships = {
                {1, 9}, {0, 2, 4}, {1, 14},
                {4, 10}, {1, 3, 5, 7}, {4, 13},
                {7, 11}, {4, 6, 8}, {7, 12},
                {0, 10, 21}, {3, 9, 11, 18}, {6, 10, 15}, {8, 13, 17}, {5, 12, 14, 20}, {2, 13, 23},
                {11, 16}, {15, 17, 19}, {12, 16},
                {10, 19}, {16, 18, 20, 22}, {13, 19},
                {9, 22}, {19, 21, 23}, {14, 22}
        };

        for (int i = 0; i < relationships.length; i++) {
            for (int j = 0; j < relationships[i].length; j++) {

                if (!gameBoard.doesPathExist(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]))) {
                    Path newPath = new Path(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]));
                    gameBoard.getIntersection(i).addPath(newPath);
                    gameBoard.getIntersection(relationships[i][j]).addPath(newPath);
                }
            }
        }

        for (int i = 0; i < NUMBER_PIECES_EACH; i++) {
            for (Player player : players) {
                // TODO change from Color.blue to change depending on player
                gameBoard.addUnplacedPiece(new Piece(player));
            }
        }

        // Just for sprint 1
        // Places all pieces from unplacedPieces into the first intersections in the gameboard
        int unplacedLength = gameBoard.getUnplacedPieces().size();
        for (int i = 0; i < unplacedLength; i++) {
            Piece piece = gameBoard.getUnplacedPieces().get(unplacedLength - i - 1);
            boolean piecePlaced = false;
            while (!piecePlaced) {
                piecePlaced = gameBoard.placePiece(piece, gameBoard.getIntersection(i));
            }
        }

        return gameBoard;
    }
}
