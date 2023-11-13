package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import christmas.utils.ErrorMessages;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderDetailsTest {

    @ParameterizedTest
    @DisplayName("중복이 아닌 케이스")
    @MethodSource("testData")
    void notDuplicate(List<String> menus) {
        assertDoesNotThrow(() -> new OrderDetails(menus));
    }

    @ParameterizedTest
    @DisplayName("중복 케이스")
    @MethodSource("testDuplicateData")
    void checkDuplicate(List<String> menus) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderDetails(menus));
        assertThat(exception).hasMessage(ErrorMessages.INVALID_ORDER);
    }


    @ParameterizedTest
    @MethodSource("onlyBeverage")
    void existOnlyBeverage_true(List<String> menus) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderDetails(menus));
        assertThat(exception).hasMessage(ErrorMessages.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("countTotalStream")
    void existOnlyBeverage_false(List<String> menus) {
        assertDoesNotThrow(() -> new OrderDetails(menus));
    }

    @Test
    void exceedMaxOrderCount() {
        List<String> orders = List.of("레드와인-2", "티본스테이크-1", "샴페인-3");
        assertDoesNotThrow(() -> new OrderDetails(orders));
    }

    @Test
    void exceedMaxOrderCount_fail() {
        List<String> orders = List.of("레드와인-15", "티본스테이크-3", "샴페인-5");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderDetails(orders));
        assertThat(exception).hasMessage(ErrorMessages.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("countTotalStream")
    void countTotal(List<String> menus, int price) {
        OrderDetails orderDetails = new OrderDetails(menus);

        int total = orderDetails.countTotal();

        assertEquals(price, total);
    }

    @ParameterizedTest
    @MethodSource("countTargetStream")
    void countTargetMenu(List<String> menus, int realCount) {
        OrderDetails orderDetails = new OrderDetails(menus);
        int count = orderDetails.countTargetMenu(MenuGroup.BEVERAGE.getMenuGroup());

        assertEquals(realCount, count);
    }


    private static Stream<Arguments> countTotalStream() {
        return Stream.of(
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 70000),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1"), 79000),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"), 109000),
                Arguments.of(Arrays.asList("제로콜라-3", "크리스마스파스타-1"), 34000)
        );
    }
    private static Stream<Arguments> countTargetStream() {
        return Stream.of(
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 0),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1"), 0),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"), 0),
                Arguments.of(Arrays.asList("제로콜라-3", "크리스마스파스타-1"), 3)
        );
    }
    private static Stream<Arguments> onlyBeverage() {
        return Stream.of(
                Arguments.of(Arrays.asList("제로콜라-1", "레드와인-1")),
                Arguments.of(Arrays.asList("샴페인-1", "레드와인-1")),
                Arguments.of(Arrays.asList("제로콜라-1", "레드와인-1", "샴페인-2"))
        );
    }
    static Stream<List<String>> testData() {
        return Stream.of(Arrays.asList("티본스테이크-1", "초코케이크-1"));
    }
    static Stream<List<String>> testDuplicateData() {
        return Stream.of(Arrays.asList("티본스테이크-1", "티본스테이크-1"));
    }
}
