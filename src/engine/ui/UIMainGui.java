package engine.ui;

import engine.Player;
import engine.board.GameBoard;
import engine.board.Intersection;
import engine.game.PieceListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIMainGui extends JFrame {
    private UIBoardPanel UIBoardPanel;
    private JPanel buttons;
    private GameBoard gameBoard;
    private JPanel playerInfoContainer;

    private Player currentPlayer;

    public UIMainGui() {
        super("Nine Man's Morris");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);

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

        buttons.setBackground(Color.WHITE);

        playerInfoContainer = new JPanel();
        BoxLayout playerInfoBox = new BoxLayout(playerInfoContainer, BoxLayout.Y_AXIS);
        playerInfoContainer.setLayout(playerInfoBox);

//         playerInfoContainer.add(new JScrollPane());

        this.add(UIBoardPanel, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.NORTH);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        UIBoardPanel.setGameBoard(gameBoard);
        redraw();
    }

    public void setPlayers(ArrayList<Player> players) {
        for (Player player : players) {
            UIPlayerDescriptionPanel playerDescription = new UIPlayerDescriptionPanel(player);
            playerInfoContainer.add(playerDescription);
        }
        UICurrentPlayerLabel currentPlayerPanel = new UICurrentPlayerLabel();
        playerInfoContainer.add(currentPlayerPanel);
        playerInfoContainer.setBackground(Color.WHITE);
        this.add(playerInfoContainer, BorderLayout.EAST);
    }

    public void redraw() {
        for (Component c : playerInfoContainer.getComponents()) {
            if (c.getClass() != UIPlayerDescriptionPanel.class) continue;

            Player player = ((UIPlayerDescriptionPanel) c).getPlayer();
            UIPlayerDescriptionPanel panel = (UIPlayerDescriptionPanel) c;

            panel.setUnplacedPiecesAmount(gameBoard.getUnplacedPieces(player).size());
            panel.setPlacedPiecesAmount(gameBoard.getPlacedPieces(player).size());
            panel.setRemovedPiecesAmount(gameBoard.getRemovedPieces(player).size());
        }

        for (Component c : playerInfoContainer.getComponents()) {
            if (c.getClass() != UICurrentPlayerLabel.class) continue;
            UICurrentPlayerLabel panel = (UICurrentPlayerLabel) c;
            panel.updatePlayer(currentPlayer);
        }

        repaint();
    }


    public void setSelectedIntersection(Intersection intersection) {
        UIBoardPanel.setSelectedIntersection(intersection);
        redraw();
    }

    public void addPieceListener(PieceListener pieceListener) {
         this.UIBoardPanel.addPieceListener(pieceListener);
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        redraw();
    }
}
