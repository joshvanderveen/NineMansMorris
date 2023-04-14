package engine.game;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.action.MenuAction;
import engine.board.GameBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Menu {

    private static Menu instance;

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public Action displayMenu(Player player, ActionList actions, GameBoard gameBoard) {
        ArrayList<Character> freeCharacters = new ArrayList<>();
        TreeMap<String, Action> menuList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        TreeMap<String, Action> turnList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (char i = 'a'; i <= 'z'; i++)
            freeCharacters.add(i);

        for (Action action : actions) {
            String menuKey = action.menuKey();

            String checkedMenuKey;

            if (menuKey == null || menuKey == "") {
                checkedMenuKey = freeCharacters.get(0).toString();
            } else {
                checkedMenuKey = menuKey;
            }

            freeCharacters.remove(0);

            // TODO: instanceof not good practice, should change
            if (action instanceof MenuAction) {
                menuList.put(checkedMenuKey, action);
            } else {
                turnList.put(checkedMenuKey, action);
            }
        }

        // Print menu options

        System.out.println("NINE MANS MORRIS");

        menuList.forEach((String key, Action action) -> {
            this.printAction(key, action, player);
        });

        // TODO: Implement properly
        System.out.println("");
        System.out.println("Player 1");
        System.out.println("Pieces");

        gameBoard.printBoard();

        // TODO: Implement properly
        System.out.println("Player 1");
        System.out.println("Pieces");
        System.out.println("");

        turnList.forEach((String key, Action action) -> {
            this.printAction(key, action, player);
        });

        System.out.print("\n");

        String keyInput;

        do {
            keyInput = InputManager.getInstance().readInput();
        } while (!menuList.containsKey(keyInput.toLowerCase()));

        return menuList.get(keyInput);
    }

    private void printAction(String menuCharacter, Action action, Player player) {
        System.out.println(menuCharacter + " - " + action.menuDescription(player));
            }

    public void print(String result) {
        System.out.println(result);
    }
}