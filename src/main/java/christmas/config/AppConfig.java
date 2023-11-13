package christmas.config;

import christmas.controller.ChristmasController;
import christmas.service.ChristmasService;
import christmas.utils.Input;
import christmas.utils.InputConsole;
import christmas.utils.Printer;
import christmas.utils.SystemPrinter;
import christmas.view.InputView;
import christmas.view.OutputView;

public class AppConfig {
    public ChristmasController christmasController(){
        return new ChristmasController(outputView(), inputView(), christmasService());
    }

    private ChristmasService christmasService(){
        return new ChristmasService();
    }

    private Printer printer(){
        return new SystemPrinter();
    }

    private Input input(){
        return new InputConsole();
    }

    private InputView inputView(){
        return new InputView(printer(), input());
    }

    private OutputView outputView(){
        return new OutputView(printer());
    }

}
