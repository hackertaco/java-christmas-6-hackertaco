package christmas.service;

import christmas.model.OrderDetails;
import christmas.model.Reservation;
import christmas.model.ReservationDate;
import java.util.List;

public class ChristmasService {
    public ReservationDate createReservationDate(int pickedDate) {
        return new ReservationDate(pickedDate);
    }

    public OrderDetails createOrderDetails(List<String> orders) {
        return new OrderDetails(orders);
    }

    public void createEvents(Reservation reservation){
        reservation.createEvents();
    }

}
