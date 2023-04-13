package engine.game;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.action.MenuAction;
import engine.board.*;
import game.actions.*;
import game.actors.HumanPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    int NUMBER_PIECES_EACH = 9;
    protected GameBoard gameBoard;
    protected ArrayList<Player> players = new ArrayList<>();
    protected ArrayList<Action> moves = new ArrayList<>();


    public void run() {
        setup();
        checkSetup();
        while (!isGameOver()) {
            loop();
        }
        endGame();
    }

    public void setup() {

        HumanPlayer player1 = new HumanPlayer("Player 1", 'X');
        HumanPlayer player2 = new HumanPlayer("Player 2", 'Y');

        this.addPlayer(player1);
        this.addPlayer(player2);

        gameBoard = new GameBoard();

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
                    gameBoard.addPath(newPath);
                    gameBoard.getIntersection(i).addPath(newPath);
                    gameBoard.getIntersection(relationships[i][j]).addPath(newPath);
                }
            }
        }

//        node 1 - node 2



//        gameBoard.printIntersections();
//        gameBoard.printPaths();

        for (int i = 0; i < NUMBER_PIECES_EACH; i++) {
            for (Player player : players) {
                gameBoard.addUnplacedPiece(new Piece(player, player.getDisplayChar()));
            }
        }
    }

    public void checkSetup() {
        if (players.size() == 0) {
            throw new IllegalStateException("Game cannot be started without players");
        }
        if (gameBoard == null) {
            throw new IllegalStateException("Game cannot be started without a Game Board");
        }
    }

    public void loop() {
        for (Player player : players) {
            Action action = processActorTurn(player);
            moves.add(action);
        }
    }

    public void endGame() {

    }

    protected Action processActorTurn(Player player) {
        ActionList availableActions = new ActionList();

        if (!player.isComputerControlled()) {
            availableActions.add(new ExitAction());
            availableActions.add(new HelpAction());
            availableActions.add(new RulesAction());
        }

        if (gameBoard.allPiecesHaveBeenPlaced()) {
            availableActions.add(new SelectPieceAction());
        }

        if (gameBoard.playerHasPieceToPlace(player)) {
            availableActions.add(new PlaceAction());
        }

        Action action = player.playTurn(availableActions, gameBoard);

        // TODO: Change from instance of
        // Calls process actor turn as the player can do more than the single MenuAction per turn
        if (action instanceof MenuAction) {
            ((MenuAction) action).execute();
            this.processActorTurn(player);
        }

        String result = ((MoveAction) action).execute(player, gameBoard);

//        Menu.getInstance().printTurnResult();

        return action;
    }

    // TODO: Add validation?
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Returns true if the game is still running
     * Checks if players have less than three pieces or no legal moves remaining.
     * @return true if game is over, false otherwise
     */
    protected boolean isGameOver() {
        return false;
    }
}
