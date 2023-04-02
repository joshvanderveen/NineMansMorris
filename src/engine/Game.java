package engine;

import java.util.ArrayList;

public class Game {
    protected ArrayList<GameBoard> gameBoards = new ArrayList<GameBoard>();
    protected ArrayList<Actor> players = new ArrayList<Actor>();
    public void addGameBoard(GameBoard gameBoard) {
        gameBoards.add(gameBoard);
    }

    public void addPlayer(Actor player) {
        players.add(player);
    }

    public void run() {
        // pre logic checks
        if (players.size() == 0) {
            throw new IllegalStateException("Game cannot be started without players");
        }

        // game loop
        // TODO: change the game loop condition
        while (isGameOver()) {
            System.out.println("hello");
        }
        System.out.println(endGameMessage());
    }

    protected void processActorTurn(Actor actor) {

    }

    protected String endGameMessage() {
        return "Game over.";
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
