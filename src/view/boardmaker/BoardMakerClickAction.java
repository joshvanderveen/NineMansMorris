package view.boardmaker;

public enum BoardMakerClickAction {
    NOTHING("Do Nothing"),
    DRAW_INTERSECTION("Draw Intersection"),
    DRAW_LINE("Draw Line"),
    ERASE_INTERSECTION("Erase Intersection");

    private final String name;

    private BoardMakerClickAction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
