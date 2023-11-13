package christmas.model;

import static christmas.utils.Messages.EVENT_FORMAT;

import java.text.DecimalFormat;

public class EventDetail {
    Event event;
    int discountPrice;
    int count;

    private EventDetail(Event event, int count){
        this.event = event;
        this.discountPrice = event.calculate(count);
        this.count = count;
    }
    public static EventDetail createEvent(Event event, int count){
        return new EventDetail(event, count);
    }

    // dto로 랩핑 고려
    public int getDiscountPrice(){
        return discountPrice;
    }

    private String getEventName(){
        return event.getName();
    }
    private String getFormattedDiscountPrice(){
        return new DecimalFormat("###,###").format(discountPrice) + "원";
    }
    @Override
    public String toString(){
        if(discountPrice > 0){
            return getEventName() + EVENT_FORMAT + getFormattedDiscountPrice();
        }
        return getEventName();
    }
}
