package view.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected HashMap<Integer, Menu> submenus;
    protected Menu parentMenu;
    public static Scanner scanner;
    protected static ArrayList<Menu> allMenus;

    static {
        allMenus = new ArrayList<>();
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        allMenus.add(this);
    }

    public String getName() {
        return name;
    }

    public void setSubMenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    public void execute() {
        Menu nextMenu;
        int validSizeLimit = submenus.size() + 1;
        int chosenMenu = getNextMenu(validSizeLimit);
        if (chosenMenu == validSizeLimit) {
            return;
        } else
            nextMenu = submenus.get(chosenMenu);
        if (nextMenu != null) {
            nextMenu.show();
            nextMenu.execute();
        }
    }

    private int getNextMenu(int validSizeLimit) {
        int chosenMenu = validSizeLimit + 1;
        String input;
        while (chosenMenu > validSizeLimit) {
            input = scanner.nextLine();
            if (!input.matches("\\d+"))
                continue;
            chosenMenu = Integer.parseInt(input);
        }
        return chosenMenu;
    }

    protected abstract void showHelp();
}
