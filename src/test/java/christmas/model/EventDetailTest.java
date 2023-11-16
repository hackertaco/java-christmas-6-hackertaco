package christmas.model;

import static christmas.utils.Messages.UNIT;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventDetailTest {
    @ParameterizedTest
    @DisplayName("할인 금액과 계산 일치여부 테스트")
    @MethodSource("eventCountProvider")
    void getDiscountPrice(Event event, int count) {
        EventDetail eventDetail = EventDetail.createEvent(event, count);

        assertEquals(event.calculatePrice(count), eventDetail.getDiscountPrice());
    }


    @ParameterizedTest
    @DisplayName("toString 결과 테스트")
    @MethodSource("eventCountProvider")
    void testToString(Event event, int count) {
        EventDetail eventDetail = EventDetail.createEvent(event, count);

        assertEquals(event.getName()+": -" + UNIT.apply(eventDetail.getDiscountPrice()), eventDetail.toString());
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
