package util;

//dto nagaganap yung typewriter effect ng story, Angas no HAHHAHHAHHA
public class Printer {

    private static final int DEFAULT_DELAY = 25;
    private static volatile boolean skipDialogue = false;

    public static void setSkipDialogue(boolean skip) {
        skipDialogue = skip;
    }

    public static void slowPrint(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            if (!skipDialogue) {
                try {
                    int delay = (c == '.' || c == '!' || c == '?') ? 190 : DEFAULT_DELAY;
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println();
    }

    public static void printDivider() {
        System.out.println("\n────────────────────────────────────────────────────────");
    }

    public static void printTitle(String title) {
        printDivider();
        System.out.println("  " + title);
        printDivider();
    }

    public static void printBox(String text) {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ " + text + " │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
