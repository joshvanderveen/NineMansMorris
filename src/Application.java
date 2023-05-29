import model.Player;
import model.board.*;
import controller.Game;
import view.UIConfigurations;
import view.UIMainGui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    private static ArrayList<Player> players = new ArrayList<>();

    private static final HashMap<GameBoardConfig, Coordinate[]> gameboardCoordinates = new HashMap<>();
    private static final HashMap<GameBoardConfig, int[][]> gameboardRelationships = new HashMap<>();
    private static final HashMap<GameBoardConfig, Integer> numPieces = new HashMap<>();

    /**
     * Main runtime method that is called to start the game
     * @param args default arguments
     */
    public static void main(String[] args) {

        // Initialise game variables
        initialiseVariables();

        GameBoardConfig gameBoardConfig = UIConfigurations.chooseGameConfig();
        ArrayList<String> playerNames = UIConfigurations.choosePlayerNames();
        Integer millLength = UIConfigurations.chooseMillLength();

        for (String playerName : playerNames) {
            players.add(new Player(playerName, Color.BLUE));
        }

        // Model
        GameBoard gameBoard = initialiseGameboard(gameBoardConfig);
        // View
        UIMainGui gui = new UIMainGui();
        // Controller
        new Game(gameBoard, gui, players);
    }

    /**
     * Initialises the GameBoard based on the game configuration
     * @param gameBoardConfiguration the game configuration to initialise the GameBoard with
     * @return the initialised GameBoard
     */
    public static GameBoard initialiseGameboard(GameBoardConfig gameBoardConfiguration) {

        GameBoard gameBoard = new GameBoard();

        Coordinate[] coordinates = gameboardCoordinates.get(gameBoardConfiguration);

        for (int i = 0; i < coordinates.length; i++) {
            gameBoard.addIntersection(new Intersection(i, coordinates[i]));
        }

        int[][] relationships = gameboardRelationships.get(gameBoardConfiguration);

        for (int i = 0; i < gameboardRelationships.get(gameBoardConfiguration).length; i++) {
            for (int j = 0; j < relationships[i].length; j++) {

                if (!gameBoard.doesPathExist(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]))) {
                    Path newPath = new Path(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]));
                    gameBoard.getIntersection(i).addPath(newPath);
                    gameBoard.getIntersection(relationships[i][j]).addPath(newPath);
                }
            }
        }

        for (int i = 0; i < numPieces.get(gameBoardConfiguration); i++) {
            for (Player player : players) {
                gameBoard.addUnplacedPiece(new Piece(player));
            }
        }

        return gameBoard;
    }

    /**
     * Initialises the game variables for the multiple boards
     */
    public static void initialiseVariables() {

        // Initialise amount of pieces per player
        numPieces.put(GameBoardConfig.GAMEBOARD_1, 9);
        numPieces.put(GameBoardConfig.GAMEBOARD_2, 5);

        // Initialise players
//        players.add(new Player("Player 1", new Color(63, 136, 197)));
//        players.add(new Player("Player 2", new Color(244, 157, 55)));

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

        gameboardCoordinates.put(GameBoardConfig.GAMEBOARD_1, gameboard1Coordinates);

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

        gameboardCoordinates.put(GameBoardConfig.GAMEBOARD_2, gameboard2Coordinates);

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

        gameboardRelationships.put(GameBoardConfig.GAMEBOARD_1, gameboard1relationships);

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

        gameboardRelationships.put(GameBoardConfig.GAMEBOARD_2, gameboard2relationships);
    }
}