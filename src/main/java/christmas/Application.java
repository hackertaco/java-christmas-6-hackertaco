package christmas;

import static christmas.config.RuleConfig.MENU_SPLITTER;
import static christmas.config.RuleConfig.TARGET_MONTH;

import static christmas.utils.ErrorMessages.INVALID_ORDER;
import static christmas.utils.Messages.REQUEST_ORDER;
import static christmas.utils.Messages.REQUEST_RESERVATION_DATE;
import static christmas.utils.Messages.WELCOME;

import camp.nextstep.edu.missionutils.Console;
import christmas.model.Badge;
import christmas.model.OrderDetails;
import christmas.model.Reservation;
import christmas.model.ReservationDate;
import christmas.utils.Printer;
import christmas.utils.SystemPrinter;
import christmas.view.OutputView;

import java.util.List;

public class Application {
    static Printer printer = new SystemPrinter();
    static OutputView outputView = new OutputView(printer);
    public static void main(String[] args) {



        outputView.printWithArguments(WELCOME, TARGET_MONTH);
        outputView.printWithArguments(REQUEST_RESERVATION_DATE, TARGET_MONTH);

        ReservationDate reservationDate;
        while(true){
            try{
                String pickedDate = Console.readLine();
                if(pickedDate.isBlank()){
                    throw new IllegalArgumentException(INVALID_ORDER);
                }
                 reservationDate = new ReservationDate(pickedDate);
                break;
            }catch (IllegalArgumentException e){
                outputView.printError(e.getMessage());
            }
        }

        outputView.printMessage(REQUEST_ORDER);
        OrderDetails orderDetails;
        while(true){
            try{
                String menuBeforeValidated = Console.readLine();
                if(menuBeforeValidated.isBlank()){
                    throw new IllegalArgumentException(INVALID_ORDER);
                }
                orderDetails = new OrderDetails(menuOneByOne(menuBeforeValidated));
                break;
            }catch (IllegalArgumentException e){
                outputView.printError(e.getMessage());
            }
        }
        Console.close();
        Reservation reservation = new Reservation(orderDetails, reservationDate);

        reservation.createEvents();
        showResult(reservation);
    }


    public static void showResult(Reservation reservation){
        int totalPrice = reservation.getTotalPrice();
        int totalEventPrice = reservation.getTotalEventPrice();
        String badge = Badge.getBadge(totalEventPrice);
        outputView.showMenu(reservation.getOrderDetails());
        outputView.showTotal(totalPrice);
        outputView.showGift(reservation);
        outputView.showTotalEventList(reservation);
        outputView.showTotalEventPrice(totalEventPrice);
        outputView.showPriceAfterDiscount(totalPrice - totalEventPrice);
        outputView.showBadge(badge);
    }

    public static List<String> menuOneByOne(String menuBeforeValidated){
        return List.of(menuBeforeValidated.split(MENU_SPLITTER));
    }

}
