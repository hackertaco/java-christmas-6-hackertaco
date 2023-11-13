package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeTest {
    @ParameterizedTest
    @CsvSource({
            "25000, 산타",
            "15000, 트리",
            "6000, 별",
            "0, 없다"
    })
    public void testGetBadge(int totalEventPrice, String expectedBadge) {
        assertEquals(expectedBadge, Badge.getBadge(totalEventPrice));
    }
}
