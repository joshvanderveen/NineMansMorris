package game.actors;

import engine.Player;

import java.awt.*;

public class HumanPlayer extends Player {
    private static final String DEFAULT_NAME = "PLAYER";
    public static final Color DEFAULT_COLOR = Color.BLUE;

    public HumanPlayer() {
        super(DEFAULT_NAME, DEFAULT_COLOR);
    }

    public HumanPlayer(String name) {
        super(name, DEFAULT_COLOR);
    }

    public HumanPlayer(String name, Color playerColor) {
        super(name, playerColor, false);
    }
}