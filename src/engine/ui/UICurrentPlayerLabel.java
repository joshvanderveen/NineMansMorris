package engine.ui;

import engine.Player;

import javax.swing.*;
import java.awt.*;

public class UICurrentPlayerLabel extends JLabel {
    public UICurrentPlayerLabel() {
        super("");

        this.setFont(new Font("Roboto", Font.BOLD, 20));
        this.setForeground(Color.BLACK);

        this.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 20));
    }

    /**
     * Updates the current player label with the new player's name and color
     * @param player The player to update the label with
     */
    public void updatePlayer(Player player) {
        if (player == null) return;
        this.setText(player.getName() + "'s turn!");
        this.setForeground(player.getPlayerColor());
    }
}
