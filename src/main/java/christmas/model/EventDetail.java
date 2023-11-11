package christmas.model;

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

    public int getDiscountPrice(){
        return discountPrice;
    }

    @Override
    public String toString(){
        return event.getName() + ": -" + discountPrice;
    }
}
