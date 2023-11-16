package christmas.view;

import static christmas.config.RuleConfig.MENU_SPLITTER;
import static christmas.config.RuleConfig.TARGET_MONTH;
import static christmas.utils.ErrorMessages.INVALID_DATE;
import static christmas.utils.ErrorMessages.INVALID_ORDER;
import static christmas.utils.Messages.REQUEST_ORDER;
import static christmas.utils.Messages.REQUEST_RESERVATION_DATE;

import christmas.utils.Input;
import christmas.utils.Printer;
import java.util.List;


public class InputView {
    private final Printer printer;
    private final Input input;

    public InputView(Printer printer, Input input) {
        this.printer = printer;
        this.input = input;
    }

    public int readDate(){
        printer.printf(REQUEST_RESERVATION_DATE, TARGET_MONTH);
        String pickedDate = input.getInput();
        validateBlank(pickedDate);

        return parseStringToInt(pickedDate);
    }

    public List<String> readOrder(){
        printer.print(REQUEST_ORDER);
        String menus = input.getInput();
        validateBlank(menus);

        return parseStringToList(menus);
    }

    public void readClose(){
        input.close();
    }

    private void validateBlank(String input){
        if(input.isBlank()){
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }

    private int parseStringToInt(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(INVALID_DATE);
        }
    }

    private List<String> parseStringToList(String input){
        return List.of(input.split(MENU_SPLITTER));
    }
}
