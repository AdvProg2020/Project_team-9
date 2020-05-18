package view.menus;

public abstract class MenuChoice {
    private String name;
    private Menu parentMenu;

    public MenuChoice(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public abstract void onSelect();

    @Override
    public String toString() {
        return name;
    }
}
