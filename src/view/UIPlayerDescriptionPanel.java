package view;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class UIPlayerDescriptionPanel extends JPanel {

    private Player player;
    private JLabel playerName;
    private JLabel piecesUnplaced;
    private JLabel piecesPlaced;
    private JLabel piecesRemoved;

    String UNPLACED_PIECES_PREFIX = "Pieces to place: ";
    String PIECES_IN_PLAY_PREFIX = "Pieces in play: ";
    String PIECES_REMOVED_PREFIX = "Pieces removed: ";

    public UIPlayerDescriptionPanel(Player player) {
        super();

        this.player = player;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        playerName = new JLabel("Name: " + this.player.getName());
        // TODO: Fix implementation to show amount of pieces player has to place
        piecesUnplaced = new JLabel(UNPLACED_PIECES_PREFIX + 0);
        piecesPlaced = new JLabel(PIECES_IN_PLAY_PREFIX + 0);
        // TODO: Fix implementation to show amount of pieces player has had taken
        piecesRemoved = new JLabel(PIECES_REMOVED_PREFIX + 0);

        this.add(playerName);
        this.add(piecesUnplaced);
        this.add(piecesPlaced);
        this.add(piecesRemoved);

        playerName.setFont(new Font("Roboto", Font.BOLD, 20));
        playerName.setForeground(player.getPlayerColor());
        piecesUnplaced.setFont(new Font("Roboto", Font.PLAIN, 16));
        piecesPlaced.setFont(new Font("Roboto", Font.PLAIN, 16));
        piecesRemoved.setFont(new Font("Roboto", Font.PLAIN, 16));

        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 20));
    }

    /**
     * Gets the player that this panel is displaying details for
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Sets the amount of pieces that the player still has to place
     * @param amount the amount of pieces
     */
    public void setUnplacedPiecesAmount(int amount) {
        this.piecesUnplaced.setText(UNPLACED_PIECES_PREFIX + amount);
    }

    /**
     * Sets the amount of pieces that the player has placed
     * @param amount the amount of pieces
     */
    public void setPlacedPiecesAmount(int amount) {
        this.piecesPlaced.setText(PIECES_IN_PLAY_PREFIX + amount);
    }

    /**
     * Sets the amount of pieces that the player has had taken
     * @param amount the amount of pieces
     */
    public void setRemovedPiecesAmount(int amount) {
        this.piecesRemoved.setText(PIECES_REMOVED_PREFIX + amount);
    }
 }
