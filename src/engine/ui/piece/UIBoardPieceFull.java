package engine.ui.piece;

import engine.board.Piece;

public class UIBoardPieceFull extends UIBoardPiece {
    private Piece piece;
    public UIBoardPieceFull(int x, int y, Piece piece) {
        super(x, y);
        this.piece = piece;
    }
}
