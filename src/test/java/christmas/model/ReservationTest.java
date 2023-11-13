package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {

    @Test
    @DisplayName("주말 이벤트 추가")
    void createEvents_weekend() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate weekendDate = new ReservationDate(2);
        Reservation reservation = new Reservation(orderDetails, weekendDate);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertTrue(reservation.getEvents().contains("주말 할인: -4,046원"));
    }

    @Test
    @DisplayName("주말 이벤트 없음")
    void createEvents_no_weekend() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate weekendDate = new ReservationDate(5);
        Reservation reservation = new Reservation(orderDetails, weekendDate);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertFalse(reservation.getEvents().contains("주말 할인: -4,046원"));
    }

    @Test
    @DisplayName("평일 이벤트 있음")
    void createEvents_weekday() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate date = new ReservationDate(5);
        Reservation reservation = new Reservation(orderDetails, date);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertTrue(reservation.getEvents().contains("평일 할인: -4,046원"));
    }
    @Test
    @DisplayName("평일 이벤트 없음")
    void createEvents_no_weekday() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate date = new ReservationDate(2);
        Reservation reservation = new Reservation(orderDetails, date);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertFalse(reservation.getEvents().contains("평일 할인: -4,046원"));
    }

    @Test
    @DisplayName("디데이 이벤트 테스트")
    void createEvents_dDay() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate date = new ReservationDate(2);
        Reservation reservation = new Reservation(orderDetails, date);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertTrue(reservation.getEvents().contains("크리스마스 디데이 할인: -1,100원"));
    }

    @Test
    @DisplayName("디데이 이벤트 없음")
    void createEvents_dDay_none() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate date = new ReservationDate(26);
        Reservation reservation = new Reservation(orderDetails, date);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertFalse(reservation.getEvents().contains("크리스마스 디데이 할인: -1,100원"));
    }
    @Test
    @DisplayName("특별 이벤트")
    void createEvents_specialDay() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"));
        ReservationDate date = new ReservationDate(3);
        Reservation reservation = new Reservation(orderDetails, date);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.isEventNone());
        assertTrue(reservation.getEvents().contains("특별 할인: -1,000원"));
    }


    @ParameterizedTest
    @MethodSource("countTotalStream")
    @DisplayName("총액 테스트")
    void getTotalPrice(List<String> menus, int price) {
        OrderDetails orderDetails = createMockOrderDetails(menus);
        ReservationDate reservationDate = new ReservationDate(1);
        Reservation reservation = new Reservation(orderDetails, reservationDate);
        assertEquals(reservation.getTotalPrice(), price);
    }

    @Test
    void getTotalEventPrice() {
    }

    @ParameterizedTest
    @MethodSource("countTotalStream")
    @DisplayName("증정 이벤트 테스트")
    void isEligibleForGivingEvent(List<String> menus, int price, boolean isEligible) {
        OrderDetails orderDetails = createMockOrderDetails(menus);
        ReservationDate reservationDate = new ReservationDate(1);
        Reservation reservation = new Reservation(orderDetails, reservationDate);
        assertEquals(reservation.isEligibleForGivingEvent(), isEligible);
    }

    @Test
    void isEventNone() {
        // Given
        OrderDetails orderDetails = createMockOrderDetails(Arrays.asList("제로콜라-1", "아이스크림-1"));
        ReservationDate date = new ReservationDate(5);
        Reservation reservation = new Reservation(orderDetails, date);

        // When
        reservation.createEvents();

        // Then
        assertFalse(reservation.getEvents().contains("평일 할인: -2,023원"));
        assertTrue(reservation.getEvents().contains("없음"));
    }


    private OrderDetails createMockOrderDetails(List<String> menus) {
        return new OrderDetails(menus);
    }

    private static Stream<Arguments> countTotalStream() {
        return Stream.of(
                Arguments.of(Arrays.asList("티본스테이크-1", "초코케이크-1"), 70000, false),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1"), 79000, false),
                Arguments.of(Arrays.asList("바비큐립-1", "크리스마스파스타-1", "초코케이크-2"), 109000, false),
                Arguments.of(Arrays.asList("바비큐립-1", "레드와인-1", "아이스크림-2"), 124000, true)
        );
    }

}
