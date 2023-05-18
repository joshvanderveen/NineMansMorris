package view.piece;

import model.board.Piece;

import java.awt.*;

public class UIBoardPositionFull extends UIBoardPosition {
    private Piece piece;
    private boolean isSelected;
    private boolean inMill;

    public UIBoardPositionFull(int x, int y, Piece piece, boolean isSelected, boolean inMill) {
        super(x, y);
        this.piece = piece;
        this.isSelected = isSelected;
        this.inMill = inMill;
        this.radius = this.radius * 4;
    }

    @Override
    public void draw(Graphics2D graphics) {
        // if is in mill
        int originalRadius = this.radius;

        if (inMill) {
            graphics.setColor(new Color(97, 201, 168));
            this.radius = (int) (this.radius + 0.5 * radius);
            super.draw(graphics);
            this.radius = originalRadius;
        }

        graphics.setColor(isSelected ? this.selectedColor : piece.getOwner().getPlayerColor());
        super.draw(graphics);
    }
}
