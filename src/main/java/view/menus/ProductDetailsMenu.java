package view.menus;

import java.util.HashMap;

public class ProductDetailsMenu extends Menu {
    public ProductDetailsMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("View Main Categories", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewMainCategoriesCommand()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
    }

    @Override
    protected void showHelp() {

    }
}
