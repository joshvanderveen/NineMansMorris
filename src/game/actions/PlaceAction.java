package game.actions;

import engine.action.Action;
import engine.Player;
import engine.action.TurnAction;
import engine.board.GameBoard;
import engine.game.Game;

public class PlaceAction extends TurnAction {
    @Override
    public String execute(Player player, GameBoard gameBoard) {
        // gameBoard.isPlaceEmpty(coordinate);
        return null;
    }

    @Override
    public String menuDescription(Player player) {
        return "Place a piece";
    }
}
