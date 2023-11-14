package christmas.view;

import static christmas.config.RuleConfig.TARGET_MONTH;
import static christmas.utils.Messages.*;

import christmas.model.Badge;
import christmas.model.Gift;
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

    public void showResult(Reservation reservation){
        showMenu(reservation);
        showTotal(reservation);
        showGift(reservation);
        showTotalEventList(reservation);
        showTotalEventPrice(reservation);
        showPriceAfterDiscount(reservation);
        showBadge(reservation);
    }

    private void showMenu(Reservation reservation){
        List<String> menus = reservation.getOrderDetails();
        printer.print(MENU_HEADER);
        for(String menu: menus){
            printer.print(menu);
        }
        addLineForReadability();
    }

    private void showTotal(Reservation reservation){
        int totalPrice = reservation.getTotalPrice();

        printer.print(TOTAL_ORDER_AMOUNT_HEADER);
        printer.print(UNIT.apply(totalPrice));
        addLineForReadability();
    }

    private void showGift(Reservation reservation){
        int totalPrice = reservation.getTotalPrice();
        printer.print(GIFT_MENU_HEADER);
        printer.print(Gift.getGift(totalPrice));
    }

    private void showTotalEventList(Reservation reservation){
        printer.print(BENEFIT_LIST_HEADER);
        for (String event: reservation.getEvents()){
            printer.print(event);
        }
        addLineForReadability();
    }

    private void showTotalEventPrice(Reservation reservation){
        int totalEventPrice = reservation.getTotalEventPrice();

        printer.print(TOTAL_BENEFIT_AMOUNT_HEADER);
        printer.print(UNIT.apply(totalEventPrice));
        addLineForReadability();
    }

    private void showPriceAfterDiscount(Reservation reservation){
        int totalPrice = reservation.getTotalPrice();
        int totalEventPrice = reservation.getTotalEventPrice();

        System.out.println(AFTER_DISCOUNT_AMOUNT_HEADER);
        printer.print(UNIT.apply(totalPrice - totalEventPrice));
        addLineForReadability();
    }

    private void showBadge(Reservation reservation){
        int totalEventPrice = reservation.getTotalEventPrice();

        printer.printf(MONTHLY_BADGE_HEADER, TARGET_MONTH);
        printer.print(Badge.getBadge(totalEventPrice));
        addLineForReadability();
    }

    private void addLineForReadability(){
        printer.print("");
    }
}
