package christmas;

import java.util.Arrays;
import java.util.List;

public enum MenuGroup {
    APPETIZER(Arrays.asList(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CEASER_SALAD)),
    MAIN(Arrays.asList(Menu.TBONE_STEAK, Menu.BARBEQUE_RIB, Menu.MARINA_PASTA, Menu.CHRISTMAS_PASTA)),
    DESSERT(Arrays.asList(Menu.CHOCOLATE_CAKE, Menu.ICECREAM)),
    BEVERAGE(Arrays.asList(Menu.ZERO_COKE, Menu.RED_WINE, Menu.CHAMPAGNE));

    private List<Menu> menuGroup;

    MenuGroup(List<Menu> menuGroup){
        this.menuGroup = menuGroup;
    }

    public List<Menu> getMenuGroup(){
        return menuGroup;
    }
}
