package christmas.model;

import static christmas.config.RuleConfig.EVENT_MIN_BOUND;
import static christmas.config.RuleConfig.GIVING_EVENT_MIN_BOUND;
import static christmas.config.RuleConfig.GIVING_EVENT_PRODUCT_COUNT;
import static christmas.config.RuleConfig.WEEKDAY_DISCOUNT_MENU_GROUP;
import static christmas.config.RuleConfig.WEEKEND_DISCOUNT_MENU_GROUP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reservation {
    private final OrderDetails orderDetails;
    private final ReservationDate date;
    private final List<EventDetail> events;

    public Reservation(OrderDetails orderDetails, ReservationDate date){
        this.date = date;
        this.orderDetails = orderDetails;
        this.events = new ArrayList<>();
    }
    public void createEvents(){
        if(getTotalPrice() >= EVENT_MIN_BOUND){
            checkWeekendDiscountEvent();
            checkWeekdayDiscountEvent();
            checkDdayEvent();
            checkSpecialEvent();
            checkGivingEvent();
        }

        if(isEventNone()){
            events.add(EventDetail.createEvent(Event.NONE, 0));
        }
    }

    private void checkWeekendDiscountEvent(){
        int count = countTargetMenu(MenuGroup.valueOf(WEEKEND_DISCOUNT_MENU_GROUP));
        if(date.isWeekend() && count > 0){
            events.add(EventDetail.createEvent(Event.WEEKEND, count));
        }
    }
    private void checkWeekdayDiscountEvent(){
        int count = countTargetMenu(MenuGroup.valueOf(WEEKDAY_DISCOUNT_MENU_GROUP));
        if(!date.isWeekend() && count > 0){
            events.add(EventDetail.createEvent(Event.WEEKDAY, count));
        }
    }
    private void checkDdayEvent(){
        if(date.isBeforeDday()){
            events.add(EventDetail.createEvent(Event.D_DAY, date.getParsedDateForDdayCalculate()));
        }
    }
    private void checkSpecialEvent(){
        if(date.isInSpecialDay()){
            events.add(EventDetail.createEvent(Event.SPECIAL, 1));
        }
    }

    private void checkGivingEvent(){
        if(isEligibleForGivingEvent()){
            events.add(EventDetail.createEvent(Event.GIFT, GIVING_EVENT_PRODUCT_COUNT));
        }
    }

    private int countTargetMenu(MenuGroup menuGroup) {
        return orderDetails.countTargetMenu(menuGroup.getMenuGroup());
    }
    public List<String> getOrderDetails(){
        return orderDetails.getOrders();
    }

    public int getTotalPrice(){
        return orderDetails.countTotal();
    }

    public int getTotalEventPrice(){
        return events.stream().mapToInt(EventDetail::getDiscountPrice).sum();
    }

    public boolean isEligibleForGivingEvent(){
        return orderDetails.countTotal() >= GIVING_EVENT_MIN_BOUND;
    }

    public boolean isEventNone(){
        return events.size() == 0;
    }

    public List<String> getEvents(){
        return events.stream().map(EventDetail::toString).collect(Collectors.toList());
    }
}
