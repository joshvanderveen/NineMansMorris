package engine.game;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.board.*;
import engine.ui.UIMainGui;
import game.actions.*;

import java.util.*;

public class Game implements PieceListener {

    int NUMBER_PIECES_EACH = 9;
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
        gui.addPieceListener(this);

        currentPlayer = players.get(0);
    }

//    public void run() {
//        gui.setGameBoard(this.gameBoard);
//    }

    @Override
    public void positionSelected(Intersection intersection) {
        // TODO: Place piece - implement in future sprints

        // If start of players turn or if they haven't played valid move
        if(selectedIntersection == null) {
            System.out.println("Select intersection");
            // If no intersection selected and selection doesn't have a piece, don't allow turn
            if (intersection.getPiece() == null) {
                System.out.println("No piece to select");
                return;
            };

            // If selected intersection is occupied with other players piece
            if (intersection.getPiece().getOwner() != currentPlayer) {
                System.out.println("Selected intersection has other players piece");
                return;
            };

            // Select intersection that has current players piece
            selectedIntersection = intersection;
        } else {
            // Select place to move
            // If place to move to is unoccupied
            if (intersection.getPiece() != null) {
                selectedIntersection = null;
                System.out.println("Selected destination intersection isn't empty");
                return;
            }

            System.out.println("Try move");

            // Try to make move
            if (!gameBoard.makeMove(currentPlayer, selectedIntersection.getPiece(), intersection)) {
                // If move is invalid, return and wait to pick next destination
                System.out.println("Invalid move");
                selectedIntersection = null;
                return;
            }

            System.out.println("Successful move");

            // Turn logic
            // Set current player to other player since there's only ever 2 players
            System.out.println("Player index: " + players.indexOf(currentPlayer));
            currentPlayer = players.get(1 - players.indexOf(currentPlayer));
            selectedIntersection = null;
        }
        gui.setSelectedIntersection(intersection);
        gui.redraw();
    }
}
