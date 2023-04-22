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
    protected ArrayList<Player> players = new ArrayList<>();
    private Intersection selectedIntersection;

    private Player currentPlayer;

    public Game(GameBoard gameBoard, UIMainGui gui) {
        this.gameBoard = gameBoard;
        this.gui = gui;
        gui.addPieceListener(this);
    }

    @Override
    public void positionSelected(Intersection intersection) {
        // TODO: Place piece - impliment in future sprints

        // If start of players turn or if they haven't played valid move
        if(selectedIntersection == null) {
            // If no intersection selected and selection doesn't have a piece, don't allow turn
            if (intersection.getPiece() == null) return;

            // If selected intersection is occupied with other players piece
            if (intersection.getPiece().getOwner() != currentPlayer) return;

            // Select intersection that has current players piece
            selectedIntersection = intersection;
        } else {
            // Select place to move
            // If place to move to is unoccupied
            if (selectedIntersection.getPiece() != null) return;

            // Try to make move
            if (!gameBoard.makeMove(currentPlayer, selectedIntersection.getPiece(), intersection)) {
                // If move is invalid, return and wait to pick next destination
                return;
            }

            // Turn logic
            // Set current player to other player since there's only ever 2 players
            currentPlayer = players.get(3 - players.indexOf(currentPlayer));
            selectedIntersection = null;
        }
    }
}
