package ui.console;

import java.util.Scanner;

public abstract class CheckInput {
    private Scanner input;

    public CheckInput() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: abstract class for check input to be an integer;
    public abstract void checkInt();

    // EFFECTS: abstract class for check input to be valid;
    public abstract int checkInput();

}
