package story;

import characters.Sera;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SeraAct3 {

    private Sera sera;
    private StoryManager story;

    public SeraAct3(Sera sera, StoryManager story) {
        this.sera = sera;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneDeepSite();
        sceneConfrontation();
        sceneChoice();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — THE WOUND IN THE WORLD  |  Sera Caldwell");
        Printer.slowPrint("You lead the party into the scar. The territory is yours by familiarity, not possession.");
        Printer.slowPrint("Eryn feels the Greying first. You say: \"We're inside the wound.\"");
        InputHandler.waitForEnter();
    }

    private void sceneDeepSite() {
        Printer.slowPrint("The artifact glows at the center — connected to the Pyre Conduit beneath.");
        Printer.slowPrint("Stage Two: a deliberate reset. Valdros's answer to a world he believes too broken to save.");
        if (!CombatSystem.startCombat(sera, Enemy.ashenSoldier())) { handleGameOver(); return; }
        if (!CombatSystem.startCombat(sera, Enemy.ashenBrute())) { handleGameOver(); return; }
        InputHandler.waitForEnter();
    }

    private void sceneConfrontation() {
        Printer.slowPrint("Captain Thessaly arrives with soldiers — to secure the Conduit, not stop it.");
        Printer.slowPrint("\"The war bought us time,\" she says. \"You were always meant to open the road.\"");
        if (!CombatSystem.startCombat(sera, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        InputHandler.waitForEnter();
    }

    private void sceneChoice() {
        Printer.slowPrint("You can testify before the Crown, leak the letters, or walk away from every institution.");
        System.out.println("  1. Present evidence publicly (stop the engineered war)");
        System.out.println("  2. Leak to the eastern settlements and disappear");
        System.out.println("  3. Take the commission's silence payment");
        int c = InputHandler.getInt(1, 3);
        if (c == 1) story.setFlag("sera_public_testimony", true);
        if (c == 2) story.setFlag("sera_lone_road", true);
        if (c == 3) story.setFlag("sera_took_silence", true);
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        System.exit(0);
    }
}
