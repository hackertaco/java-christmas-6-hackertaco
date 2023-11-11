package christmas.model;

import static christmas.config.RuleConfig.MAX_ORDER_COUNT;
import static christmas.utils.ErrorMessages.INVALID_ORDER;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderDetails {
    private static List<Order> orders = new ArrayList<>();

    public OrderDetails(List<String> ordersBeforeValidated){
        orders.clear();
        orders.addAll(ordersBeforeValidated.stream().map(Order::createOrder).toList());
        validateOrders();
    }

    private void validateOrders(){
        if(isMenuDuplicate() || existOnlyBeverage() || exceedMaxOrderCount()){
            System.out.println(isMenuDuplicate());
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }

    public static boolean isMenuDuplicate(){
        Set<Menu> uniqueMenus = orders.stream().map(Order::getMenu).collect(Collectors.toSet());
        return uniqueMenus.size() != orders.size();
    }
    public static boolean existOnlyBeverage(){
        List<Menu> menus = orders.stream().map(Order::getMenu).toList();

        return menus.stream().allMatch(MenuGroup.BEVERAGE::contains);
    }
    public static boolean exceedMaxOrderCount(){
        int orderCount = orders.stream()
                .mapToInt(Order::getCount)
                .sum();

        return orderCount > MAX_ORDER_COUNT;
    }

    public int countTotal(){
        return orders.stream().mapToInt(Order::getPrice).sum();
    }

    public int countTargetMenu(List<Menu> targetMenus){
        return (int) orders.stream()
                .map(Order::getMenu)
                .filter(targetMenus::contains)
                .count();
    }

    public List<String> getOrders(){
        return orders.stream().map(Order::toString).collect(Collectors.toList());
    }
}
