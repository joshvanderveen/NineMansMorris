package controller;

import model.MillManager;
import model.Player;
import model.Mill;
import model.board.*;
import view.UIMainGui;

import java.util.*;

public class Game implements PieceListener {
    protected GameBoard gameBoard;
    protected UIMainGui gui;
    protected ArrayList<Player> players;
    private Intersection selectedIntersection = null;
    private Player currentPlayer;


    public Game(GameBoard gameBoard, UIMainGui gui, ArrayList<Player> players) {
        this.players = players;
        this.gameBoard = gameBoard;
        this.gui = gui;
        gui.setPlayers(this.players);
        gui.setGameBoard(this.gameBoard);
        gui.addPieceListener(this);
        currentPlayer = players.get(0);
        gui.setCurrentPlayer(currentPlayer);

        gui.setVisible(true);

    }

    /**
     * Main method that takes care of Game logic and splits up tasks depending on game status
     *
     * @param intersection - input selected intersection from UI
     */
    @Override
    public void positionSelected(Intersection intersection) {

        // Check if removing stage
        if (MillManager.playerHasUnusedMill(currentPlayer)) {
            handleRemovingStage(intersection);
            checkGameOver();
            return;
        }

        // Check if placing stage
        if (gameBoard.getUnplacedPieces(currentPlayer).size() > 0) {
            handlePlacingStage(intersection);
            return;
        }

        // Check if selecting stage
        if (selectedIntersection == null) {
            handleSelectingStage(intersection);
            return;
        }

        // Check if moving stage
        if (selectedIntersection != null){
            handleMovingStage(intersection);
            checkGameOver();
            return;
        }
    }

    private void handleRemovingStage(Intersection intersection) {
        // Check if intersection has a piece
        if (intersection.getPiece() == null) return;
        // Check if piece belongs to current player
        if (intersection.getPiece().getOwner() == currentPlayer) return;
        // get mills, then update
        ArrayList<Mill> mills = gameBoard.checkForMills();
        MillManager.updateMills(mills);

        boolean areFreePieces = false;

        for (Intersection _intersection : gameBoard.getIntersections()) {
            if (_intersection.getPiece() == null) continue;
            if (_intersection.getPiece().getOwner() != currentPlayer) continue;
            if (!MillManager.intersectionIsInMill(_intersection)) {
                areFreePieces = true;
            }
        }

        if (areFreePieces) {
            if (MillManager.intersectionIsInMill(intersection)) return;
        }

        // Remove piece
        gameBoard.removeFromBoard(intersection.getPiece());

        // use mill
        MillManager.useMill(currentPlayer);

        gui.redraw();
        checkGameOver();
        setNextPlayer();
    }

    private void handlePlacingStage(Intersection intersection) {
        // Check there is an intersection selected
        if (intersection == null) return;
        // check if intersection already has a piece
        if (intersection.getPiece() != null) return;
        // place piece
        gameBoard.placePiece(gameBoard.getUnplacedPieces(currentPlayer).get(0), intersection);

        ArrayList<Mill> mills = gameBoard.checkForMills();
        MillManager.updateMills(mills);

        if (mills.size() > 0) {

            gui.redraw();

            if (MillManager.playerHasUnusedMill(currentPlayer)) {
                gui.redraw();
                gui.notifyOfMill();
                return;
            }

        }

        setNextPlayer();
        gui.redraw();

    }

    private void handleSelectingStage(Intersection intersection) {
        // Check if intersection has a piece
        if (intersection.getPiece() == null) return;
        // Check if piece belongs to current player
        if (intersection.getPiece().getOwner() != currentPlayer) return;
        // Select piece
        selectedIntersection = intersection;
        gui.setSelectedIntersection(selectedIntersection);
        gui.redraw();
    }

    private void handleMovingStage(Intersection intersection) {
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
        gui.setSelectedIntersection(selectedIntersection);

        ArrayList<Mill> mills = gameBoard.checkForMills();
        MillManager.updateMills(mills);

        if (mills.size() > 0) {

            gui.redraw();

            if (MillManager.playerHasUnusedMill(currentPlayer)) {
                gui.notifyOfMill();
                return;
            }

            setNextPlayer();
            return;
        }

        gui.redraw();
        setNextPlayer();
    }

    /**
     * Sets the currentPlayer to the next one
     *
     * @see Game#currentPlayer
     */
    public void setNextPlayer() {
        // Set current player to other player since there's only ever 2 players
        currentPlayer = players.get(1 - players.indexOf(currentPlayer));
        gui.setCurrentPlayer(currentPlayer);
    }

    /**
     * Checks whether the game is over, and notifies the UI if it is
     */
    public void checkGameOver() {
        if (gameBoard.isGameOver(currentPlayer)) {
            System.out.println("Player " + currentPlayer + " has lost!");
            gui.notifyOfWin(players.get(1 - players.indexOf(currentPlayer)));
            System.exit(0);
        }
    }
}
