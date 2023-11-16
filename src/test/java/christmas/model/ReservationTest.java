package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {

    @ParameterizedTest
    @DisplayName("주말 이벤트 테스트")
    @CsvSource({
            "2, true",
            "5, false"
    })
    void createEvents(int reservationDate, boolean expectedEvent) {
        Reservation reservation = createMockReservation(reservationDate);
        reservation.createEvents();

        assertFalse(reservation.isEventNone());
        assertEquals(expectedEvent, reservation.getEvents().contains("주말 할인: -4,046원"));
    }

    @ParameterizedTest
    @DisplayName("평일 이벤트 테스트")
    @CsvSource({
            "2, false",
            "5, true"
    })
    void createEvents_weekday(int reservationDate, boolean expectedEvent) {
        Reservation reservation = createMockReservation(reservationDate);
        reservation.createEvents();

        assertFalse(reservation.isEventNone());
        assertEquals(expectedEvent, reservation.getEvents().contains("평일 할인: -4,046원"));
    }


    @ParameterizedTest
    @DisplayName("디데이 이벤트 테스트")
    @CsvSource({
            "2, true",
            "26, false"
    })
    void createEvents_dDay(int reservationDate, boolean expectedEvent) {
        Reservation reservation = createMockReservation(reservationDate);
        reservation.createEvents();

        assertFalse(reservation.isEventNone());
        assertEquals(expectedEvent, reservation.getEvents().contains("크리스마스 디데이 할인: -1,100원"));
    }
    @ParameterizedTest
    @DisplayName("특별 이벤트 테스트")
    @CsvSource({
            "2, false",
            "3, true"
    })
    void createEvents_specialDay(int reservationDate, boolean expectedEvent) {
        Reservation reservation = createMockReservation(reservationDate);
        reservation.createEvents();

        assertFalse(reservation.isEventNone());
        assertEquals(expectedEvent, reservation.getEvents().contains("특별 할인: -1,000원"));
    }


    @ParameterizedTest
    @MethodSource("countTotalStream")
    @DisplayName("총액 테스트")
    void getTotalPrice(List<String> menus, int price) {
        Reservation reservation = createMockReservationWithMenu(menus,1);
        assertEquals(reservation.getTotalPrice(), price);
    }

    @ParameterizedTest
    @MethodSource("countEventStream")
    @DisplayName("이벤트 금액 테스트")
    void getTotalEventPrice(List<String> menus, int date, int eventPrice) {
        Reservation reservation = createMockReservationWithMenu(menus, date);
        reservation.createEvents();
        assertEquals(reservation.getTotalEventPrice(), eventPrice);
    }

    @ParameterizedTest
    @MethodSource("countTotalStream")
    @DisplayName("증정 이벤트 테스트")
    void isEligibleForGivingEvent(List<String> menus, int price, boolean isEligible) {
        Reservation reservation = createMockReservationWithMenu(menus, 1);
        reservation.createEvents();
        assertEquals(reservation.isEligibleForGivingEvent(), isEligible);
    }

    @Test
    @DisplayName("이벤트 조건이 되지 않는 경우 테스트")
    void isEventNone() {
        Reservation reservation = createMockReservationWithMenu(Arrays.asList("제로콜라-1", "아이스크림-1"), 1);
        reservation.createEvents();

        assertFalse(reservation.getEvents().contains("평일 할인: -2,023원"));
        assertTrue(reservation.getEvents().contains("없음"));
    }


    private Reservation createMockReservation(int date) {
        List<String> defaultMenus = Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2");
        OrderDetails orderDetails = new OrderDetails(defaultMenus);
        ReservationDate weekendDate = new ReservationDate(date);
        return new Reservation(orderDetails, weekendDate);
    }

    private Reservation createMockReservationWithMenu(List<String> menu, int date) {
        OrderDetails orderDetails = new OrderDetails(menu);
        ReservationDate weekendDate = new ReservationDate(date);
        return new Reservation(orderDetails, weekendDate);
    }

    private static Stream<Arguments> countTotalStream() {
        return Stream.of(
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 70000, false),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1"), 79000, false),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"), 109000, false),
                Arguments.of(Arrays.asList("바비큐립-1", "레드와인-1", "아이스크림-2"), 124000, true)
        );
    }

    private static Stream<Arguments> countEventStream() {
        // 평일인 날짜 골라내기
        return Stream.of(
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 1, 3023),
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 2, 3123),
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 3, 4223),
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 4, 3323)
        );
    }

}
