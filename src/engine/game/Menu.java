package engine.game;

import engine.Player;
import engine.action.Action;
import engine.action.ActionList;
import engine.action.MenuAction;
import engine.board.GameBoard;

import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String, Action> menuList = new HashMap<>();
        HashMap<String, Action> turnList = new HashMap<>();

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

        menuList.forEach((String key, Action action) -> {
            this.printAction(key, action, player);
        });

        gameBoard.printBoard();

        turnList.forEach((String key, Action action) -> {
            this.printAction(key, action, player);
        });

        String key;

        do {
            key = InputManager.getInstance().readInput();
        } while (!menuList.containsKey(key));

        return menuList.get(key);
    }

    private void printAction(String menuCharacter, Action action, Player player) {
        System.out.println(menuCharacter + " - " + action.menuDescription(player));
    }
}