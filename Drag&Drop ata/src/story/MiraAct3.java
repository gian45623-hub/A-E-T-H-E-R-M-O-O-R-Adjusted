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
        sceneBossFight();
        sceneChoice();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — THE WOUND IN THE WORLD  |  Mira Cael");
        Printer.slowPrint("You journey to the deep eastern territory — the epicenter, where the Greying was strongest.");
        Printer.slowPrint("The air here is literal ash, choking and heavy. The ground feels spongy, corrupted.");
        Printer.slowPrint("Ashen things move with purpose in the thick fog, their twisted shapes unnatural.");
        Printer.pause(400);
        Printer.slowPrint("But you are a thief of the lower city. You read this magical scar the same way you read a noble's mansion:");
        Printer.slowPrint("You look for the wrong surfaces. You listen for the wrong silences. You find the path.");
        InputHandler.waitForEnter();
    }

    private void sceneEasternSite() {
        Printer.printDivider();
        Printer.slowPrint("You reach Ashford Crossing — a ruined town where survivors have been living inside the scar for twelve years.");
        Printer.slowPrint("They look like ghosts. Half-starved, sick, but hardened.");
        Printer.slowPrint("The village elder confirms your suspicions: three Crown agents came through weeks ago, heavily armed, hunting the 'Conduit'.");
        Printer.pause(400);
        Printer.slowPrint("Suddenly, a horn blares through the fog. A terrible, guttural sound.");
        Printer.slowPrint("\"They're coming!\" someone screams. The dead are walking on the camp.");
        
        System.out.println("  1. Defend the villagers. Stand your ground.");
        System.out.println("  2. Use the chaos to slip past the horde toward the Conduit site.");
        System.out.println("  3. Set a trap using the town's remaining explosive supplies.");
        
        int choice = InputHandler.getInt(1, 3);
        if (choice == 1) {
            Printer.slowPrint("You draw your daggers and step to the front line with the village defenders.");
            story.setFlag("mira_defended_village", true);
        } else if (choice == 2) {
            Printer.slowPrint("You vanish into the fog, leaving them to their fate. You have a mission.");
            story.setFlag("mira_abandoned_village", true);
        } else {
            Printer.slowPrint("You rig up a powder keg near a choke point. It blows a massive hole in the undead ranks.");
            story.setFlag("mira_used_trap", true);
        }
        
        if (!CombatSystem.startCombat(mira, Enemy.ashenSoldier())) { handleGameOver(); return; }
        mira.heal(25);
        Printer.slowPrint("A massive brute crashes through a ruined building, swinging wild.");
        if (!CombatSystem.startCombat(mira, Enemy.ashenBrute())) { handleGameOver(); return; }
        mira.heal(30);
        
        Printer.slowPrint("You wipe the black, viscous blood from your daggers. The path to the relic site is clear.");
        InputHandler.waitForEnter();
    }

    private void sceneConduit() {
        Printer.printDivider();
        Printer.slowPrint("Beneath the relic site, deep underground in a cavern that shouldn't exist, you find it.");
        Printer.slowPrint("The Pyre Conduit.");
        Printer.pause(400);
        Printer.slowPrint("It's a massive, churning engine of dark magic, suspended by massive iron chains.");
        Printer.slowPrint("It hums with a frequency that makes your teeth ache.");
        Printer.slowPrint("This is the fuel for Stage Two. This is what caused the Greying. This is what poisoned Lena.");
        Printer.pause(400);
        Printer.slowPrint("Nearby, standing on a metal gangway, an Overseer in Crown robes is adjusting the arcane flow valves.");
        Printer.slowPrint("He is tapping directly into the world's life force.");
        story.setFlag("mira_saw_conduit", true);
        InputHandler.waitForEnter();
    }

    private void sceneBossFight() {
        Printer.printDivider();
        Printer.slowPrint("You step out of the shadows. The Overseer turns, his eyes glowing with purple fire.");
        Printer.slowPrint("\"You shouldn't be here, little rat,\" he spits, drawing energy into his hands.");
        Printer.slowPrint("⚔️   MIRA CAEL  vs  CONDUIT OVERSEER");
        InputHandler.waitForEnter();
        Enemy overseer = new Enemy("Conduit Overseer", "A high-ranking arcanist guarding the Conduit.", 85, 35, 5, "Void Burst", 3);
        if (!CombatSystem.startCombat(mira, overseer)) { handleGameOver(); }
    }

    private void sceneChoice() {
        Printer.printDivider();
        Printer.slowPrint("The Overseer lies dead, his body rapidly turning to ash.");
        Printer.slowPrint("The machine is vulnerable. The core is exposed.");
        Printer.pause(400);
        Printer.slowPrint("You reach into your pack. You have the ledgers. You have the proof.");
        Printer.slowPrint("You can burn them into the public record, exposing the Crown, stopping the war, and saving thousands of lives.");
        Printer.slowPrint("Or... you can sabotage the machine, extract the pure, uncorrupted energy core, and vanish with Lena.");
        Printer.slowPrint("The core could buy her the greatest alchemists in the world. It would cure her completely.");
        
        System.out.println("  The choice is yours:");
        System.out.println("  1. Deliver the proof to the Crown's enemies. Expose the cover-up. Save thousands, but risk Lena's life.");
        System.out.println("  2. Destroy the ledgers, take the core, and disappear with Lena. Let the world burn.");
        System.out.println("  3. Sell the proof AND the core to the highest bidder on the black market. You are a thief, after all.");
        
        int c = InputHandler.getInt(1, 3);
        Printer.printDivider();
        if (c == 1) {
            Printer.slowPrint("You look at the glowing core. You think of Lena coughing in the dark.");
            Printer.slowPrint("And you think of all the other children who will cough in the dark if you walk away.");
            Printer.slowPrint("You choose the people. You sabotage the Conduit, letting it melt down, and you take the ledgers.");
            Printer.slowPrint("Lena might suffer, but no other child will.");
            story.setFlag("mira_exposed_truth", true);
        } else if (c == 2) {
            Printer.slowPrint("The world can burn. You never cared about Thaalisia. You only ever cared about her.");
            Printer.slowPrint("You extract the core. It's warm in your hands, like a heartbeat.");
            Printer.slowPrint("You drop the ledgers into the Conduit's exhaust vent. They burn to cinders instantly.");
            Printer.slowPrint("You're going to save your sister.");
            story.setFlag("mira_chose_lena", true);
        } else if (c == 3) {
            Printer.slowPrint("You are a thief. You play the game, and you win.");
            Printer.slowPrint("You extract the core carefully, and pack it next to the ledgers.");
            Printer.slowPrint("With these two items, you hold the fate of kingdoms in your knapsack.");
            Printer.slowPrint("It's time to get very, very rich.");
            story.setFlag("mira_sold_proof", true);
        }
        
        Printer.pause(500);
        Printer.printDivider();
        Printer.printBox("STORY COMPLETE — MIRA CAEL");
        Printer.printDivider();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("You fall. The Conduit hums on. Lena waits in the dark, but you will never come.");
        System.exit(0);
    }
}
