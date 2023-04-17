package engine.ui;

import engine.Player;
import engine.board.GameBoard;
import game.actors.ComputerPlayer;
import game.actors.HumanPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private Board board;
    private JPanel buttons;

    private JPanel player;

    private GameBoard gameBoard;
    public Gui() {
        super("Nine Man's Morris");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 800);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton rulesButton = new JButton("Rules");
        JButton exitButton = new JButton("Exit");
        rulesButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        rulesButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(board,
                    "NINE MEN'S MORRIS - RULES\n" +
                            "- Each player starts with 9 pieces.\n" +
                            "- In the first phase of the game, players take turns to place their pieces on the board\n" +
                            "- Once you have placed all of your pieces, you can begin to move them, sliding them to an empty adjacent intersection.\n" +
                            "- When placing and moving your pieces, your aim is to form Mills. A Mill is a straight row of 3 of your pieces.\n" +
                            "- When you form a Mill, you can remove one of your opponent’s pieces from the board. The piece you remove cannot be part of an existing Mill.\n" +
                            "- Once a piece has been removed from the board, it cannot be played again.\n" +
                            "- If you only have 3 pieces left, you can jump your pieces. This means that on your turn, rather than sliding your pieces, you can move them to any free intersection on the board.\n" +
                            "- Your aim is to leave your opponent with less than 3 pieces on the board, or no legal moves on their turn. If you successfully achieve either of these conditions, you win the game.\n"
            );
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(board,"Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                System.exit(0);
            }
        });

        board = new Board();

        buttons.add(rulesButton);
        buttons.add(exitButton);

        this.add(board, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.NORTH);

        // TODO: Implement properly
//        PlayerDescription player1 = new PlayerDescription(new HumanPlayer());
//        PlayerDescription player2 = new PlayerDescription(new ComputerPlayer());

//        this.add(player1, BorderLayout.WEST);
//        this.add(player2, BorderLayout.EAST);

        this.setVisible(true);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        board.setGameBoard(gameBoard);

        repaint();
    }
}
