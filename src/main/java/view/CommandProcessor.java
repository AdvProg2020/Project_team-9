package view;

import controller.Manager;
import view.menus.LoginAndRegisterMenu;
import view.menus.Menu;

import java.util.Scanner;

public class CommandProcessor {
    private static Manager manager;
    private static Scanner scanner;

    public static void show() {
        manager = new Manager();
        scanner = new Scanner(System.in);
        runWithMenu();
    }

    private static void runWithMenu() {
        Menu.setScanner(scanner);
        Menu.setManager(manager);
        Menu currentMenu = new LoginAndRegisterMenu(null);
        currentMenu.show();
        currentMenu.execute();
    }

}
