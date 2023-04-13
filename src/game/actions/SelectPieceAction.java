package game.actions;

import engine.Player;
import engine.action.TurnAction;
import engine.board.Coordinate;
import engine.board.GameBoard;
import engine.game.Game;
import engine.action.Action;

public class SelectPieceAction extends TurnAction {
    @Override
    public String execute(Player player, GameBoard gameBoard) {
        // gameBoard.hasPlayersPiece(player, coordinate)
        return null;
    }

    @Override
    public String menuDescription(Player player) {
        return "Select piece to move";
    }
}
