package christmas.model;

import static christmas.config.RuleConfig.GIVING_EVENT_MIN_BOUND;
import static christmas.config.RuleConfig.GIVING_EVENT_PRODUCT;
import static christmas.utils.Messages.GIFT_FORMAT;

public enum Gift {
    GIFT(Menu.valueOf(GIVING_EVENT_PRODUCT).getName(), GIVING_EVENT_MIN_BOUND, 1),
    NONE("없음", 0, 0);

    private final String name;
    private final int priceThreshold;
    private final int count;

    Gift(String name, int priceThreshold, int count) {
        this.name = name;
        this.priceThreshold = priceThreshold;
        this.count = count;
    }

    public static String getGift(int price) {
        for (Gift gift : values()) {
            if (price >= gift.priceThreshold) {
                return String.format(GIFT_FORMAT, gift.name, gift.count);
            }
        }
        return Gift.NONE.name;
    }
}
