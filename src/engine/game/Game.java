package engine.game;

import engine.Actor;
import engine.board.GameBoard;
import engine.board.Intersection;
import engine.board.Path;
import game.actors.HumanPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    protected GameBoard gameBoard;
    protected ArrayList<Actor> players = new ArrayList<Actor>();
    public void addPlayer(Actor player) {
        players.add(player);
    }

    public void run() {

        setup();

//        checkSetup();

        while (isGameOver()) {
            loop();
        }

        endGame();
    }

    public void setup() {

        HumanPlayer humanPlayer1 = new HumanPlayer();
        HumanPlayer humanPlayer2 = new HumanPlayer();

        players.add(humanPlayer1);
        players.add(humanPlayer2);

        gameBoard = new GameBoard();

        /**
         * Coordinates of each intersection
         */
        Point[] coords = {
                new Point(0, 0), new Point(3, 0), new Point(6, 0),
                new Point(1, 1), new Point(3, 1), new Point(5, 1),
                new Point(2, 2), new Point(3, 2), new Point(4, 2),
                new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(4, 3), new Point(5, 3), new Point(6, 3),
                new Point(2, 4), new Point(3, 4), new Point(4, 4),
                new Point(1, 5), new Point(3, 5), new Point(5, 5),
                new Point(0, 6), new Point(3, 6), new Point(6, 6)
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

                System.out.println("Create path?");

                if (!gameBoard.doesPathExist(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]))) {
                    Path newPath = new Path(gameBoard.getIntersection(i), gameBoard.getIntersection(relationships[i][j]));
                    gameBoard.addPath(newPath);
                    gameBoard.getIntersection(i).addPath(newPath);
                    gameBoard.getIntersection(relationships[i][j]).addPath(newPath);
                }
            }
        }

        gameBoard.printIntersections();
        gameBoard.printPaths();
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

    }

    public void endGame() {

    }

    protected void processActorTurn(Actor actor) {

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
