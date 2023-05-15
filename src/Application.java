import com.sun.net.httpserver.HttpHandler;
import engine.Player;
import engine.board.*;
import engine.game.Game;
import engine.ui.UIMainGui;
import game.actors.HumanPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    private static ArrayList<Player> players = new ArrayList<>();

    private static final HashMap<GameConfig, Coordinate[]> gameboardCoordinates = new HashMap<>();
    private static final HashMap<GameConfig, int[][]> gameboardRelationships = new HashMap<>();
    private static final HashMap<GameConfig, Integer> numPieces = new HashMap<>();

    public static void main(String[] args) {

        // Initialise game variables
        initialiseVariables();

        // Model
        GameBoard gameBoard = initialiseGameboard(GameConfig.GAMEBOARD_2);
        // View
        UIMainGui gui = new UIMainGui();
        // Controller
        new Game(gameBoard, gui, players);
    }

    public static GameBoard initialiseGameboard(GameConfig gameConfiguration) {

        GameBoard gameBoard = new GameBoard();

        Coordinate[] coordinates = gameboardCoordinates.get(gameConfiguration);

        for (int i = 0; i < coordinates.length; i++) {
            gameBoard.addIntersection(new Intersection(i, coordinates[i]));
        }

        int[][] relationships = gameboardRelationships.get(gameConfiguration);

        for (int i = 0; i < gameboardRelationships.get(gameConfiguration).length; i++) {
            for (int j = 0; j < relationships[i].length; j++) {

                if (!gameBoard.doesPathExist(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]))) {
                    Path newPath = new Path(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]));
                    gameBoard.getIntersection(i).addPath(newPath);
                    gameBoard.getIntersection(relationships[i][j]).addPath(newPath);
                }
            }
        }

        for (int i = 0; i < numPieces.get(gameConfiguration); i++) {
            for (Player player : players) {
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

    public static void initialiseVariables() {

        // Initialise amount of pieces per player
        numPieces.put(GameConfig.GAMEBOARD_1, 9);
        numPieces.put(GameConfig.GAMEBOARD_2, 5);

        // Initialise players
        players.add(new HumanPlayer("Player 1", Color.BLUE));
        players.add(new HumanPlayer("Player 2", Color.GREEN));

        /*
         * Coordinates of each intersection for Gameboard 1
         */
        Coordinate[] gameboard1Coordinates = {
                new Coordinate(0, 0), new Coordinate(3, 0), new Coordinate(6, 0),
                new Coordinate(1, 1), new Coordinate(3, 1), new Coordinate(5, 1),
                new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2),
                new Coordinate(0, 3), new Coordinate(1, 3), new Coordinate(2, 3), new Coordinate(4, 3), new Coordinate(5, 3), new Coordinate(6, 3),
                new Coordinate(2, 4), new Coordinate(3, 4), new Coordinate(4, 4),
                new Coordinate(1, 5), new Coordinate(3, 5), new Coordinate(5, 5),
                new Coordinate(0, 6), new Coordinate(3, 6), new Coordinate(6, 6)
        };

        gameboardCoordinates.put(GameConfig.GAMEBOARD_1, gameboard1Coordinates);

        /*
         * Coordinates of each intersection for Gameboard 2
         */
        Coordinate[] gameboard2Coordinates = {
                new Coordinate(3, 0),
                new Coordinate(2, 1), new Coordinate(4, 1),
                new Coordinate(1, 2), new Coordinate(3, 2), new Coordinate(5, 2),
                new Coordinate(0, 3), new Coordinate(2, 3), new Coordinate(4, 3), new Coordinate(6, 3),
                new Coordinate(1, 4), new Coordinate(3, 4), new Coordinate(5, 4),
                new Coordinate(2, 5), new Coordinate(4, 5),
                new Coordinate(3, 6)
        };

        gameboardCoordinates.put(GameConfig.GAMEBOARD_2, gameboard2Coordinates);

        /*
         * Relationships of each intersection for Gameboard 1
         */
        int[][] gameboard1relationships = {
                {1, 9}, {0, 2, 4}, {1, 14},
                {4, 10}, {1, 3, 5, 7}, {4, 13},
                {7, 11}, {4, 6, 8}, {7, 12},
                {0, 10, 21}, {3, 9, 11, 18}, {6, 10, 15}, {8, 13, 17}, {5, 12, 14, 20}, {2, 13, 23},
                {11, 16}, {15, 17, 19}, {12, 16},
                {10, 19}, {16, 18, 20, 22}, {13, 19},
                {9, 22}, {19, 21, 23}, {14, 22}
        };

        gameboardRelationships.put(GameConfig.GAMEBOARD_1, gameboard1relationships);

        /*
         * Relationships of each intersection for Gameboard 2
         */
        int[][] gameboard2relationships = {
                {1, 2},
                {0, 3, 4}, {0, 4, 5},
                {1, 6, 7}, {1, 2, 7, 8}, {2, 8, 9},
                {3, 10}, {3, 4, 10, 11}, {4, 5, 11, 12}, {5, 12},
                {6, 7, 13}, {7, 8, 13, 14}, {8, 9, 14},
                {10, 11, 15}, {11, 12, 15},
                {13, 14}
        };

        gameboardRelationships.put(GameConfig.GAMEBOARD_2, gameboard2relationships);
    }
}
