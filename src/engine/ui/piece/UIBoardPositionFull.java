package engine.ui.piece;

import engine.board.Piece;

import java.awt.*;

public class UIBoardPositionFull extends UIBoardPosition {
    private Piece piece;
    private boolean isSelected = false;
    public UIBoardPositionFull(int x, int y, Piece piece, boolean isSelected) {
        super(x, y);
        this.piece = piece;
        this.isSelected = isSelected;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(isSelected ? this.selectedColor : piece.getOwner().getPlayerColor());
        this.radius = this.radius * 3;
        super.draw(graphics);
    }
}
