package engine;

public abstract class Action {
    public abstract String execute(Actor actor, Game game);
    public abstract String menuDescription(Actor actor);
    public String menuKey() {
        return null;
    }
}
