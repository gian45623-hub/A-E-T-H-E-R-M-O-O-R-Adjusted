package util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InputHandler {
    private static final BlockingQueue<String> inputQueue = new ArrayBlockingQueue<>(1);

    public static void submitInput(String input) {
        inputQueue.offer(input);
    }

    private static String takeInput() {
        try {
            String input = inputQueue.take();
            util.Printer.setSkipDialogue(false);
            return input;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }

    public static int getInt(int min, int max) {
        int input = -1;
        while (input < min || input > max) {
            // Tell the UI to show the buttons for these choices
            if (ui.GamePanel.getInstance() != null) {
                ui.GamePanel.getInstance().showNumberChoices(min, max);
            }
            
            try {
                input = Integer.parseInt(takeInput().trim());
                if (input < min || input > max) {
                    System.out.println("Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection.");
            }
        }

        if (ui.GamePanel.getInstance() != null) {
            ui.GamePanel.getInstance().stopChoiceTimer();
        }
        
        // Echo the choice so the user sees what they picked
        System.out.println("\n> " + input);
        return input;
    }

    public static String getString() {
        // Not implemented in button UI, but we could add a text field fallback later
        return takeInput().trim();
    }

    public static void waitForEnter() {
        if (ui.GamePanel.getInstance() != null) {
            ui.GamePanel.getInstance().showContinueButton();
        } else {
            System.out.print("\n[Press ENTER to continue...]");
        }
        takeInput();
        System.out.println();
    }
}
