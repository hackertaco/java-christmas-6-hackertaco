package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GiftTest {

    @ParameterizedTest
    @DisplayName("구매 금액에 따라 선물이 정상적으로 리턴되는지를 테스트")
    @CsvSource({
            "145000, 샴페인 1개",
            "0, 없음"
    })
    void getGift(int totalPrice, String expectedGift) {
        assertEquals(Gift.getGift(totalPrice), expectedGift);
    }
}
