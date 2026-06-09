package story;

import characters.Sera;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SeraAct1 {

    private Sera sera;
    private StoryManager story;

    public SeraAct1(Sera sera, StoryManager story) {
        this.sera = sera;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        scenePatrol();
        sceneAmbush();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT I — THE VOSS COMPASS  |  Sera Caldwell");
        Printer.slowPrint("Seven years on Thaalisia's eastern patrol taught you to hear the land before it moves.");
        Printer.slowPrint("Three years of contract work kept the skill sharp — and the guilt sharper.");
        Printer.slowPrint("You know these hills. You have been trying not to.");
        InputHandler.waitForEnter();
    }

    private void scenePatrol() {
        Printer.slowPrint("You guide a survey party toward ruins in the eastern scar.");
        Printer.slowPrint("Pell stumbles a tripwire. Your bow is up before he hits the ground.");
        Printer.slowPrint("Nothing moves. An old trap. But the wire was new.");
        story.setFlag("sera_noticed_fresh_trap", true);
        InputHandler.waitForEnter();
    }

    private void sceneAmbush() {
        Printer.slowPrint("The bend in the trail opens east — the road you stopped wanting to look at.");
        Printer.slowPrint("Memory: Corvin Ash, peace emissary to the border kingdoms. Vrakkas wanted war.");
        Printer.slowPrint("You heard the attack before you saw it. You arrived after.");
        Printer.slowPrint("Now crossbow bolts from the ridge. Not bandits — disciplined, timed.");
        System.out.println("  1. Shield the party — hold the line  2. Flank the ridge  3. Order a fighting retreat");
        int c = InputHandler.getInt(1, 3);
        if (c == 2) story.setFlag("sera_flanked_ridge", true);
        if (!CombatSystem.startCombat(sera, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        if (!CombatSystem.startCombat(sera, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        Printer.slowPrint("Among the dead: Vrakkas colors — and a seal you don't recognize. Not random. Engineered.");
        story.setFlag("sera_found_engineered_ambush", true);
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printBox("ACT I COMPLETE — THE VOSS COMPASS");
        Printer.slowPrint("Corvin's death was not bad luck. Someone needed the eastern road open for war.");
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The eastern road claims another guide.");
        System.exit(0);
    }
}
