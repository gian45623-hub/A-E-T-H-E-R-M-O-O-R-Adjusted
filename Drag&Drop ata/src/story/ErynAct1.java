package story;

import characters.Mage;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class ErynAct1 {

    private Mage eryn;
    private StoryManager story;

    public ErynAct1(Mage eryn, StoryManager story) {
        this.eryn = eryn;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneAshenAttack();
        sceneAftermath();
        sceneSevikMeeting();
        sceneMageTower();
        sceneActEnd();
    }

    // ─── SCENE 1: Duskwall Intro ───────────────────────────────────────────────
    private void sceneIntro() {
        Printer.printTitle("ACT I — THE SPARK  |  Eryn Voss");
        Printer.slowPrint("The town of Duskwall smells like ash and old fish.");
        Printer.slowPrint("You've lived here for three years. You do card tricks for coin.");
        Printer.slowPrint("You used to rewrite the laws of arcane theory. Now you guess which hand the copper is in.");
        Printer.pause(600);
        Printer.slowPrint("Tonight, the sky above Duskwall has gone the color of a bruise.");
        InputHandler.waitForEnter();

        Printer.slowPrint("A child tugs your sleeve near the market stalls.");
        Printer.slowPrint("\"Mage lady! My mum says supper's ready and you can have some if you do the fire trick!\"");
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Do the fire trick. A warm meal sounds good.");
        System.out.println("  2. Decline politely. You don't do magic anymore.");
        System.out.println("  3. Ask the child about the strange sky.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You snap your fingers. A small flame dances on your palm.");
                Printer.slowPrint("The child squeals with delight. You feel something stir in your chest.");
                Printer.slowPrint("You eat well tonight. Lamb stew. First hot meal in a week.");
                story.setFlag("eryn_used_magic_willingly", true);
            }
            case 2 -> {
                Printer.slowPrint("\"Sorry, little one. I'm just a card lady now.\"");
                Printer.slowPrint("The child looks disappointed and runs off.");
                Printer.slowPrint("You feel the familiar hollow ache settle back in.");
            }
            case 3 -> {
                Printer.slowPrint("The child squints upward. \"Mum says it's just weather. But it feels wrong.\"");
                Printer.slowPrint("Smart kid. You watch the bruised sky for a long moment.");
                story.setFlag("eryn_noticed_sky", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 2: Ashen Attack ────────────────────────────────────────────────
    private void sceneAshenAttack() {
        Printer.printDivider();
        Printer.slowPrint("It happens just after midnight.");
        Printer.pause(400);
        Printer.slowPrint("Screaming. Then a sound like wind — but wrong. Like wind through a corpse.");
        Printer.slowPrint("You stumble out of your rented cot to find the market in chaos.");
        Printer.slowPrint("Three Ashen have broken through the town's east wall.");
        Printer.slowPrint("They move like puppets — hollow, purposeful, wrong.");
        Printer.pause(500);
        Printer.slowPrint("A woman is cornered against a grain cart. There's no one else.");
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. You reach for magic. Whatever's left — use it.");
        System.out.println("  2. Grab a weapon from a nearby stall. Fight without magic.");
        System.out.println("  3. Scream for the town guard.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You reach inside. The place where your magic used to live.");
                Printer.slowPrint("It's not empty. It's been waiting.");
                Printer.slowPrint("It tears out of you like something STARVED.");
            }
            case 2 -> {
                Printer.slowPrint("You grab an iron poker from a blacksmith's stall.");
                Printer.slowPrint("You get halfway there before your hands start to glow.");
                Printer.slowPrint("The magic comes anyway. It was never really gone.");
            }
            case 3 -> {
                Printer.slowPrint("You scream. No one comes. They're all hiding.");
                Printer.slowPrint("The Ashen turns toward the woman.");
                Printer.slowPrint("Your hands burst into flame before you even decide to act.");
            }
        }

        Printer.pause(400);
        Printer.slowPrint("COMBAT: Ashen Soldiers attack!");
        InputHandler.waitForEnter();

        // Fight 1: Two Ashen Soldiers
        boolean won1 = CombatSystem.startCombat(eryn, Enemy.ashenSoldier());
        if (!won1) { handleGameOver(); return; }
        eryn.restoreMana(20);
        Printer.slowPrint("One falls. Two more remain.");
        boolean won2 = CombatSystem.startCombat(eryn, Enemy.ashenSoldier());
        if (!won2) { handleGameOver(); return; }

        Printer.pause(400);
        Printer.slowPrint("The last Ashen crumbles to grey dust.");
        Printer.slowPrint("You're standing in the middle of Duskwall's market, breathing hard.");
        Printer.slowPrint("Your hands are still smoking.");
        Printer.slowPrint("You forgot how good this felt. That's terrifying.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 3: Aftermath & Journal ─────────────────────────────────────────
    private void sceneAftermath() {
        Printer.printDivider();
        Printer.slowPrint("In the rubble left by the Ashen, something catches your eye.");
        Printer.slowPrint("A half-burned journal. Arcane Circle binding. The embossed seal of the High Council.");
        Printer.pause(300);
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Pick it up immediately.");
        System.out.println("  2. Leave it. You want nothing to do with the Circle.");
        System.out.println("  3. Study the scene first — where did it come from?");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You snatch it up before the dust settles.");
            }
            case 2 -> {
                Printer.slowPrint("You turn away. You take three steps.");
                Printer.slowPrint("Then you turn back and pick it up anyway.");
                Printer.slowPrint("You were never good at leaving things alone.");
            }
            case 3 -> {
                Printer.slowPrint("You trace the path of destruction. The Ashen came from the east.");
                Printer.slowPrint("The journal is near the leader — the biggest one.");
                Printer.slowPrint("As if it was being carried. As if it was important to someone.");
                story.setFlag("eryn_investigated_scene", true);
            }
        }

        Printer.pause(300);
        Printer.slowPrint("You crack the journal open. The script is in Arcane Cipher.");
        Printer.slowPrint("Your old speciality. The words come back like a bad dream.");
        Printer.pause(500);
        Printer.slowPrint("Most pages are burned. But one entry survives:");
        Printer.pause(400);
        Printer.printBox("\"...THE ARCHITECT'S WORK PROCEEDS. DUSKWALL IS A TEST.");
        Printer.printBox(" THE GREYING WAS NOT AN ACCIDENT. IT NEVER WAS.\"");
        Printer.pause(600);
        Printer.slowPrint("Your hands are trembling. Not from the magic.");
        Printer.slowPrint("You were right. You were right all along.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 4: Sevik the Broker ────────────────────────────────────────────
    private void sceneSevikMeeting() {
        Printer.printDivider();
        Printer.slowPrint("The next morning, a man finds you at the only tavern still standing.");
        Printer.slowPrint("He's well-dressed for Duskwall. Too well-dressed.");
        Printer.slowPrint("He slides into the seat across from you without being invited.");
        Printer.pause(300);
        Printer.slowPrint("\"Eryn Voss. Exiled scholar. Former prodigy.\"");
        Printer.slowPrint("He sets a small coin on the table. Arcane Circle mint. Rare.");
        Printer.slowPrint("\"My name is Sevik. I know things. I sell things. Right now, I'm buying.\"");
        System.out.println();
        System.out.println("  How do you respond?");
        System.out.println("  1. \"What do you want?\" (Cautious)");
        System.out.println("  2. \"How do you know my name?\" (Suspicious)");
        System.out.println("  3. Stand up to leave.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("Sevik smiles. \"Straight to it. I like you already.\"");
            }
            case 2 -> {
                Printer.slowPrint("\"I know everyone's name who might be useful.\" He shrugs.");
                Printer.slowPrint("\"You were impressive last night. Word travels fast in small towns.\"");
                story.setFlag("eryn_suspicious_of_sevik", true);
            }
            case 3 -> {
                Printer.slowPrint("\"There's a name in that journal you found,\" Sevik says quietly.");
                Printer.slowPrint("You stop. Sit back down.");
                Printer.slowPrint("He smiles like he expected that.");
                story.setFlag("eryn_suspicious_of_sevik", true);
            }
        }

        Printer.pause(300);
        Printer.slowPrint("Sevik leans forward. \"There's an old mage tower two days east. Collapsed in the Greying.\"");
        Printer.slowPrint("\"Someone stole something from it before it fell. A relic. I want it back.\"");
        Printer.slowPrint("\"Retrieve it for me, and I'll give you information worth more than that journal.\"");
        Printer.slowPrint("\"I know who the Architect is.\"");
        Printer.pause(500);
        System.out.println();
        System.out.println("  Do you take the job?");
        System.out.println("  1. Yes. Information is what you need.");
        System.out.println("  2. Ask for more details before agreeing.");
        System.out.println("  3. Refuse. This feels like a trap.");

        int deal = InputHandler.getInt(1, 3);

        switch (deal) {
            case 1 -> {
                Printer.slowPrint("\"Done.\" You shake his hand. His grip is practiced. Careful.");
            }
            case 2 -> {
                Printer.slowPrint("He answers every question smoothly. Too smoothly.");
                Printer.slowPrint("You agree anyway. What choice do you have?");
                story.setFlag("eryn_suspicious_of_sevik", true);
            }
            case 3 -> {
                Printer.slowPrint("\"Then go alone, with nothing.\" Sevik shrugs and stands.");
                Printer.slowPrint("\"The tower's called Vel Maren. The relic glows blue. Good luck.\"");
                Printer.slowPrint("He leaves. You follow the lead anyway.");
                story.setFlag("eryn_refused_sevik_deal", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 5: The Mage Tower ──────────────────────────────────────────────
    private void sceneMageTower() {
        Printer.printDivider();
        Printer.slowPrint("Vel Maren stands at the edge of a dead forest — or it leans, rather.");
        Printer.slowPrint("Half the tower has collapsed into itself. The other half defies gravity out of spite.");
        Printer.slowPrint("The air crackles with unstable arcane energy. Constructs patrol the rubble.");
        Printer.slowPrint("Someone has been here recently. The footprints in the ash are fresh.");
        InputHandler.waitForEnter();

        // Combat: Corrupted Constructs
        Printer.slowPrint("A Corrupted Construct lurches from the shadows!");
        boolean won = CombatSystem.startCombat(eryn, Enemy.corruptedConstruct());
        if (!won) { handleGameOver(); return; }
        eryn.restoreMana(15);

        Printer.slowPrint("You push deeper into the ruin. The relic glows from a collapsed library.");
        Printer.slowPrint("You find it easily — a small blue crystal, pulsing steadily.");
        Printer.pause(400);

        System.out.println();
        System.out.println("  You also notice a hidden door behind a fallen bookshelf.");
        System.out.println("  1. Investigate the hidden door first.");
        System.out.println("  2. Take the relic and leave quickly.");

        int choice = InputHandler.getInt(1, 2);

        if (choice == 1 || story.getFlag("eryn_investigated_scene")) {
            Printer.slowPrint("Behind the door: a chamber untouched by the collapse.");
            Printer.slowPrint("Maps. Letters. And one document that makes your blood run cold.");
            Printer.pause(400);
            Printer.printBox("A detailed map — Valdenmere. The old Arcane Circle headquarters.");
            Printer.printBox("Marked in red: \"STAGE TWO BEGINS HERE.\"");
            Printer.pause(500);
            Printer.slowPrint("The Architect isn't hiding. They're preparing.");
            story.setFlag("eryn_found_stage_two_map", true);
        } else {
            Printer.slowPrint("You grab the relic. The tower groans around you.");
            Printer.slowPrint("You run. Something scrapes the wall behind you as you go.");
            Printer.slowPrint("You don't look back.");
        }

        // Second combat on exit
        Printer.slowPrint("A Rogue Arcanist blocks the exit!");
        boolean won2 = CombatSystem.startCombat(eryn, Enemy.rogueArcanist());
        if (!won2) { handleGameOver(); return; }

        InputHandler.waitForEnter();
    }

    // ─── SCENE 6: Act End ─────────────────────────────────────────────────────
    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You return to Duskwall with the relic. Sevik is waiting at the gate.");
        Printer.slowPrint("He takes it from your hands without inspecting it. Like he already knew what was inside.");
        Printer.pause(400);
        Printer.slowPrint("\"The Architect,\" you say. \"You promised a name.\"");
        Printer.pause(300);
        Printer.slowPrint("Sevik looks at you for a long moment.");
        Printer.slowPrint("\"Valdros — The Architect,\" he says. \"Your old master. The man who exiled you.\"");
        Printer.slowPrint("\"He cast the Greying, Eryn. All of it. On purpose.\"");
        Printer.pause(600);
        Printer.slowPrint("You already knew. Some part of you always knew.");
        Printer.slowPrint("The journal. The map. The constructs defending a tower that should be abandoned.");
        Printer.pause(400);
        Printer.slowPrint("\"Where is he?\" you ask.");
        Printer.slowPrint("Sevik smiles. For the first time, it doesn't quite reach his eyes.");
        Printer.slowPrint("\"Valdenmere. Where else?\"");
        Printer.pause(500);
        Printer.slowPrint("He walks away. You watch him go.");
        Printer.slowPrint("The relic in his hands pulses once — then goes dark.");
        Printer.pause(400);
        Printer.slowPrint("You head for Valdenmere.");
        Printer.slowPrint("You have a score to settle with the man who burned your books.");
        Printer.printDivider();
        Printer.printBox("ACT I COMPLETE — THE SPARK");
        Printer.printBox("Eryn Voss sets her sights on Valdenmere.");

        if (story.getFlag("eryn_found_stage_two_map")) {
            Printer.printBox("★ Secret: You know about Stage Two. This will matter.");
        }
        Printer.printDivider();
        InputHandler.waitForEnter();

        story.advanceAct();
    }

    private void handleGameOver() {
        Printer.slowPrint("Perhaps the story ends differently another day.");
        System.exit(0);
    }
}
