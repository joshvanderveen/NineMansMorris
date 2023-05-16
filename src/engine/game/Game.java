package engine.game;

import engine.Player;
import engine.board.*;
import engine.ui.UIMainGui;

import java.util.*;

public class Game implements PieceListener {

    private final int NUMBER_PIECES_EACH = 9;
    protected GameBoard gameBoard;
    protected UIMainGui gui;
    protected ArrayList<Player> players;
    private Intersection selectedIntersection = null;
    private Player currentPlayer;

    public Game(GameBoard gameBoard, UIMainGui gui, ArrayList<Player> players) {
        this.players = players;
        this.gameBoard = gameBoard;
        this.gui = gui;
        gui.setGameBoard(this.gameBoard);
        gui.setPlayers(this.players);
        gui.addPieceListener(this);
        gui.setVisible(true);

        currentPlayer = players.get(0);
    }

//    public void run() {
//        gui.setGameBoard(this.gameBoard);
//    }

    /**
     * Main method that takes care of Game logic and splits up tasks depending on game status
     * @param intersection - input selected intersection from UI
     */
    @Override
    public void positionSelected(Intersection intersection) {
        // TODO: Place piece - implement in future sprints

        // If start of players turn or if they haven't played valid move
        if(selectedIntersection == null) {
            // If no intersection selected and selection doesn't have a piece, don't allow turn
            if (intersection.getPiece() == null) {
                return;
            }

            // If selected intersection is occupied with other players piece
            if (intersection.getPiece().getOwner() != currentPlayer) {
                return;
            }

            // Select intersection that has current players piece
            selectedIntersection = intersection;
        } else {
            // Select place to move
            // If place to move to is unoccupied
            if (intersection.getPiece() != null) {
                selectedIntersection = null;
                gui.setSelectedIntersection(selectedIntersection);
                gui.redraw();
                return;
            }


            // Try to make move
            if (!gameBoard.makeMove(currentPlayer, selectedIntersection.getPiece(), selectedIntersection, intersection)) {
                // If move is invalid, return and wait to pick next destination
                selectedIntersection = null;
                gui.setSelectedIntersection(selectedIntersection);
                gui.redraw();
                return;
            }

            selectedIntersection = null;

            gameBoard.checkForMills(currentPlayer);

            // Turn logic
            // Set current player to other player since there's only ever 2 players
            currentPlayer = players.get(1 - players.indexOf(currentPlayer));
        }
        gui.setSelectedIntersection(selectedIntersection);
        gui.redraw();
    }
}
