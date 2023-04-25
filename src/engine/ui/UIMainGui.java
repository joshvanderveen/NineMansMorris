package engine.ui;

import engine.board.GameBoard;
import engine.board.Intersection;
import engine.game.Game;
import engine.game.PieceListener;

import javax.swing.*;
import java.awt.*;

public class UIMainGui extends JFrame {
    private UIBoardPanel UIBoardPanel;
    private JPanel buttons;
    private JPanel player;

    private GameBoard gameBoard;
    public UIMainGui() {
        super("Nine Man's Morris");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 800);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton rulesButton = new JButton("Rules");
        JButton controlsButton = new JButton(("Controls"));
        JButton exitButton = new JButton("Exit");
        rulesButton.setFocusPainted(false);
        controlsButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        rulesButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(UIBoardPanel,
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

        controlsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(UIBoardPanel,
                    "NINE MEN'S MORRIS - CONTROLS\n" +
                            "- To select a piece that you want to move, click on a piece. This will cause it to turn red when it is successfully selected\n"+
                            "- Once you have selected a piece, click on the position you want to move it to. This will cause the piece you selected to be moved to that position.\n"
            );
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(UIBoardPanel,"Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                System.exit(0);
            }
        });

        UIBoardPanel = new UIBoardPanel();

//        UIBoardPanel.addMouseListener(new BoardMouseListener(gameBoard));

        buttons.add(rulesButton);
        buttons.add(controlsButton);
        buttons.add(exitButton);

        this.add(UIBoardPanel, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.NORTH);

        // TODO: Implement properly
//        PlayerDescription player1 = new PlayerDescription(new HumanPlayer());
//        PlayerDescription player2 = new PlayerDescription(new ComputerPlayer());

//        this.add(player1, BorderLayout.SOUTH);
//        this.add(player2, BorderLayout.EAST);

        this.setVisible(true);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        UIBoardPanel.setGameBoard(gameBoard);
        redraw();
    }

    public void redraw() {
        repaint();
    }


    public void setSelectedIntersection(Intersection intersection) {
        UIBoardPanel.setSelectedIntersection(intersection);
        redraw();
    }

    public void addPieceListener(PieceListener pieceListener) {
         this.UIBoardPanel.addPieceListener(pieceListener);
    }
}
