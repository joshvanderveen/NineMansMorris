package engine.ui;

import engine.Player;
import engine.board.GameBoard;
import engine.board.Intersection;
import engine.game.Game;
import engine.game.PieceListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UIMainGui extends JFrame {
    private UIBoardPanel UIBoardPanel;
    private JPanel buttons;

    public UIMainGui() {
        super("Nine Man's Morris");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        UIBoardPanel = new UIBoardPanel();

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
                            "- To select a piece that you want to move, click on a piece containing a piece. This will cause it to turn red when it is successfully selected\n"+
                            "- Once you have selected a piece, click on the position you want to move it to. This will cause the piece you selected to be moved to that position.\n" +
                            "- Player 1 goes first (BLUE), followed by Player 2 (GREEN)"
            );
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(UIBoardPanel,"Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                System.exit(0);
            }
        });

        buttons.add(rulesButton);
        buttons.add(controlsButton);
        buttons.add(exitButton);

        if (UIBoardPanel.getGameBoard() != null) {
            JPanel playerInfo = new JPanel();
            BoxLayout playerInfoBox = new BoxLayout(playerInfo, BoxLayout.Y_AXIS);
            playerInfo.setLayout(playerInfoBox);

            ArrayList<HashMap<String, Integer>> playerPieceInfo;
            playerPieceInfo = UIBoardPanel.getGameBoard().getPlayerPieceInfo();

            JTextArea player1Info = new JTextArea(
                    String.format("Player 1" +
                            "\nPieces in play:   %d" +
                            "\nPieces lost:      %d" +
                            "\nPieces to place:  %d", playerPieceInfo.get(0).get("placed"), playerPieceInfo.get(0).get("removed"), playerPieceInfo.get(0).get("unplaced"))
            );
            player1Info.setEditable(false);
            JTextArea player2Info = new JTextArea(
                    String.format("Player 2" +
                            "\nPieces in play:  %d" +
                            "\nPieces lost:     %d" +
                            "\nPieces to place: %d", playerPieceInfo.get(1).get("placed"), playerPieceInfo.get(1).get("removed"), playerPieceInfo.get(1).get("unplaced"))
            );
            player2Info.setEditable(false);

            playerInfo.add(player1Info);
            playerInfo.add(player2Info);

            this.add(playerInfo, BorderLayout.EAST);
        }

        this.add(UIBoardPanel, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.NORTH);

        // TODO: Implement properly
//        PlayerDescription player1 = new PlayerDescription(new HumanPlayer());
//        PlayerDescription player2 = new PlayerDescription(new ComputerPlayer());

        this.setVisible(true);
    }

    public void setGameBoard(GameBoard gameBoard) {
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
