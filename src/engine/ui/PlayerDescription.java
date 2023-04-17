package engine.ui;

import engine.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerDescription extends JPanel {

    private Player player;
    private JLabel playerName;
    private JLabel playerColor;
    private JLabel piecesToPlace;
    private JLabel piecesTaken;

    public PlayerDescription(Player player) {
        super();

        this.player = player;

        this.setLayout(new FlowLayout());

        playerName = new JLabel("Name: " + this.player.getName());
        playerColor = new JLabel("Colour: " + this.player.getPlayerColor().toString());
        // TODO: Fix implementation to show amount of pieces player has to place
        piecesToPlace = new JLabel("Pieces to place: " + 0);
        // TODO: Fix implementation to show amount of pieces player has had taken
        piecesTaken = new JLabel("Pieces to place: " + 0);

        this.add(playerName);
        this.add(playerColor);
        this.add(piecesToPlace);
        this.add(piecesTaken);
    }
}
