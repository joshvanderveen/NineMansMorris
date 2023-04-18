package engine;

import engine.action.ActionList;
import engine.board.GameBoard;
import engine.action.Action;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected char displayChar;

    protected Color playerColor;
    protected boolean isComputerControlled = false;

    public Player(String name, char displayChar, Color playerColor) {
        this.name = name;
        this.displayChar = displayChar;
        this.playerColor = playerColor;
    }

    public Player(String name, char displayChar, Color playerColor, boolean isComputerControlled) {
        this.name = name;
        this.displayChar = displayChar;
        this.playerColor = playerColor;
        this.isComputerControlled = isComputerControlled;
    }

    public String getName() {
        return name;
    }

    public boolean isComputerControlled() {
        return isComputerControlled;
    }

    public abstract Action playTurn(ActionList actions, GameBoard gameboard);

    public char getDisplayChar() {
        return displayChar;
    }

    public Color getPlayerColor() {
        return this.playerColor;
    }
}
