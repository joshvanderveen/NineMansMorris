package game.actions;

import engine.action.Action;
import engine.Player;
import engine.action.TurnAction;
import engine.board.GameBoard;
import engine.board.Piece;
import engine.game.Game;

import java.awt.*;

public class MoveAction extends TurnAction {
    public MoveAction(Player player, Piece piece, Point point) {
    }

    @Override
    public String execute(Player player, GameBoard gameBoard) {
        // gameboard.hasNeighbour(coordinate) - either 1 or 2 adjacent
        // gameboard.canMove(coordinate)
        // gameboard.moveTo(coordinate)

        return null;
    }


    @Override
    public String menuDescription(Player player) {
        return "Move a piece";
    }
}