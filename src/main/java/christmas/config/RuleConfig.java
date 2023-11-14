package christmas.config;

import java.util.List;

public class RuleConfig {
    // 메뉴 포맷팅 분리 관련
    public static final String MENU_SPLITTER = ",";
    public static final String MENU_DIVIDER = "-";

    // 주문 갯수 요구사항 관련
    public static final int MAX_ORDER_COUNT = 20;
    public static final int MIN_ORDER_COUNT = 1;

    // 이벤트 조건 정보
    public static final int EVENT_MIN_BOUND = 10000;
    public static final int TARGET_YEAR = 2023;
    public static final int TARGET_MONTH = 12;
    public static final int D_DAY = 25;
    public static final int EVENT_START_DAY = 1;

    // 증정 이벤트 관련
    public static final int GIVING_EVENT_MIN_BOUND = 120000;
    public static final String GIVING_EVENT_PRODUCT = "CHAMPAGNE";

    // 할인 이벤트 관련
    public static final int DATE_DISCOUNT_PRICE = 2023;
    public static final String WEEKEND_DISCOUNT_MENU_GROUP = "MAIN";
    public static final String WEEKDAY_DISCOUNT_MENU_GROUP = "DESSERT";
    public static final int SPECIAL_DISCOUNT_PRICE = 1000;
    public static final List<Integer> SPECIAL_DISCOUNT_DATE = List.of(3, 10, 17, 24, 25, 31);


}
