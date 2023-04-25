package engine.game;

import engine.board.Intersection;
import engine.board.Piece;

public interface PieceListener {
    void positionSelected(Intersection intersection);
}
