package christmas.config;

import java.util.List;

public class RuleConfig {
    public static final int MIN_DAY = 1;
    public static final int MAX_DAY = 31;

    public static final String MENU_SPLITTER = ",";
    public static final String MENU_DIVIDER = "-";

    public static final int MAX_ORDER_COUNT = 20;
    public static final int MIN_ORDER_COUNT = 1;

    public static final int GIVING_EVENT_MIN_BOUND = 120000;
    public static final String GIVING_EVENT_PRODUCT = "CHAMPAGNE";

    public static final int TARGET_YEAR = 2023;
    public static final int TARGET_MONTH = 12;
    public static final int DATE_DISCOUNT_PRICE = 2023;

    public static final String WEEKEND_DISCOUNT_MENU_GROUP = "MAIN";
    public static final String WEEKDAY_DISCOUNT_MENU_GROUP = "DESSERT";
    public static final List<Integer> SPECIAL_DISCOUNT_DATE = List.of(3, 10, 17, 24, 25, 31);
}
