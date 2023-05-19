package model;

import java.awt.*;

public class Player {
    protected String name;
    protected Color playerColor;


    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

    /**
     * Gets the players name
     * @return the players name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the players color
     * @return the players color
     */
    public Color getPlayerColor() {
        return this.playerColor;
    }
}
