package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class BrennanAct3 {

    private Knight brennan;
    private StoryManager story;

    public BrennanAct3(Knight brennan, StoryManager story) {
        this.brennan = brennan;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneBreakout();
        sceneFinalChoice();
        sceneBossFight();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — REDEMPTION OR REVENGE  |  Brennan Ashvane");
        Printer.slowPrint("The holding cells of the Iron Hall. Execution at dawn.");
        Printer.slowPrint("Young knights still believe in something. Some are about to prove it.");
        InputHandler.waitForEnter();
    }

    private void sceneBreakout() {
        Printer.slowPrint("Ser Aldis opens your cell. \"I was at Millfield. I know what Veyran ordered.\"");
        System.out.println("  1. Trust them fully  2. Use them carefully  3. Escape alone");
        InputHandler.getInt(1, 3);
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        if (!CombatSystem.startCombat(brennan, Enemy.ashenBrute())) { handleGameOver(); return; }
        brennan.heal(15);
        InputHandler.waitForEnter();
    }

    private void sceneFinalChoice() {
        Printer.slowPrint("Veyran stands alone. Sword in hand. Still certain.");
        System.out.println("  How do you end this?");
        System.out.println("  1. Kill him yourself  2. Bring him to trial alive  3. Offer him a public trial");
        if (InputHandler.getInt(1, 3) == 1) {
            story.setFlag("brennan_killed_veyran_personally", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneBossFight() {
        Printer.slowPrint("⚔️   BRENNAN ASHVANE  vs  LORD MARSHAL VEYRAN");
        InputHandler.waitForEnter();
        if (!CombatSystem.startCombat(brennan, Enemy.bossVeyran())) { handleGameOver(); }
    }

    private void handleGameOver() {
        Printer.slowPrint("Veyran wins. The corruption spreads.");
        System.exit(0);
    }
}
