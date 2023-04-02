package game.actions;

import engine.Action;
import engine.Actor;
import engine.board.Node;

public class SlideAction extends Action {

    Node destination;

    String menuKey;

    public SlideAction(Node destinationNode, String menuKey) {
        this.destination = destinationNode;
        this.menuKey = menuKey;
    }

    @Override
    public String execute(Actor actor) {
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.getName() + " moved to " + this.destination.getFriendlyLocation();
    }
}
