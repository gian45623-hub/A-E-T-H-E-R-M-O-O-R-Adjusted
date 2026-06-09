package story;

import characters.Sera;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SeraAct2 {

    private Sera sera;
    private StoryManager story;

    public SeraAct2(Sera sera, StoryManager story) {
        this.sera = sera;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneCommission();
        sceneEvidence();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT II — THE ROAD REMEMBERS  |  Sera Caldwell");
        Printer.slowPrint("Caldenmere's military quarter wants a guide to the deep eastern site.");
        Printer.slowPrint("The pay is military. The client is Crown. The timing is the war with Vrakkas.");
        InputHandler.waitForEnter();
    }

    private void sceneCommission() {
        Printer.slowPrint("Captain Thessaly slides orders across the table. \"Find the artifact. Don't ask why.\"");
        System.out.println("  1. Accept — you need answers about Corvin  2. Demand full disclosure  3. Refuse, then follow anyway");
        int c = InputHandler.getInt(1, 3);
        if (c == 2) {
            Printer.slowPrint("She hesitates. \"Peace would have made the east harder to access.\"");
            story.setFlag("sera_heard_official_hint", true);
        }
        if (c == 3) story.setFlag("sera_went_alone", true);
        InputHandler.waitForEnter();
    }

    private void sceneEvidence() {
        Printer.slowPrint("A safe house cache: correspondence linking border raids to Valdenmere interests.");
        Printer.slowPrint("Not Vrakkas alone — agents preventing negotiation, steering Thaalisia toward total war.");
        if (!CombatSystem.startCombat(sera, Enemy.rogueArcanist())) { handleGameOver(); return; }
        story.setFlag("sera_has_conspiracy_letters", true);
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printBox("ACT II COMPLETE — THE ROAD REMEMBERS");
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        System.exit(0);
    }
}
