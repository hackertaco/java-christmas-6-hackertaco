package christmas.model;

public enum Badge {
    FIRST("산타",20000),
    SECOND("트리", 10000),
    THIRD("별", 5000),
    NONE("없다", 0);
    private final String name;
    private final int priceThreshold;

    Badge(String name, int priceThreshold) {
        this.name = name;
        this.priceThreshold = priceThreshold;
    }

    public static String getBadge(int totalEventPrice) {
        for (Badge badge : values()) {
            if (totalEventPrice >= badge.priceThreshold) {
                return badge.name;
            }
        }
        return Badge.NONE.name;
    }
}
