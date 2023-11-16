package christmas.utils;

import java.text.DecimalFormat;
import java.util.function.Function;

public class Messages {

    // 환영 메세지
    static public final String WELCOME = "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.\n";

    // user input 메세지
    static public final String REQUEST_RESERVATION_DATE = "%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)\n";
    static public final String REQUEST_ORDER = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    // 각 output header
    public static final String MENU_HEADER = "<주문 메뉴>";
    public static final String TOTAL_ORDER_AMOUNT_HEADER = "<할인 전 총주문 금액>";
    public static final String GIFT_MENU_HEADER = "<증정 메뉴>";
    public static final String BENEFIT_LIST_HEADER = "<혜택 내역>";
    public static final String TOTAL_BENEFIT_AMOUNT_HEADER = "<총혜택 금액>";
    public static final String AFTER_DISCOUNT_AMOUNT_HEADER = "<할인 후 예상 결제 금액>";
    public static final String MONTHLY_BADGE_HEADER = "<%d월 이벤트 배지>\n";

    // 각 포맷
    public static final Function<Integer, String> UNIT = (price) -> new DecimalFormat("###,###").format(price) + "원";
    public static final String MENU_FORMAT = "%s %d개";
    public static final String EVENT_FORMAT = "%s: -%s";
    public static final String GIFT_FORMAT = "%s %d개";
}
