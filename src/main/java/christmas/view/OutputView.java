package christmas.view;

import static christmas.config.RuleConfig.GIVING_EVENT_PRODUCT;
import static christmas.config.RuleConfig.TARGET_MONTH;
import static christmas.utils.Messages.*;

import christmas.model.Menu;
import christmas.model.Reservation;
import christmas.utils.Printer;
import java.util.List;


public class OutputView {
    private final Printer printer;


    public OutputView(Printer printer) {
        this.printer = printer;
    }

    public void printError(String errorMessage){
        String errorPrefix = "[ERROR] ";
        printer.print(errorPrefix + errorMessage);
    }

    public void printWithArguments(String message, Object... args){
        printer.printf(message, args);
    }

    public void showMenu(List<String> menus){
        printer.print(MENU_HEADER);
        for(String menu: menus){
            printer.print(menu);
        }
        addLineForReadability();
    }
    public void showTotal(int totalPrice){
        printer.print(TOTAL_ORDER_AMOUNT_HEADER);
        printer.printf(String.valueOf(totalPrice));
        addLineForReadability();
    }
    public void showGift(Reservation reservation){
        printer.print(GIFT_MENU_HEADER);
        if(reservation.isEligibleForGivingEvent()){
            printer.printf(GIFT_DETAIL, Menu.valueOf(GIVING_EVENT_PRODUCT).getName(), 1);
            return;
        }
        printer.print(NO_GIFT_MESSAGE);
        addLineForReadability();
    }
    public void showTotalEventList(Reservation reservation){
        printer.print(BENEFIT_LIST_HEADER);
        for (String event: reservation.getEvents()){
            printer.print(event);
        }
        addLineForReadability();
    }
    public void showTotalEventPrice(int eventTotalPrice){
        printer.print(TOTAL_BENEFIT_AMOUNT_HEADER);
        printer.printf(MONEY_UNIT, eventTotalPrice);
        addLineForReadability();
    }
    public void showPriceAfterDiscount(int afterDiscountPrice){
        System.out.println(AFTER_DISCOUNT_AMOUNT_HEADER);
        printer.printf(MONEY_UNIT, afterDiscountPrice);
        addLineForReadability();
    }
    public void showBadge(String badge){
        printer.printf(MONTHLY_BADGE_HEADER, TARGET_MONTH);
        printer.print(badge);
        addLineForReadability();
    }

    private void addLineForReadability(){
        printer.print("");
    }
}
