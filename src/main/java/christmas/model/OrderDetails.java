package christmas.model;

import static christmas.config.RuleConfig.MAX_ORDER_COUNT;
import static christmas.config.RuleConfig.NOT_PERMITTED_MENUGROUP;
import static christmas.utils.ErrorMessages.INVALID_ORDER;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderDetails {
    private final List<Order> orders = new ArrayList<>();

    public OrderDetails(List<String> ordersBeforeValidated){
        orders.addAll(ordersBeforeValidated.stream().map(Order::createOrder).toList());
        validateOrders();
    }

    public int countTotal(){
        return orders.stream().mapToInt(Order::getPrice).sum();
    }

    public int countTargetMenu(List<Menu> targetMenus){
        return orders.stream()
                .filter(order -> targetMenus.contains(order.getMenu()))
                .mapToInt(Order::getCount)
                .sum();
    }

    public List<String> getOrders(){
        return orders.stream().map(Order::toString).collect(Collectors.toList());
    }

    private void validateOrders(){
        if(isMenuDuplicate() || existOnlyNotPermittedGroup() || exceedMaxOrderCount()){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }

    private boolean isMenuDuplicate(){
        Set<Menu> uniqueMenus = orders.stream().map(Order::getMenu).collect(Collectors.toSet());
        return uniqueMenus.size() != orders.size();
    }

    private boolean existOnlyNotPermittedGroup(){
        List<Menu> menus = orders.stream().map(Order::getMenu).toList();
        return menus.stream().allMatch(MenuGroup.valueOf(NOT_PERMITTED_MENUGROUP)::contains);
    }

    private boolean exceedMaxOrderCount(){
        int orderCount = orders.stream()
                .mapToInt(Order::getCount)
                .sum();

        return orderCount > MAX_ORDER_COUNT;
    }
}
