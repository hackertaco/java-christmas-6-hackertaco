package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeTest {
    @ParameterizedTest
    @DisplayName("이벤트 금액에 따라 뱃지가 정상적으로 리턴되는지를 테스트")
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
