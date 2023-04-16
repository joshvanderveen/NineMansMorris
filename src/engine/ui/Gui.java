package engine.ui;

import engine.board.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private Board board;
    private JPanel buttons;

    private GameBoard gameBoard;
    public Gui() {
        super("Nine Man's Morris");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 900);

        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton helpButton = new JButton("Help");
        JButton exitButton = new JButton("Exit");

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Help pressed");
                return;
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit pressed ");
                return;
            }
        });

        board = new Board();

        buttons.add(helpButton);
        buttons.add(exitButton);

        add(board, BorderLayout.CENTER);
        add(buttons, BorderLayout.NORTH);

        this.setVisible(true);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        board.setGameBoard(gameBoard);

        repaint();
    }
}
