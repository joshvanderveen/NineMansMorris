package engine;

import java.awt.*;

public abstract class Player {
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

    public String getName() {
        return name;
    }

    public boolean isComputerControlled() {
        return isComputerControlled;
    }

    public Color getPlayerColor() {
        return this.playerColor;
    }
}
