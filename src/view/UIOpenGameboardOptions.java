package view;

public enum UIOpenGameboardOptions {
    EXISTING_BOARD("Open Existing Board"),
    NEW_BOARD("Create New Board"),
    DELETE_BOARD("Delete Existing Board");

    private final String name;

    private UIOpenGameboardOptions(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
