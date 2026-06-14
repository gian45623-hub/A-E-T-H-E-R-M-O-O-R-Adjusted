package story;

import characters.Mage;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class ErynAct3 {

    private Mage eryn;
    private StoryManager story;

    public ErynAct3(Mage eryn, StoryManager story) {
        this.eryn = eryn;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneTheSanctum();
        sceneTheSpellbooks();
        sceneStageTwo();
        sceneFinalChoice();
        sceneBossFight();
        sceneEnding();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — TRUTH BURNS  |  Eryn Voss");
        Printer.slowPrint("The old cathedral hides the Sanctum beneath its stones.");
        Printer.slowPrint("The figure in white robes told you where to find the entrance.");
        Printer.slowPrint("Two days in the catacombs. Now you stand at the sealed archway.");
        Printer.slowPrint("Protected by wards you recognize — your own designs, actually.");
        Printer.pause(500);
        Printer.slowPrint("The wards recognize you. They part like they've been waiting.");
        InputHandler.waitForEnter();
    }

    private void sceneTheSanctum() {
        Printer.printDivider();
        Printer.slowPrint("The Sanctum is older than anything else in Valdenmere.");
        Printer.slowPrint("Ancient architecture. Candlelit. Filled with magic so deep it tastes like iron.");
        Printer.pause(400);
        System.out.println();
        System.out.println("  What do you notice first?");
        System.out.println("  1. The warnings carved in Old Arcane: DO NOT UNBIND THE ARCHITECT.");
        System.out.println("  2. The feeling of being watched by the very stones.");
        System.out.println("  3. A sound like breathing from the chamber ahead.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> Printer.slowPrint("DO NOT TRUST THE VOICE THAT SOUNDS LIKE YOUR OWN FAITH.");
            case 2 -> {
                Printer.slowPrint("The entire structure is alive with magic. Conscious. Watching.");
                Printer.slowPrint("You've walked into something far stranger than you expected.");
            }
            case 3 -> Printer.slowPrint("Like gills. Like a heartbeat. Something waking up.");
        }
        InputHandler.waitForEnter();
    }

    private void sceneTheSpellbooks() {
        Printer.printDivider();
        Printer.slowPrint("The inner chamber is Valdros's study. His shrine. His tomb.");
        Printer.slowPrint("Books everywhere. And on a pedestal:");
        Printer.slowPrint("Your spellbooks. All seven. Preserved. Protected. Cherished.");
        Printer.pause(600);
        Printer.slowPrint("You pick one up. Your handwriting from three years ago.");
        Printer.slowPrint("The work that got you exiled for spreading dangerous lies.");
        System.out.println();
        System.out.println("  Do you read Valdros's marginal notes?");
        System.out.println("  1. Yes. See what he really thought.");
        System.out.println("  2. No — reclaim your books and move on.");

        int choice = InputHandler.getInt(1, 2);
        if (choice == 1) {
            Printer.slowPrint("His responses span a decade. Some furious. Most admiring.");
            Printer.slowPrint("\"You were right. You were right. You were right.\"");
            Printer.slowPrint("The last note: \"I'm sorry. The only way to survive your truth");
            Printer.slowPrint("was to burn it. I created only more fire.\"");
            story.setFlag("eryn_read_dael_responses", true);
        } else {
            Printer.slowPrint("You gather the books. Heavy with stolen time.");
            eryn.heal(20);
        }

        InputHandler.waitForEnter();

        Printer.slowPrint("A guardian construct activates — the Sanctum defending itself!");
        boolean won = CombatSystem.startCombat(eryn, Enemy.corruptedConstruct());
        if (!won) {
            handleGameOver();
            return;
        }
        eryn.restoreMana(25);
        InputHandler.waitForEnter();
    }

    private void sceneStageTwo() {
        Printer.printDivider();
        Printer.slowPrint("Stage Two is not a spell. It's a device. A conduit.");
        Printer.slowPrint("Feeds to every major city in Aethermoor simultaneously.");
        Printer.pause(500);
        Printer.slowPrint("Valdros stands in front of it like a parent with a dying child.");
        Printer.slowPrint("\"You figured it out,\" he says, turning. His face is exhausted. But certain.");
        System.out.println();
        System.out.println("  How do you respond?");
        System.out.println("  1. \"Why? After everything — why?\"");
        System.out.println("  2. \"Stage Two will kill hundreds of thousands.\"");
        System.out.println("  3. Say nothing. Let him explain himself.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("\"Because you were right about everything,\" he says.");
                Printer.slowPrint("\"And because being right about the Greying meant understanding");
                Printer.slowPrint("there's no fixing it. Only ending it.\"");
            }
            case 2 -> {
                Printer.slowPrint("\"It will save millions by ending the suffering,\" he replies.");
                Printer.slowPrint("\"That's not salvation. That's surrender.\" you say.");
                Printer.slowPrint("\"Perhaps,\" he admits. \"But it's merciful.\"");
            }
            case 3 -> {
                Printer.slowPrint("He explains for several minutes. Methodical. Certain.");
                Printer.slowPrint("He sounds like someone who's made peace with an unbearable decision.");
            }
        }

        Printer.pause(400);
        Printer.slowPrint("\"The activation is in six hours,\" Valdros says.");
        Printer.slowPrint("\"Stage Two with the Pyre Conduit in Valdenmere will cleanse Aethermoor. Finally.\"");
        System.out.println();
        System.out.println("  How do you stop this?");
        System.out.println("  1. Destroy the device immediately.");
        System.out.println("  2. Fight Valdros one-on-one. Let the victor decide.");
        System.out.println("  3. Appeal to him. Maybe something can still be saved.");

        int action = InputHandler.getInt(1, 3);
        switch (action) {
            case 1 -> Printer.slowPrint("You reach for magic. Valdros blocks you effortlessly.");
            case 2 -> {
                Printer.slowPrint("\"Just us,\" you say. Valdros draws his staff — one you designed.");
                story.setFlag("eryn_fought_dael_one_on_one", true);
            }
            case 3 -> {
                Printer.slowPrint("\"You taught me that knowledge is sacred,\" you say.");
                Printer.slowPrint("\"You were right. But this isn't the answer.\"");
                Printer.slowPrint("Something flickers. Then hardens. \"Stop me, if you can.\"");
                story.setFlag("eryn_appealed_to_dael", true);
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneFinalChoice() {
        Printer.printDivider();
        Printer.slowPrint("You have three uses of Raw Power remaining. Uncontrolled. Dangerous.");
        Printer.slowPrint("You could burn this Sanctum down. Burn Valdros. Burn yourself.");
        System.out.println();
        System.out.println("  How do you fight?");
        System.out.println("  1. Use all Raw Power. Burn it all down.");
        System.out.println("  2. Fight smart. Disarm him instead of destroying.");
        System.out.println("  3. Let him strike first. Maybe he'll hesitate.");

        int approach = InputHandler.getInt(1, 3);
        switch (approach) {
            case 1 -> {
                Printer.slowPrint("The raw power inside you has been screaming to be used.");
                Printer.slowPrint("This time you let it. All of it.");
                story.setFlag("eryn_used_raw_power_on_valdros", true);
            }
            case 2 -> {
                Printer.slowPrint("You focus. He's powerful but old. You're younger. Faster.");
                story.setFlag("eryn_fought_dael_carefully", true);
            }
            case 3 -> {
                Printer.slowPrint("You stand there. Inviting him to move first.");
                Printer.slowPrint("In that moment of commitment, maybe there's a window.");
                story.setFlag("eryn_let_dael_strike_first", true);
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneBossFight() {
        Printer.printDivider();
        Printer.slowPrint("⚔️   ERYN VOSS  vs  HIGH ARCANIST DAEL");
        Printer.slowPrint("    The Architect of the Greying.");
        Printer.printDivider();
        InputHandler.waitForEnter();

        boolean won = CombatSystem.startCombat(eryn, Enemy.bossValdros());
        if (!won) {
            handleGameOver();
            return;
        }
    }

    private void sceneEnding() {
        Printer.printDivider();
        Printer.slowPrint("Stage Two goes dark as Valdros falls.");
        Printer.pause(500);

        if (story.getFlag("eryn_used_raw_power_on_valdros")) {
            Printer.slowPrint("But the Raw Power tore through you too.");
            Printer.slowPrint("The world dissolves into black.");
            Printer.pause(600);
            Printer.slowPrint("You wake in a healer's cot. Darkness is your world now.");
            Printer.slowPrint("Your magic is gone. The neural pathways burned out forever.");
            Printer.pause(500);
            Printer.slowPrint("By the second week, you've started writing.");
            Printer.slowPrint("Everything you know. Every secret Valdros kept.");
            Printer.slowPrint("Other scribes read your words and write them down word-perfect.");
            Printer.pause(500);
            Printer.slowPrint("The Circle reforms. Without you at its head.");
            Printer.slowPrint("But you speak truth. And the order listens.");
            Printer.printDivider();
            Printer.printBox("THE BLIND SCHOLAR");
            Printer.printBox("She gave her sight for the truth. It was enough.");
        } else {
            Printer.slowPrint("Valdros is still alive. Barely. Defeated.");
            Printer.slowPrint("\"Finish it,\" he whispers.");
            Printer.slowPrint("You don't. You bind him. Drag him to the surface.");
            Printer.pause(400);
            Printer.slowPrint("The trials last three weeks. Valdros is imprisoned. But alive.");
            Printer.pause(500);
            Printer.slowPrint("A month later, they offer you leadership of the Circle.");
            Printer.slowPrint("You refuse. You create something new instead: Keeper of Accountability.");
            Printer.slowPrint("Someone to watch the watchers. Someone who won't let them become what they were.");
            Printer.printDivider();
            Printer.printBox("THE REFORMER");
            Printer.printBox("Power doesn't corrupt those who never wanted it.");
        }

        Printer.printDivider();
        Printer.slowPrint("═══════════════════════════════════════════════════════════════");
        Printer.slowPrint("               ERYN VOSS — STORY COMPLETE");
        Printer.slowPrint("═══════════════════════════════════════════════════════════════");
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The truth dies with Eryn Voss. Aethermoor burns in silence.");
        System.exit(0);
    }
}