package christmas.controller;

import static christmas.config.RuleConfig.TARGET_MONTH;
import static christmas.utils.Messages.WELCOME;

import christmas.model.Badge;
import christmas.model.OrderDetails;
import christmas.model.Reservation;
import christmas.model.ReservationDate;
import christmas.service.ChristmasService;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.function.Function;
import java.util.function.Supplier;

public class ChristmasController {
    private final OutputView outputView;
    private final InputView inputView;
    private final ChristmasService christmasService;

    public ChristmasController(OutputView outputView, InputView inputView, ChristmasService christmasService){
        this.outputView = outputView;
        this.inputView = inputView;
        this.christmasService = christmasService;
    }

    public void process(){
        outputView.printWithArguments(WELCOME, TARGET_MONTH);

        ReservationDate reservationDate = getReservationDate();
        OrderDetails orderDetails = getOrders();
        Reservation reservation = new Reservation(orderDetails, reservationDate);

        christmasService.createEvents(reservation);

        showResult(reservation);
        inputView.readClose();
    }

    private ReservationDate getReservationDate(){
        return getInputWithRetry(inputView::readDate, christmasService::createReservationDate);
    }

    private OrderDetails getOrders(){
        return getInputWithRetry(inputView::readOrder, christmasService::createOrderDetails);
    }

    private void showResult(Reservation reservation){
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

    private <T, R> T getInputWithRetry(Supplier<R> inputReader, Function<R, T> creator) {
        while (true) {
            try {
                R userInput = inputReader.get();
                return creator.apply(userInput);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

}
