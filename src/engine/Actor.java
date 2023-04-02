package engine;

import java.util.ArrayList;

public abstract class Actor {
    protected String name;
    protected char displayChar;

    public Actor(String name, char displayChar) {
        this.name = name;
        this.displayChar = displayChar;
    }

    public String getName() {
        return name;
    }

    public abstract Action playTurn(ArrayList<Action> actions, GameBoard gameBoard);
}
