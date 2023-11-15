package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

    @ParameterizedTest
    @DisplayName("주문넣기_성공")
    @ValueSource(strings = {"티본스테이크-2"})
    void createOrder(String validInput) {
        Order order = Order.createOrder(validInput);

        assertEquals("티본스테이크", order.getMenu().getName());
        assertEquals(Menu.TBONE_STEAK, order.getMenu());
        assertEquals(2, order.getCount());
    }

    @ParameterizedTest
    @DisplayName("주문넣기_실패")
    @ValueSource(strings = {"invalidInput", "invalidInput-", "-invalidInput", "d-2", "티본스테이크-0"})
    void createInvalidOrder(String invalidInput) {
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(invalidInput));
    }

    @Test
    @DisplayName("개별 메뉴 금액 계산 테스트")
    void getPrice() {
        String menu = "티본스테이크-2";
        Order order = Order.createOrder(menu);
        Menu m = Arrays.stream(Menu.values()).filter(d-> d.getName().equals("티본스테이크")).findAny().get();
        assertEquals(order.getPrice(), m.getPrice() * 2);
    }

    @Test
    @DisplayName("개별 메뉴 toString 테스트")
    void testToString() {
        String menu = "티본스테이크-2";
        Order order = Order.createOrder(menu);

        assertEquals(order.toString(), "티본스테이크 2개");
    }
}
