package christmas.model;

import static christmas.utils.Messages.EVENT_FORMAT;
import static christmas.utils.Messages.UNIT;

public class EventDetail {
    Event event;
    int discountPrice;
    int count;

    private EventDetail(Event event, int count){
        this.event = event;
        this.discountPrice = event.calculatePrice(count);
        this.count = count;
    }
    public static EventDetail createEvent(Event event, int count){
        return new EventDetail(event, count);
    }

    public int getDiscountPrice(){
        return discountPrice;
    }

    @Override
    public String toString(){
        if(discountPrice > 0){
            return getEventName() + EVENT_FORMAT + getFormattedDiscountPrice();
        }
        return getEventName();
    }

    private String getEventName(){
        return event.getName();
    }

    private String getFormattedDiscountPrice(){
        return UNIT.apply(discountPrice);
    }
}
