package story;

import characters.Mira;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class MiraAct3 {

    private Mira mira;
    private StoryManager story;

    public MiraAct3(Mira mira, StoryManager story) {
        this.mira = mira;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneEasternSite();
        sceneConduit();
        sceneChoice();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — THE WOUND IN THE WORLD  |  Mira Cael");
        Printer.slowPrint("The eastern territory — where the Greying was strongest. Ashen things move with purpose.");
        Printer.slowPrint("You read the scar the way you read a floor: wrong surfaces, wrong silences.");
        InputHandler.waitForEnter();
    }

    private void sceneEasternSite() {
        Printer.slowPrint("Ashford Crossing — survivors inside the scar for twelve years.");
        Printer.slowPrint("The elder confirms: three agents came through weeks ago, hunting the Conduit.");
        if (!CombatSystem.startCombat(mira, Enemy.ashenSoldier())) { handleGameOver(); return; }
        if (!CombatSystem.startCombat(mira, Enemy.ashenBrute())) { handleGameOver(); return; }
        InputHandler.waitForEnter();
    }

    private void sceneConduit() {
        Printer.slowPrint("Beneath the relic site: the Pyre Conduit. Fuel for Stage Two.");
        Printer.slowPrint("Ledgers name the officials who traded clean air in the lower city for silence.");
        story.setFlag("mira_saw_conduit", true);
        InputHandler.waitForEnter();
    }

    private void sceneChoice() {
        Printer.slowPrint("You can burn the ledgers into the record — or vanish with Lena and let the machine run.");
        System.out.println("  1. Deliver proof to the Crown (expose the cover-up)");
        System.out.println("  2. Destroy the ledgers and disappear with Lena");
        System.out.println("  3. Sell the proof to the highest bidder");
        int c = InputHandler.getInt(1, 3);
        if (c == 1) story.setFlag("mira_exposed_truth", true);
        if (c == 2) story.setFlag("mira_chose_lena", true);
        if (c == 3) story.setFlag("mira_sold_proof", true);
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        System.exit(0);
    }
}
