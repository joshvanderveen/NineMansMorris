package engine;

import engine.action.ActionList;
import engine.board.GameBoard;
import engine.action.Action;

import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected char displayChar;

    protected boolean isComputerControlled = false;

    public Player(String name, char displayChar) {
        this.name = name;
        this.displayChar = displayChar;
    }

    public Player(String name, char displayChar, boolean isComputerControlled) {
        this.name = name;
        this.displayChar = displayChar;
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
}
