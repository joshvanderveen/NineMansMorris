package game.actions;

import engine.action.Action;
import engine.Player;
import engine.action.TurnAction;
import engine.board.GameBoard;
import engine.game.Game;

public class MoveAction extends TurnAction {
    public MoveAction() {
    }

    @Override
    public String execute(Player player, GameBoard gameBoard) {
//        Sudo code
//       if(gameBoard.isValidMove(player, piece, position)) {
//           gameBoard.executeMove(player, piece, position)
//       }
        return null;
    }


    @Override
    public String menuDescription(Player player) {
        return "Move a piece";
    }
}
