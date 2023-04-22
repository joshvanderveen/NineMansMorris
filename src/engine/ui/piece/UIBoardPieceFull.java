package engine.ui.piece;

import engine.board.Piece;

public class UIBoardPieceFull extends UIBoardPiece {
    Piece piece;
    public UIBoardPieceFull(int x, int y, Piece piece) {
        super(x, y);
        this.piece = piece;
    }
}
