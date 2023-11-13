package christmas.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventDetailTest {
    @DisplayName("각 이벤트 별 ")
    @ParameterizedTest
    @MethodSource("eventCountProvider")
    void getDiscountPrice(Event event, int count) {
        EventDetail eventDetail = EventDetail.createEvent(event, count);

        assertEquals(event.calculate(count), eventDetail.getDiscountPrice());
    }


    @ParameterizedTest
    @MethodSource("eventCountProvider")
    void testToString(Event event, int count) {
        EventDetail eventDetail = EventDetail.createEvent(event, count);

        assertEquals(event.getName()+": -" + eventDetail.getDiscountPrice(), eventDetail.toString());
    }

    private static Stream<Arguments> eventCountProvider() {
        return Stream.of(
                Arguments.of(Event.WEEKDAY, 2),
                Arguments.of(Event.WEEKEND, 3),
                Arguments.of(Event.D_DAY, 25),
                Arguments.of(Event.SPECIAL, 1),
                Arguments.of(Event.GIFT, 1)
        );
    }
}
