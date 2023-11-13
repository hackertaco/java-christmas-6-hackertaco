package christmas.utils;

import camp.nextstep.edu.missionutils.Console;

public class InputConsole implements Input {
    @Override
    public String getInput() {
        return Console.readLine();
    }

    @Override
    public void close(){
        Console.close();
    }
}
