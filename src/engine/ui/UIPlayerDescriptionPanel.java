package engine.ui;

import engine.Player;

import javax.swing.*;
import java.awt.*;

public class UIPlayerDescriptionPanel extends JPanel {

    private Player player;
    private JLabel playerName;
    private JLabel playerColor;
    private JLabel piecesToPlace;
    private JLabel piecesInPlay;
    private JLabel piecesTaken;

    public UIPlayerDescriptionPanel(Player player) {
        super();

        this.player = player;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        playerName = new JLabel("Name: " + this.player.getName());
        playerColor = new JLabel("Colour: " + this.player.getPlayerColor().toString());
        // TODO: Fix implementation to show amount of pieces player has to place
        piecesToPlace = new JLabel("Pieces to place: " + 0);
        piecesInPlay = new JLabel("Pieces in play: " + 0);
        // TODO: Fix implementation to show amount of pieces player has had taken
        piecesTaken = new JLabel("Pieces to place: " + 0);

        this.add(playerName);
        this.add(playerColor);
        this.add(piecesToPlace);
        this.add(piecesInPlay);
        this.add(piecesTaken);

        playerName.setFont(new Font("Arial", Font.PLAIN, 18));
        playerColor.setFont(new Font("Arial", Font.PLAIN, 14));
        piecesToPlace.setFont(new Font("Arial", Font.PLAIN, 14));
        piecesInPlay.setFont(new Font("Arial", Font.PLAIN, 14));
        piecesTaken.setFont(new Font("Arial", Font.PLAIN, 14));

        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 20));
    }
}
