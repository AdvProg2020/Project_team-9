package view;

import view.menus.LoginAndRegisterMenu;
import view.menus.Menu;

import java.util.Scanner;

public class CommandProcessor {
    private static Scanner scanner;

    public static void show() {
        scanner = new Scanner(System.in);
        runWithMenu();
    }

    private static void runWithMenu() {
        Menu.setScanner(scanner);
        Menu currentMenu = new LoginAndRegisterMenu(null);
        currentMenu.show();
        currentMenu.execute();
    }

}
