package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class BrennanAct2 {

    private Knight brennan;
    private StoryManager story;

    public BrennanAct2(Knight brennan, StoryManager story) {
        this.brennan = brennan;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneValdenmere();
        sceneCampfire();
        sceneSummit();
        sceneConfrontation();
        sceneArrest();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT II — THE RECKONING  |  Brennan Ashvane");
        Printer.slowPrint("Valdenmere. The Iron Vow Summit. Every Marshal in Thaalisia — including Veyran.");
        Printer.slowPrint("Running ends today.");
        InputHandler.waitForEnter();
    }

    private void sceneValdenmere() {
        Printer.slowPrint("Your face is on three wanted boards before you reach the market.");
        System.out.println("  Where do you lie low?");
        System.out.println("  1. The Ashen Tap  2. Refugee camp  3. Old contact Ser Dovin");
        int c = InputHandler.getInt(1, 3);
        if (c == 1) story.setFlag("brennan_knows_summit_schedule", true);
        if (c == 3) story.setFlag("brennan_knows_purge_incoming", true);
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        InputHandler.waitForEnter();
    }

    private void sceneCampfire() {
        Printer.slowPrint("At a waystation you meet Eryn Voss — exiled mage, hunting the Architect.");
        System.out.println("  1. Share Veyran and the cult connection  2. Stay vague  3. Ask about her target");
        if (InputHandler.getInt(1, 3) != 2) {
            Printer.slowPrint("\"Your Veyran and my Valdros,\" she says. \"They're working together.\"");
            story.setFlag("brennan_knows_about_valdros", true);
        }
        story.setFlag("brennan_met_eryn", true);
        InputHandler.waitForEnter();
    }

    private void sceneSummit() {
        Printer.slowPrint("You infiltrate the Iron Hall. Veyran stands at the front, polished and certain.");
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        InputHandler.waitForEnter();
    }

    private void sceneConfrontation() {
        Printer.slowPrint("\"Ashvane,\" Veyran says. \"I wondered when.\"");
        System.out.println("  1. The innocent village  2. The supply crates  3. Silence");
        int c = InputHandler.getInt(1, 3);
        if (c == 2) story.setFlag("brennan_confronted_with_crates", true);
        Printer.slowPrint("\"The Greying was a wound,\" Veyran says. \"A wound needs cauterizing.\"");
        InputHandler.waitForEnter();
    }

    private void sceneArrest() {
        Printer.slowPrint("Six guards. Veyran behind them. \"Execution at dawn.\"");
        System.out.println("  1. Fight free  2. Surrender  3. Shout the truth to the hall");
        int c = InputHandler.getInt(1, 3);
        if (c == 1) story.setFlag("brennan_fought_free", true);
        if (c == 2) story.setFlag("brennan_was_arrested", true);
        if (c == 3) {
            story.setFlag("brennan_made_public_accusation", true);
            story.setFlag("brennan_was_arrested", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        if (story.getFlag("brennan_was_arrested")) {
            Printer.slowPrint("A young knight opens your cell: \"Ser Ashvane. We're not all like him.\"");
        }
        Printer.printBox("ACT II COMPLETE — THE RECKONING");
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        System.exit(0);
    }
}
