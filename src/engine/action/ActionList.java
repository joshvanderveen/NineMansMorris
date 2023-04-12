package engine.action;

import java.util.*;

public class ActionList implements Iterable<Action> {
    private ArrayList<Action> actions = new ArrayList<>();

    public ActionList() {}

    public ActionList(Action action) {
        add(action);
    }

    public ActionList(ActionList actions) {
        add(actions);
    }

    @Override
    public Iterator<Action> iterator() {
        return Collections.unmodifiableList(actions).iterator();
    }

    public void add(Action action) {
        actions.add(action);
    }

    public void add(ActionList actions) {
        for (Action action : actions) this.add(action);
    }

    public Action get(int index) {
        return actions.get(index);
    }

    public void remove(int index) throws IllegalArgumentException {
        if (this.size() < index) throw new IllegalArgumentException("Index out of range")
        actions.remove(index);
    }

    public void remove(Action action) throws IllegalArgumentException {
        if (!actions.contains(action)) throw new IllegalArgumentException("Action not in ActionList");
        actions.remove(action);
    }

    public int size() {
        return actions.size();
    }
}
