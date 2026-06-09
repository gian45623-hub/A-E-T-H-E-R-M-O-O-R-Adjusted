package story;

import characters.Mira;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class MiraAct2 {

    private Mira mira;
    private StoryManager story;

    public MiraAct2(Mira mira, StoryManager story) {
        this.mira = mira;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneArchive();
        sceneTruth();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT II — THE COST OF AIR  |  Mira Cael");
        Printer.slowPrint("Davan, your fence, sends you to the Crown Archive — \"routine retrieval.\"");
        Printer.slowPrint("The pay is wrong. The timing is wrong. Lena's treatment deposit matches the fee exactly.");
        Printer.slowPrint("Someone designed this job around what you would do for her.");
        InputHandler.waitForEnter();
    }

    private void sceneArchive() {
        Printer.slowPrint("You slip through the stacks after midnight. The target file: Greying medical assessments.");
        if (!CombatSystem.startCombat(mira, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        Printer.slowPrint("The documents prove officials knew the lower-city poison was deliberate cover-up.");
        story.setFlag("mira_has_archive_proof", true);
        InputHandler.waitForEnter();
    }

    private void sceneTruth() {
        Printer.slowPrint("Davan meets you in the rain. \"They needed the map moved. You were useful.\"");
        System.out.println("  1. Demand he help get Lena out  2. Threaten to expose him  3. Walk away — find another way");
        int c = InputHandler.getInt(1, 3);
        if (c == 2) story.setFlag("mira_blackmailed_davan", true);
        if (c == 3) story.setFlag("mira_cut_ties_davan", true);
        Printer.slowPrint("Either way, the eastern road is the only place the cure and the truth intersect.");
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printBox("ACT II COMPLETE — THE COST OF AIR");
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        System.exit(0);
    }
}
