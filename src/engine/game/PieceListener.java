package engine.game;

import engine.board.Intersection;
import engine.board.Piece;

public interface PieceListener {

    /**
     * Called when an intersection is selected from the user interface.
     * @param intersection The intersection that was selected.
     */
    void positionSelected(Intersection intersection);
}
