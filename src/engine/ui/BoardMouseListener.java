package engine.ui;

import engine.board.GameBoard;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardMouseListener extends MouseAdapter {
    public BoardMouseListener() {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Point clicked = e.getPoint();
//        gameBoard.mouseClicked(Point);
    }
}
