package christmas.model;

import static christmas.config.RuleConfig.EVENT_START_DAY;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationDateTest {

    @ParameterizedTest
    @DisplayName("날짜체크_성공")
    @ValueSource(ints = {2, 3, 4 ,5})
    void isBeforeDday(int date) {
        ReservationDate reservationDate = new ReservationDate(date);
        assertTrue(reservationDate.isBeforeDday());
    }

    @ParameterizedTest
    @DisplayName("날짜체크_실패")
    @ValueSource(ints = {26, 27})
    void isBeforeDday_fail(int date) {
        ReservationDate reservationDate = new ReservationDate(date);
        assertFalse(reservationDate.isBeforeDday());
    }


    @ParameterizedTest
    @DisplayName("D-day 날짜 계산을 위한 메서드")
    @ValueSource(ints = {26, 27})
    void getParsedDateForDdayCalculate(int date) {
        ReservationDate reservationDate = new ReservationDate(date);
        assertEquals(date - EVENT_START_DAY, reservationDate.getParsedDateForDdayCalculate());
    }

    @ParameterizedTest
    @DisplayName("특별날짜체크_성공")
    @ValueSource(ints = {3, 10, 17})
    void isInSpecialDay(int date) {
        ReservationDate reservationDate = new ReservationDate(date);
        assertTrue(reservationDate.isInSpecialDay());
    }

    @ParameterizedTest
    @DisplayName("특별날짜체크_성공")
    @ValueSource(ints = {2, 5, 6})
    void isInSpecialDay_fail(int date) {
        ReservationDate reservationDate = new ReservationDate(date);
        assertFalse(reservationDate.isInSpecialDay());
    }
}
