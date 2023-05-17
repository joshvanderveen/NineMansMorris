package engine;

import java.awt.*;

public class Player {
    protected String name;
    protected Color playerColor;
    protected boolean isComputerControlled = false;

    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

    public Player(String name, Color playerColor, boolean isComputerControlled) {
        this.name = name;
        this.playerColor = playerColor;
        this.isComputerControlled = isComputerControlled;
    }

    /**
     * Gets the players name
     * @return the players name
     */
    public String getName() {
        return name;
    }

    public Color getPlayerColor() {
        return this.playerColor;
    }
}
