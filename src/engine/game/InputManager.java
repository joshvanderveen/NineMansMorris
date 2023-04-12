package engine.game;

import java.util.Scanner;

public class InputManager {
    private Scanner input = new Scanner(System.in);

    public String readInput() {
        return input.next();
    }

    public boolean isValidCoordinate(String coordinate) {
        if (coordinate.length() != 2) return false;

        if (!isInAlphabet(coordinate.charAt(0))) return false;

        if (!isNumberValue(coordinate.charAt(1))) return false;

        return true;
    }

    public boolean isInAlphabet(char character) {
        for (char i = 'a'; i <= 'z'; i++) {
            if (character == i) return true;
        }
        return false;
    }

    public boolean isNumberValue(String character) {
        if (character == null) return false;

        try {
            Double.parseDouble(character);
        } catch (NumberFormatException err) {
            return false;
        }
        return true;
    }

    public boolean isNumberValue(char character) {
        try {
            Double.parseDouble(String.valueOf(character));
        } catch (NumberFormatException err) {
            return false;
        }
        return true;
    }
}
