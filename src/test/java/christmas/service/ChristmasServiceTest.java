package christmas.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChristmasServiceTest {
    ChristmasService christmasService = new ChristmasService();

    @Test
    void createReservationDate() {
        int pickedDate = 1;
        assertDoesNotThrow(() -> christmasService.createReservationDate(pickedDate));
    }

    @Test
    void createReservationDate_throw_error() {
        int pickedDate = 59;
        assertThrows(IllegalArgumentException.class, () -> christmasService.createReservationDate(pickedDate));
    }

    @Test
    void createOrderDetails() {
        List<String> menus = Arrays.asList("초코케이크-1");
        assertDoesNotThrow(() -> christmasService.createOrderDetails(menus));
    }

    @Test
    void createOrderDetails_throw_error() {
        List<String> menus = Arrays.asList("제로콜라-1");
        assertThrows(IllegalArgumentException.class, () -> christmasService.createOrderDetails(menus));
    }
}
