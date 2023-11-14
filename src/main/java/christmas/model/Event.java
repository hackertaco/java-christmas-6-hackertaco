package christmas.model;

import static christmas.config.RuleConfig.DATE_DISCOUNT_PRICE;
import static christmas.config.RuleConfig.GIVING_EVENT_PRODUCT;
import static christmas.config.RuleConfig.SPECIAL_DISCOUNT_PRICE;

import java.util.function.Function;

public enum Event {
    SPECIAL("특별 할인", value-> value * SPECIAL_DISCOUNT_PRICE),
    WEEKDAY("평일 할인", value-> value * DATE_DISCOUNT_PRICE),
    WEEKEND("주말 할인", value-> value * DATE_DISCOUNT_PRICE),
    D_DAY("크리스마스 디데이 할인", value-> value * 100 + 1000),
    GIFT("증정 이벤트", value-> value * Menu.valueOf(GIVING_EVENT_PRODUCT).getPrice()),
    NONE("없음", value->value)
    ;

    private final String name;
    private final Function<Integer, Integer> expression;

    Event(String name, Function<Integer, Integer> expression){
        this.name = name;
        this.expression = expression;
    }
    public String getName(){
        return this.name;
    }

    public int calculatePrice(int value){
        return expression.apply(value);
    }
}
