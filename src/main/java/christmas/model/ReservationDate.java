package christmas.model;

import static christmas.config.RuleConfig.D_DAY;
import static christmas.config.RuleConfig.EVENT_START_DAY;
import static christmas.config.RuleConfig.SPECIAL_DISCOUNT_DATE;
import static christmas.config.RuleConfig.TARGET_MONTH;
import static christmas.config.RuleConfig.TARGET_YEAR;
import static christmas.utils.ErrorMessages.INVALID_DATE;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationDate{
    private final int eventDate;

    public ReservationDate(int pickedDate){
        validatePickedDate(pickedDate);
        eventDate = pickedDate;
    }

    private void validatePickedDate(int day){
        LocalDate date = LocalDate.of(TARGET_YEAR, TARGET_MONTH, 1);
        if(day < EVENT_START_DAY || day > date.lengthOfMonth()){
            throw new IllegalArgumentException(INVALID_DATE);
        }
    }

    public boolean isWeekend(){
        LocalDate date = LocalDate.of(TARGET_YEAR, TARGET_MONTH, eventDate);
        String day = date.getDayOfWeek().toString();
        return Objects.equals(day, "SATURDAY") || Objects.equals(day, "SUNDAY");
    }

    public boolean isBeforeDday(){
        return eventDate <= D_DAY;
    }

    public int getParsedDateForDdayCalculate(){
        return eventDate - EVENT_START_DAY;
    }

    public boolean isInSpecialDay(){
        return SPECIAL_DISCOUNT_DATE.contains(eventDate);
    }
}
