package story;

import characters.Priest;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SoliaAct1 {

    private Priest solia;
    private StoryManager story;

    public SoliaAct1(Priest solia, StoryManager story) {
        this.solia = solia;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneRefugeeCamp();
        sceneMedallion();
        sceneValdenmereStreets();
        sceneEscapeHelper();
        sceneActEnd();
    }

    // ─── SCENE 1: The Camp ────────────────────────────────────────────────────
    private void sceneIntro() {
        Printer.printTitle("ACT I — THE HOLLOW SAINT  |  Solia Ren");
        Printer.slowPrint("The refugee camp outside Valdenmere's walls holds four hundred people.");
        Printer.slowPrint("You know most of them by name now.");
        Printer.slowPrint("Maren, who lost her son in the first Greying wave.");
        Printer.slowPrint("Old Pell, who insists his wound is fine when it clearly isn't.");
        Printer.slowPrint("The twins from Ashford — they won't speak yet. That's okay. You wait.");
        Printer.pause(500);
        Printer.slowPrint("You are not a licensed healer. The Sacred Flame revoked that with your title.");
        Printer.slowPrint("You help anyway. What else is there to do?");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 2: Morning in the Camp ────────────────────────────────────────
    private void sceneRefugeeCamp() {
        Printer.printDivider();
        Printer.slowPrint("Morning. You're changing the bandages on Old Pell's leg when he grabs your wrist.");
        Printer.slowPrint("\"Ren.\" His voice is urgent. \"There was a priest here last night.\"");
        Printer.slowPrint("\"Sacred Flame robes. He came to 'bless' the sick.\" He pauses.");
        Printer.slowPrint("\"Three people he touched are worse this morning. Much worse.\"");
        Printer.pause(400);
        System.out.println();
        System.out.println("  What do you do first?");
        System.out.println("  1. Go check on the three affected patients immediately.");
        System.out.println("  2. Ask Pell to describe the priest.");
        System.out.println("  3. Find the camp leader and report it.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You find them fast. Grey veins spreading under the skin. Greying corruption.");
                Printer.slowPrint("Not from exposure. This was introduced. Deliberately.");
                Printer.slowPrint("You spend two hours stabilizing them. Your hands shake.");
                story.setFlag("solia_saw_corruption_firsthand", true);
            }
            case 2 -> {
                Printer.slowPrint("\"Older man. Kind eyes, I thought. White robes with a gold trim.\"");
                Printer.slowPrint("\"But his medallion — it was dark. Not golden like yours was.\"");
                Printer.slowPrint("\"Pulsed like a heartbeat. Wrong-colored.\"");
                story.setFlag("solia_has_priest_description", true);
            }
            case 3 -> {
                Printer.slowPrint("The camp leader listens. He's useless. \"Probably just sick from the water.\"");
                Printer.slowPrint("You're on your own. As always.");
            }
        }

        Printer.pause(300);
        Printer.slowPrint("The three sick refugees improve slowly. You do what you can.");
        Printer.slowPrint("But you can't shake what Pell told you. A priest. A dark medallion.");
        Printer.slowPrint("Deliberately spreading the Greying to vulnerable people.");
        Printer.slowPrint("That's not an accident. That's a test.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 3: The Medallion ───────────────────────────────────────────────
    private void sceneMedallion() {
        Printer.printDivider();
        Printer.slowPrint("That afternoon, a dying man presses something into your hand.");
        Printer.slowPrint("A Sacred Flame medallion. Tarnished. Pulsing with wrong energy.");
        Printer.slowPrint("\"...took it,\" he whispers. \"From the priest. Didn't... want him to have it.\"");
        Printer.slowPrint("He doesn't wake up again.");
        Printer.pause(600);
        Printer.slowPrint("You hold the medallion up to the light. The sacred symbol is there.");
        Printer.slowPrint("But threaded through it — like veins through stone — is Greying corruption.");
        Printer.slowPrint("Someone has fused holy relics with dark energy.");
        Printer.pause(400);
        System.out.println();
        System.out.println("  This changes everything. What do you do?");
        System.out.println("  1. Keep the medallion as evidence. Go to the city.");
        System.out.println("  2. Destroy it. It feels wrong to touch.");
        System.out.println("  3. Try to purify it yourself, right now.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You wrap it in cloth and tuck it into your pack.");
                Printer.slowPrint("Evidence. If you can find someone to believe you.");
                story.setFlag("solia_has_medallion", true);
            }
            case 2 -> {
                Printer.slowPrint("You press it between two stones and crush it.");
                Printer.slowPrint("The dark energy disperses with a sound like a sigh.");
                Printer.slowPrint("You feel better. Then worse — you needed that proof.");
            }
            case 3 -> {
                Printer.slowPrint("You close your hands around it. Pray.");
                Printer.slowPrint("The corruption resists. But something responds — faintly.");
                Printer.slowPrint("You can't purify it fully. But you can feel how it was done.");
                Printer.slowPrint("This is sophisticated work. This required access to the Vault.");
                story.setFlag("solia_understands_corruption_method", true);
            }
        }

        // Combat: corrupted priest attacks the camp
        Printer.pause(400);
        Printer.slowPrint("\nA Corrupted Priest emerges from the camp's edge — sent to retrieve the medallion!");
        InputHandler.waitForEnter();

        boolean won = CombatSystem.startCombat(solia, Enemy.corruptedPriest());
        if (!won) { handleGameOver(); return; }
        solia.restoreMana(20);

        Printer.slowPrint("The corrupted priest falls. Before losing consciousness he rasps:");
        Printer.slowPrint("\"...Aldran will finish what the Greying started. All of it. All of them.\"");
        Printer.pause(400);
        Printer.slowPrint("Aldran.");
        Printer.slowPrint("Your mentor. Your High Priest. The man who taught you that faith is love.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 4: Into Valdenmere ─────────────────────────────────────────────
    private void sceneValdenmereStreets() {
        Printer.printDivider();
        Printer.slowPrint("You slip into Valdenmere through the merchant gates.");
        Printer.slowPrint("Without your robes and title, no one looks twice.");
        Printer.slowPrint("That used to bother you. Now it's useful.");
        Printer.pause(300);
        Printer.slowPrint("You head toward the Sacred Flame's district — the Ember Quarter.");
        Printer.slowPrint("Aldran's personal chambers are there. And so is the order's archive.");
        Printer.pause(300);
        System.out.println();
        System.out.println("  How do you approach?");
        System.out.println("  1. Pose as a visiting cleric. Bluff your way in.");
        System.out.println("  2. Enter through the servants' entrance — you know the layout.");
        System.out.println("  3. Watch and wait. Learn the patrol patterns first.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You straighten your back. Lift your chin. Become Sister Ren again.");
                Printer.slowPrint("The novice at the door barely looks at you. \"Sister. The archive is unlocked.\"");
                Printer.slowPrint("You feel sick pretending. But you're in.");
            }
            case 2 -> {
                Printer.slowPrint("The kitchen door is exactly where you remember.");
                Printer.slowPrint("The cook on duty is new. He doesn't recognize you.");
                Printer.slowPrint("\"Delivering linens,\" you say. He nods. You're in.");
            }
            case 3 -> {
                Printer.slowPrint("Three hours. You learn the guards rotate every hour.");
                Printer.slowPrint("You spot a priest acting strange — hurrying, nervous.");
                Printer.slowPrint("You follow him. He leads you to a side entrance. You slip through behind him.");
                story.setFlag("solia_observed_nervous_priest", true);
            }
        }

        Printer.pause(300);
        Printer.slowPrint("The archive tells you everything.");
        Printer.slowPrint("Aldran's notes. The corrupted relics. A weapon called the Pyre Conduit.");
        Printer.slowPrint("Pages of calculations — how much Greying energy is needed.");
        Printer.slowPrint("What radius of effect. What... yield.");
        Printer.pause(600);
        Printer.slowPrint("This isn't a weapon to fight the Ashen.");
        Printer.slowPrint("This is a weapon to cleanse Aethermoor of anyone touched by the Greying.");
        Printer.slowPrint("After ten years... that's almost everyone.");

        Printer.pause(400);
        Printer.slowPrint("Guards. They've found you in the archive.");
        InputHandler.waitForEnter();

        boolean won = CombatSystem.startCombat(solia, Enemy.corruptedPriest());
        if (!won) { handleGameOver(); return; }
        solia.restoreMana(15);

        InputHandler.waitForEnter();
    }

    // ─── SCENE 5: The Escape & Eryn ──────────────────────────────────────────
    private void sceneEscapeHelper() {
        Printer.printDivider();
        Printer.slowPrint("You run. The Ember Quarter's streets blur past.");
        Printer.slowPrint("More guards behind you. Faster than you expected.");
        Printer.pause(300);
        Printer.slowPrint("In the mage district across the boulevard — chaos.");
        Printer.slowPrint("A woman is fleeing a group of robed arcanists.");
        Printer.slowPrint("She moves like someone who knows exactly how outmatched she is");
        Printer.slowPrint("and doesn't care.");
        Printer.pause(400);
        System.out.println();
        System.out.println("  Your paths are about to collide. What do you do?");
        System.out.println("  1. Help her — create a distraction so she can escape.");
        System.out.println("  2. Use the commotion to slip away unnoticed.");

        int choice = InputHandler.getInt(1, 2);

        if (choice == 1) {
            Printer.slowPrint("You step into the alley mouth and unleash a Smite at the nearest arcanist.");
            Printer.slowPrint("They spin toward you. The woman glances back — locks eyes with you.");
            Printer.slowPrint("A fraction of a second. Something like gratitude. Then she's gone.");
            Printer.slowPrint("You run the other direction before they regroup.");
            story.setFlag("solia_helped_eryn", true);
        } else {
            Printer.slowPrint("You dart through a side alley while both groups chase each other.");
            Printer.slowPrint("Effective. Efficient. A little cold.");
            Printer.slowPrint("You don't look back.");
        }

        Printer.pause(400);
        Printer.slowPrint("You make it out of the city as the sun sets.");
        Printer.slowPrint("Safe. Alone. With the weight of what you found in that archive.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 6: Act End ─────────────────────────────────────────────────────
    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You sit outside Valdenmere's walls in the dark.");
        Printer.slowPrint("The Sacred Flame archive is clear in your mind. The numbers. The range.");
        Printer.slowPrint("The Pyre Conduit, when activated, will kill hundreds of thousands.");
        Printer.slowPrint("Maybe more. Maybe everyone.");
        Printer.pause(500);
        Printer.slowPrint("And Aldran believes he's saving what's left.");
        Printer.pause(300);
        Printer.slowPrint("That's the part you can't let go of.");
        Printer.slowPrint("He's not a monster. He's a man who lost his faith in everything");
        Printer.slowPrint("except fire.");
        Printer.pause(500);
        Printer.slowPrint("You lost your faith too. But you chose people over certainty.");
        Printer.slowPrint("Someone has to stop him. There's no one else who knows.");
        Printer.slowPrint("There's no one else who understands what the Vault holds.");
        Printer.pause(400);
        Printer.slowPrint("You stand. Brush the dust from your clothes.");
        Printer.slowPrint("Walk back toward the city.");

        // Faith loss for discovering the truth
        Printer.pause(300);
        solia.loseFaith(2);
        Printer.slowPrint("What Aldran is doing... it shakes something deep inside you.");

        Printer.printDivider();
        Printer.printBox("ACT I COMPLETE — THE HOLLOW SAINT");
        Printer.printBox("Solia Ren returns to face her mentor.");

        if (story.getFlag("solia_helped_eryn")) {
            Printer.printBox("★ You helped a stranger escape. Kindness remembered.");
        }
        if (story.getFlag("solia_understands_corruption_method")) {
            Printer.printBox("★ You understand how the relics are corrupted. This will matter.");
        }
        Printer.printDivider();
        InputHandler.waitForEnter();
        story.advanceAct();
    }

    private void handleGameOver() {
        Printer.slowPrint("Solia Ren's light goes out quietly.");
        System.exit(0);
    }
}
