package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class BrennanAct1 {

    private Knight brennan;
    private StoryManager story;

    public BrennanAct1(Knight brennan, StoryManager story) {
        this.brennan = brennan;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneVillage();
        sceneBanditCamp();
        sceneWantedPoster();
        sceneOnTheRoad();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT I — THE WANDERER  |  Brennan Ashvane");
        Printer.slowPrint("Kingdom of Thaalisia, ten years after the Greying. The war with Vrakkas grinds on.");
        Printer.slowPrint("You've been walking for six days. No destination. That's the point.");
        Printer.slowPrint("The Iron Vow's deserter bounty sits around your neck like a stone —");
        Printer.slowPrint("a fall you took for an order Veyran never admitted giving.");
        Printer.slowPrint("Bounty hunters still need to know your face first.");
        Printer.slowPrint("You keep your hood up. You keep moving.");
        Printer.pause(500);
        Printer.slowPrint("The village of Millhaven appears through the treeline.");
        Printer.slowPrint("Small. Quiet. The kind of place that should be safe.");
        Printer.slowPrint("You hear screaming before you see the smoke.");
        InputHandler.waitForEnter();
    }

    private void sceneVillage() {
        Printer.printDivider();
        Printer.slowPrint("Three men in dark robes are dragging villagers into the square.");
        Printer.slowPrint("Ashen Hand cultists. You recognize the symbol — a grey hand on black cloth.");
        Printer.slowPrint("They're demanding the village's stored grain. \"A tithe to the Greying,\" one shouts.");
        Printer.pause(300);
        Printer.slowPrint("An old farmer spits at their feet. One of the cultists raises a blade.");
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Step in immediately — draw your sword.");
        System.out.println("  2. Circle around and flank them tactically.");
        System.out.println("  3. Call out from the treeline to distract them.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("Your sword is out before the thought finishes forming.");
                story.setFlag("brennan_acts_on_instinct", true);
            }
            case 2 -> {
                Printer.slowPrint("Old habits. You sweep wide, emerge behind them.");
                story.setFlag("brennan_uses_tactics", true);
            }
            case 3 -> {
                Printer.slowPrint("\"HEY!\" Your voice carries. They spin toward you.");
                Printer.slowPrint("The old man shoves the nearest cultist into the well.");
            }
        }

        Printer.slowPrint("\nCOMBAT: Ashen Hand Cultists attack!");
        InputHandler.waitForEnter();

        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        brennan.restoreMana(10);
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        Printer.slowPrint("The last cultist drops his blade and runs. You let him go.");
        InputHandler.waitForEnter();
    }

    private void sceneBanditCamp() {
        Printer.printDivider();
        Printer.slowPrint("The villagers offer coin. Food. A warm bed. You refuse all of it.");
        System.out.println();
        System.out.println("  The village elder asks if you'll stay to protect them.");
        System.out.println("  1. \"I'll stay the night.\" (Rest and recover HP)");
        System.out.println("  2. \"I'm just passing through.\" Search the cultists first.");
        System.out.println("  3. Ask about the Ashen Hand — who are they, where do they camp?");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You dream of a burning village. A medal in your hand.");
                brennan.heal(30);
            }
            case 2 -> {
                Printer.slowPrint("A map marks Fort Greyveil. Two days north.");
                story.setFlag("brennan_found_map", true);
            }
            case 3 -> {
                Printer.slowPrint("\"They camp at Greyveil,\" the elder whispers. \"No one who goes there returns.\"");
                story.setFlag("brennan_knows_about_greyveil", true);
            }
        }

        Printer.slowPrint("Among their belongings: a letter bearing the Iron Vow's seal.");
        Printer.slowPrint("\"SUPPLIES DELIVERED AS ARRANGED. — V\"");
        Printer.slowPrint("Veyran.");
        InputHandler.waitForEnter();
    }

    private void sceneWantedPoster() {
        Printer.printDivider();
        Printer.slowPrint("WANTED — BRENNAN ASHVANE. DESERTER. REWARD: 500 GOLD.");
        System.out.println("  1. Pull your hood lower.  2. Tear the poster down.  3. Talk to a merchant.");
        int choice = InputHandler.getInt(1, 3);
        if (choice == 3) {
            Printer.slowPrint("\"Some say the Vow did something to deserve losing him,\" the merchant says.");
            story.setFlag("brennan_people_suspect_vow", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneOnTheRoad() {
        Printer.printDivider();
        Printer.slowPrint("Fort Greyveil looms ahead. Two Ashen Brutes guard the gate.");
        InputHandler.waitForEnter();
        if (!CombatSystem.startCombat(brennan, Enemy.ashenBrute())) { handleGameOver(); return; }
        brennan.restoreMana(10);
        brennan.heal(15);
        Printer.slowPrint("Inside: Iron Vow crates. Weapons. Greying-corrupted relics. Veyran is funding them.");
        InputHandler.waitForEnter();
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        System.out.println("  1. Leave the lieutenant alive as a witness.  2. Walk away.");
        if (InputHandler.getInt(1, 2) == 1) story.setFlag("brennan_kept_witness", true);
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.slowPrint("You start walking toward Valdenmere — and the Iron Vow Summit.");
        Printer.printBox("ACT I COMPLETE — THE WANDERER");
        if (story.getFlag("brennan_kept_witness")) Printer.printBox("★ You kept the witness.");
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The road ends here for Brennan Ashvane.");
        System.exit(0);
    }
}
