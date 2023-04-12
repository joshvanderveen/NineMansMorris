package engine.game;

import engine.Actor;
import engine.action.Action;
import engine.action.ActionList;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    public Action displayMenu(Actor actor, ActionList actions) {
        ArrayList<Character> freeCharacters = new ArrayList<>();
        HashMap<String, Action> menuList = new HashMap<>();

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
            menuList.put(checkedMenuKey, action);

            this.printAction(checkedMenuKey, action, actor);
        }

        String key;

        InputManager input = new InputManager();

        do {
            key = input.readInput();
        } while (!menuList.containsKey(key));

        return menuList.get(key);
    }

    public void printAction(String menuCharacter, Action action, Actor actor) {
        System.out.println(menuCharacter + " - " + action.menuDescription(actor));
    }
}
