package engine.ui.piece;

import java.awt.*;

public class UIBoardPositionEmpty extends UIBoardPosition {
    public UIBoardPositionEmpty(int x, int y) {
        super(x, y);
    }
    public UIBoardPositionEmpty(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        super.draw(graphics);
    }


}
