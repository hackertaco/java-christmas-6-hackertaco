package christmas.model;

import static christmas.config.RuleConfig.MENU_DIVIDER;
import static christmas.config.RuleConfig.MIN_ORDER_COUNT;
import static christmas.utils.ErrorMessages.INVALID_ORDER;

import java.util.Arrays;


public class Order {
    private final Menu menu;
    private final int count;

    private Order(String menuName, String menuCount){
        menu = Arrays.stream(Menu.values()).filter(m->m.getName().equals(menuName)).findAny().get();
        count = Integer.parseInt(menuCount);
    }

    public static Order createOrder(String menuBeforeValidated){
            haveMenuFormat(menuBeforeValidated);
            canDivideInto(menuBeforeValidated);

            String menuName = menuBeforeValidated.split(MENU_DIVIDER)[0];
            String menuCount = menuBeforeValidated.split(MENU_DIVIDER)[1];

            validateMenu(menuName);
            validateMenuCount(menuCount);

            return new Order(menuName, menuCount);
    }

    public Menu getMenu(){
        return menu;
    }
    public int getCount(){
        return count;
    }

    public int getPrice(){
        return menu.getPrice() * count;
    }

    @Override
    public String toString(){
        return menu.getName()+ " " +count+"개";
    }

    private static void validateMenu(String menuNameBeforeValidated){
        if(!isAvailableMenu(menuNameBeforeValidated)){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }
    private static void haveMenuFormat(String menuBeforeValidated) {
        if(!menuBeforeValidated.contains(MENU_DIVIDER)){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }

    private static void canDivideInto(String menuBeforeValidated) {
        if(menuBeforeValidated.split(MENU_DIVIDER).length != 2){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }
    private static boolean isAvailableMenu(String menuNameBeforeValidated){
        return Arrays.stream(Menu.values())
                .anyMatch(menu -> menu.getName().equals(menuNameBeforeValidated));
    }

    private static void validateMenuCount(String menuCountBeforeValidated){
        checkZeroCount(parseStringToInt(menuCountBeforeValidated));
    }

    private static int parseStringToInt(String menuCountBeforeValidated){
        try {
            return Integer.parseInt(menuCountBeforeValidated);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }

    private static void checkZeroCount(int count){
        if(count < MIN_ORDER_COUNT){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }
}
