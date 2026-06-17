package util;

public class Dice {

    /**
     * Rolls a d20 (1 to 20).
     * 
     * @return random int between 1 and 20.
     */
    public static int rollD20() {
        return (int) (Math.random() * 20) + 1;
    }

    /**
     * General dice roll.
     * 
     * @param min minimum value
     * @param max maximum value
     * @return random int between min and max (inclusive)
     */
    public static int roll(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Performs a story skill check.
     * 
     * @param actionName name of the skill being tested (e.g. "Investigation")
     * @param dc         difficulty class (target number to reach)
     * @return true if successful, false otherwise
     */
    public static boolean performSkillCheck(String actionName, int dc) {
        System.out.println();
        util.Printer.printDivider();
        System.out.println("  [SKILL CHECK: " + actionName + " | DC: " + dc + "]");
        System.out.println("  Press Enter to roll the D20...");
        util.InputHandler.waitForEnter();

        int roll = rollD20();
        System.out.println("  [D20 Roll: " + roll + "]");

        if (roll == 1) {
            System.out.println("  *** CRITICAL FAILURE! ***");
            util.Printer.printDivider();
            System.out.println();
            return false;
        } else if (roll == 20) {
            System.out.println("  *** CRITICAL SUCCESS! ***");
            util.Printer.printDivider();
            System.out.println();
            return true;
        }

        if (roll >= dc) {
            System.out.println("  [SUCCESS]");
            util.Printer.printDivider();
            System.out.println();
            return true;
        } else {
            System.out.println("  [FAILURE]");
            util.Printer.printDivider();
            System.out.println();
            return false;
        }
    }
}
