package view.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    public static Scanner scanner;
    protected static ArrayList<Menu> allMenus;

    static {
        allMenus = new ArrayList<>();
    }

    protected HashMap<Integer, Menu> subMenus;
    protected HashMap<Integer, MenuChoice> choices;
    protected Menu parentMenu;
    private String name;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        allMenus.add(this);
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getName() {
        return name;
    }

    public void setSubMenus(HashMap<Integer, Menu> submenus) {
        this.subMenus = submenus;
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 1) + ". Back");
        //else
            //System.out.println((subMenus.size() + 1) + ". Exit");
    }

    public void execute() {
        Menu nextMenu;
        int validSizeLimit = subMenus.size() + 1;
        int chosenMenu = getNextMenu(validSizeLimit);
        if (chosenMenu == validSizeLimit) {
            return;
        } else
            nextMenu = subMenus.get(chosenMenu);
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

    public String getString() {
        return scanner.nextLine();
    }

    public String getStringWithPattern(String pattern, String messageInCaseOfRepeat) {
        String input = getString();
        while (!input.matches(pattern)) {
            showString(messageInCaseOfRepeat);
            input = getString();
        }
        return input;
    }

    public int getInt() {
        String input = getStringWithPattern("\\d{1, 9}",
                "Please enter a valid integer less than 1,000,000,000.");
        return Integer.parseInt(input);
    }

    public void showString(String string) {
        System.out.println(string);
    }

    public void showString(Object o) {
        showString(o.toString());
    }

    protected abstract void showHelp();
}
