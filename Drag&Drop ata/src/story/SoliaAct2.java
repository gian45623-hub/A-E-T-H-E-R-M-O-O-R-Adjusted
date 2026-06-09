package story;

import characters.Priest;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SoliaAct2 {

    private Priest solia;
    private StoryManager story;

    public SoliaAct2(Priest solia, StoryManager story) {
        this.solia = solia;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneReturningToTheOrder();
        sceneOldFriends();
        sceneTheVault();
        sceneThePyreConduit();
        sceneAldranConfrontation();
        sceneActEnd();
    }

    // ─── SCENE 1: Returning ───────────────────────────────────────────────────
    private void sceneIntro() {
        Printer.printTitle("ACT II — THE BROKEN ALTAR  |  Solia Ren");
        Printer.slowPrint("You left the Sacred Flame because you stopped believing.");
        Printer.slowPrint("Not in the god — you're still not sure about that part.");
        Printer.slowPrint("In the people running the institution. In Aldran.");
        Printer.slowPrint("In the version of faith that looks like a weapon.");
        Printer.pause(500);
        Printer.slowPrint("You're going back anyway.");
        Printer.slowPrint("Because the Pyre Conduit will activate in weeks,");
        Printer.slowPrint("and you're the only person who knows what it actually does.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 2: Reapproaching the Sacred Flame ──────────────────────────────
    private void sceneReturningToTheOrder() {
        Printer.printDivider();
        Printer.slowPrint("The Sacred Flame's Valdenmere chapter house is in the Ember Quarter.");
        Printer.slowPrint("Gold and white banners. The smell of incense you can recognize from two streets away.");
        Printer.slowPrint("It used to feel like coming home.");
        Printer.pause(400);
        Printer.slowPrint("Now it feels like approaching something that used to be safe");
        Printer.slowPrint("and isn't anymore.");
        System.out.println();
        System.out.println("  How do you approach?");
        System.out.println("  1. Walk in openly as a former priestess. Claim you want to return.");
        System.out.println("  2. Observe from outside first. Learn who's here and who's loyal to Aldran.");
        System.out.println("  3. Use a side entrance you know from your years of service here.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("The novice at the door recognizes your face.");
                Printer.slowPrint("\"Sister Ren. We were told you'd left permanently.\"");
                Printer.slowPrint("\"I reconsidered,\" you say.");
                Printer.slowPrint("She steps aside, uncertain. You're in. You're also being watched.");
                story.setFlag("solia_entered_openly", true);
            }
            case 2 -> {
                Printer.slowPrint("Two hours of watching teaches you a lot.");
                Printer.slowPrint("Priests entering freely, leaving quickly. Tight expressions.");
                Printer.slowPrint("Two senior clergy who haven't left the building in days.");
                Printer.slowPrint("And one — Brother Aldric, your old friend — lingering by the gate");
                Printer.slowPrint("like he's waiting for someone. Like he's waiting for you.");
                story.setFlag("solia_observed_chapter_house", true);
            }
            case 3 -> {
                Printer.slowPrint("The garden entrance. Behind the eastern shrine.");
                Printer.slowPrint("Unlocked since you joined as a novice — you used to sneak out for night walks.");
                Printer.slowPrint("It's still unlocked. Some things don't change.");
                Printer.slowPrint("You're inside before anyone knows to watch for you.");
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 3: Old Friends ─────────────────────────────────────────────────
    private void sceneOldFriends() {
        Printer.printDivider();
        Printer.slowPrint("You find two people you trust inside.");
        Printer.pause(300);
        Printer.slowPrint("Brother Aldric — tall, anxious, seven years your junior in the order.");
        Printer.slowPrint("He taught himself the old healing scripts from your notes.");
        Printer.slowPrint("He hugs you before you say a word. That says enough.");
        Printer.pause(400);
        Printer.slowPrint("Sister Vael — sharp, practical, the best administrator the order has.");
        Printer.slowPrint("She was loyal to Aldran for years. Something has changed.");
        Printer.slowPrint("She looks at you with the eyes of someone who has seen something they can't unsee.");
        Printer.pause(300);
        System.out.println();
        System.out.println("  You need information. Who do you talk to first?");
        System.out.println("  1. Aldric — he'll tell you the truth even if it's bad.");
        System.out.println("  2. Vael — she knows the operational details. What's actually happening.");
        System.out.println("  3. Both — split your time between them.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("Aldric speaks in a low rush like he's been holding it for weeks.");
                Printer.slowPrint("\"He's stopped letting us see the sick. Says the Conduit will handle it all.");
                Printer.slowPrint("Three priests questioned the plan. They were sent on 'pilgrimage' overnight.\"");
                Printer.slowPrint("\"They haven't come back, Solia. That was two weeks ago.\"");
                story.setFlag("solia_knows_priests_missing", true);
            }
            case 2 -> {
                Printer.slowPrint("Vael is precise and controlled and clearly terrified.");
                Printer.slowPrint("\"The Conduit is nearly complete. He's moved it to the Vault of the First Flame.\"");
                Printer.slowPrint("\"The calculations he's using, Solia — the yield radius. I've seen the numbers.\"");
                Printer.slowPrint("She can't finish the sentence. She doesn't need to.");
                story.setFlag("solia_knows_conduit_location", true);
            }
            case 3 -> {
                Printer.slowPrint("You split an hour between them. Aldric gives you the human cost.");
                Printer.slowPrint("Vael gives you the technical reality.");
                Printer.slowPrint("Together the picture is complete and deeply frightening.");
                story.setFlag("solia_knows_priests_missing", true);
                story.setFlag("solia_knows_conduit_location", true);
            }
        }

        Printer.pause(400);
        Printer.slowPrint("\"Are you going to stop him?\" Aldric asks.");
        System.out.println();
        System.out.println("  How do you answer?");
        System.out.println("  1. \"Yes. I need your help.\"");
        System.out.println("  2. \"I'm going to try. Stay out of it — keep yourselves safe.\"");
        System.out.println("  3. \"I don't know yet. I need to see what he's built first.\"");

        int answer = InputHandler.getInt(1, 3);
        switch (answer) {
            case 1 -> {
                Printer.slowPrint("Aldric straightens. Vael nods once — the decision made.");
                Printer.slowPrint("\"Tell us what you need,\" she says.");
                story.setFlag("solia_has_allies", true);
            }
            case 2 -> {
                Printer.slowPrint("Aldric looks like he wants to argue. He doesn't.");
                Printer.slowPrint("\"Be careful,\" is all he says. He means it in more ways than one.");
            }
            case 3 -> {
                Printer.slowPrint("\"Then go see,\" Vael says quietly. \"And come back.\"");
                Printer.slowPrint("The way she says it makes you realize they've been waiting for someone to do exactly this.");
            }
        }

        InputHandler.waitForEnter();

        // Combat: Aldran's loyalists notice her
        Printer.slowPrint("Two of Aldran's loyalist priests spot you in the corridor and attack to drive you out!");
        boolean won = CombatSystem.startCombat(solia, Enemy.corruptedPriest());
        if (!won) { handleGameOver(); return; }
        solia.restoreMana(20);
        InputHandler.waitForEnter();
    }

    // ─── SCENE 4: The Vault of the First Flame ────────────────────────────────
    private void sceneTheVault() {
        Printer.printDivider();
        Printer.slowPrint("The Vault of the First Flame is beneath the chapter house.");
        Printer.slowPrint("You've been here twice in your life — both times for sacred ceremonies.");
        Printer.slowPrint("The oldest site of the Sacred Flame. Older than the order itself.");
        Printer.pause(500);
        Printer.slowPrint("The stairs down are guarded now. Four priests at the entrance.");
        Printer.slowPrint("Their medallions are dark. Corrupted. They've been given Aldran's version of faith.");
        System.out.println();
        System.out.println("  How do you get past them?");
        System.out.println("  1. Confront them — demand entry as a former senior priestess.");
        System.out.println("  2. Use a servant's passage — you mapped these tunnels during your training.");
        System.out.println("  3. Ask Aldric or Vael to create a distraction above.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("\"I am Sister Ren of the Third Circle. Stand aside.\"");
                Printer.slowPrint("Your voice doesn't shake. You're surprised.");
                Printer.slowPrint("Two of them hesitate. One steps forward. \"The High Priest said—\"");
                Printer.slowPrint("\"The High Priest answers to the Sacred Flame. As do you. As do I.\"");
                Printer.slowPrint("A long pause. They part. Training runs deep, even now.");
                story.setFlag("solia_bluffed_guards", true);
            }
            case 2 -> {
                Printer.slowPrint("There's a passage behind the reliquary storage — used by novices for centuries.");
                Printer.slowPrint("Aldran's loyalists don't know about it. Why would they? They didn't do chores.");
                Printer.slowPrint("You slip through and emerge below the guard line.");
            }
            case 3 -> {
                if (story.getFlag("solia_has_allies")) {
                    Printer.slowPrint("You signal Aldric. Three minutes later — shouting above.");
                    Printer.slowPrint("All four guards rush upstairs. The door is unattended.");
                    Printer.slowPrint("You have maybe two minutes. You move.");
                } else {
                    Printer.slowPrint("You're alone. You'll have to find another way.");
                    Printer.slowPrint("You fall back to the servant's passage instead.");
                }
            }
        }

        Printer.pause(400);
        Printer.slowPrint("The Vault is not what it was.");
        Printer.pause(300);
        Printer.slowPrint("The ancient golden altar has been shoved to the corner.");
        Printer.slowPrint("In its place: a towering lattice of corrupted relics and arcane conduits.");
        Printer.slowPrint("The air smells of burnt faith.");
        Printer.pause(500);
        Printer.slowPrint("It is enormous. It is almost complete.");
        Printer.slowPrint("And it is humming — a sound like ten thousand voices praying in the wrong direction.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 5: Understanding the Conduit ───────────────────────────────────
    private void sceneThePyreConduit() {
        Printer.printDivider();
        Printer.slowPrint("You walk around it slowly. You don't touch it.");
        Printer.slowPrint("Your understanding of relic corruption — from the medallion, from the archive —");
        Printer.slowPrint("gives you enough to read what you're looking at.");
        Printer.pause(400);
        Printer.slowPrint("The Pyre Conduit works by amplifying Greying energy through sacred geometry.");
        Printer.slowPrint("Holy sites as lenses. The First Flame as the source.");
        Printer.slowPrint("When activated, it will send a purifying wave outward from Valdenmere.");
        Printer.pause(300);
        Printer.slowPrint("'Purifying' in Aldran's definition: burning away everything touched by Greying corruption.");
        Printer.slowPrint("After ten years, trace amounts of Greying energy are in nearly every living person.");
        Printer.pause(600);
        Printer.slowPrint("This is not a weapon.");
        Printer.slowPrint("This is a funeral pyre for a kingdom.");
        Printer.pause(400);
        System.out.println();
        System.out.println("  You need to understand it fully before you can stop it. How do you proceed?");
        System.out.println("  1. Study the conduit's architecture — look for a weakness or fail-safe.");
        System.out.println("  2. Find Aldran's notes nearby — he would have documented everything.");
        System.out.println("  3. Touch the conduit to feel the magic directly. It's risky but fast.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You move methodically around the structure.");
                Printer.slowPrint("There — a central binding rune. The keystone.");
                Printer.slowPrint("Destroy that and the whole structure collapses. Vault and all.");
                Printer.slowPrint("Or — redirect it, if you know the right counter-inscription.");
                story.setFlag("solia_found_keystone", true);
            }
            case 2 -> {
                Printer.slowPrint("Aldran's notes are on a lectern at the Conduit's base. Meticulous.");
                Printer.slowPrint("Every calculation. Every rune. And one page marked 'FAIL-SAFE'.");
                Printer.slowPrint("A purification sequence — it could invert the Conduit's purpose.");
                Printer.slowPrint("Heal instead of burn. If the operator has enough faith.");
                Printer.slowPrint("You linger on that last clause.");
                story.setFlag("solia_found_failsafe_notes", true);
            }
            case 3 -> {
                Printer.slowPrint("You lay your hand on the outer casing.");
                Printer.slowPrint("It hits you like cold water and grief and fire all at once.");
                Printer.slowPrint("You feel the Greying energy coiled inside. You feel the sacred geometry.");
                Printer.slowPrint("And underneath it — like a coal not yet gone out — you feel");
                Printer.slowPrint("something that might still be holy.");
                Printer.slowPrint("It could be redirected. It wants to be, almost.");
                Printer.slowPrint("You step back, breathing hard. But you know what to do.");
                story.setFlag("solia_felt_conduit", true);
                solia.loseFaith(1);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 6: Aldran's Confrontation ─────────────────────────────────────
    private void sceneAldranConfrontation() {
        Printer.printDivider();
        Printer.slowPrint("\"I knew you'd come here.\"");
        Printer.pause(500);
        Printer.slowPrint("Aldran stands at the far end of the Vault.");
        Printer.slowPrint("In white and gold, as always. The same kind eyes. The same gentle voice.");
        Printer.slowPrint("You spent twenty years believing in this man.");
        Printer.pause(400);
        Printer.slowPrint("\"You were my best student, Solia,\" he says, walking toward you unhurried.");
        Printer.slowPrint("\"You always saw what others couldn't. You still do.\"");
        Printer.slowPrint("He gestures at the Conduit. \"So you understand what this is for.\"");
        Printer.pause(300);
        System.out.println();
        System.out.println("  How do you respond?");
        System.out.println("  1. \"I understand what it does. That's not the same thing.\"");
        System.out.println("  2. \"You're going to kill hundreds of thousands of people.\"");
        System.out.println("  3. Say nothing. Let him explain himself.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("He pauses. Something in that lands differently than argument.");
                Printer.slowPrint("\"The Greying cannot be healed,\" he says. \"Only ended.\"");
                Printer.slowPrint("\"I've tried every other way. This is what remains.\"");
            }
            case 2 -> {
                Printer.slowPrint("\"I'm going to end the Greying,\" he says, as if that's the whole answer.");
                Printer.slowPrint("\"The people who survive will be clean. They can rebuild.\"");
                Printer.slowPrint("\"That's not salvation,\" you say. \"That's arithmetic.\"");
                Printer.slowPrint("His eyes cloud. \"It's mercy.\"");
            }
            case 3 -> {
                Printer.slowPrint("He explains for several minutes. Methodical. Certain.");
                Printer.slowPrint("The numbers. The projections. The years of failure before this.");
                Printer.slowPrint("He sounds like a man who has made peace with an unbearable decision.");
                Printer.slowPrint("That's the most frightening version of him.");
            }
        }

        Printer.pause(400);
        Printer.slowPrint("\"Join me, Solia,\" he says finally.");
        Printer.slowPrint("\"The activation requires someone of genuine faith. I believe that's still you.\"");
        Printer.slowPrint("\"Help me do this. Then help me build what comes after.\"");
        Printer.pause(400);
        solia.loseFaith(1);

        System.out.println();
        System.out.println("  This is the moment. What do you say?");
        System.out.println("  1. Refuse clearly. \"I will not.\"");
        System.out.println("  2. Stall. \"Give me time to think.\" Buy time for another approach.");
        System.out.println("  3. Ask him one question: \"What if you're wrong?\"");

        int response = InputHandler.getInt(1, 3);
        switch (response) {
            case 1 -> {
                Printer.slowPrint("\"I will not.\"");
                Printer.slowPrint("The simplicity of it silences him for a moment.");
                Printer.slowPrint("Then he signals the guards. \"Then you leave me no choice.\"");
                Printer.slowPrint("\"You're expelled from the Sacred Flame. Formally. Permanently.\"");
                Printer.slowPrint("\"And you'll be held here until the activation is complete.\"");
                story.setFlag("solia_refused_aldran_clearly", true);
            }
            case 2 -> {
                Printer.slowPrint("His eyes narrow. He's not fooled — but he wants to believe.");
                Printer.slowPrint("\"One night,\" he says. \"Meditate. You'll see I'm right.\"");
                Printer.slowPrint("You're placed in the old novice quarters. Comfortable. Locked.");
                Printer.slowPrint("You spend the night planning. Not praying.");
                story.setFlag("solia_stalled_aldran", true);
            }
            case 3 -> {
                Printer.slowPrint("The question hangs in the air.");
                Printer.slowPrint("Aldran opens his mouth. Closes it.");
                Printer.slowPrint("\"I'm not wrong,\" he says. But something in his voice is different.");
                Printer.slowPrint("\"I can't be wrong. Too much depends on it.\"");
                Printer.slowPrint("That's not the same thing. And he knows it.");
                story.setFlag("solia_planted_doubt_in_aldran", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 7: Act End ─────────────────────────────────────────────────────
    private void sceneActEnd() {
        Printer.printDivider();

        if (story.getFlag("solia_stalled_aldran")) {
            Printer.slowPrint("The novice quarters window opens onto the garden.");
            Printer.slowPrint("A familiar garden entrance. Still unlocked.");
            Printer.slowPrint("Of course it is.");
        } else if (story.getFlag("solia_refused_aldran_clearly")) {
            Printer.slowPrint("They lock you in. They don't search you carefully.");
            Printer.slowPrint("Old priests never really learned to think like guards.");
            Printer.slowPrint("You're out through the servant's passage before the hour is done.");
        } else {
            Printer.slowPrint("Aldran watches you leave, still uncertain, still hoping you'll return to him.");
            Printer.slowPrint("That uncertainty might be the most useful thing you have.");
        }

        Printer.pause(400);
        Printer.slowPrint("You stand outside the chapter house in the night air.");
        Printer.slowPrint("The Conduit activates in three days.");
        Printer.slowPrint("You know what it does. You know where its keystone is.");
        Printer.slowPrint("You might even know how to redirect it, if your faith holds.");
        Printer.pause(500);
        Printer.slowPrint("That's the question, isn't it.");
        Printer.slowPrint("Not whether you have the knowledge.");
        Printer.slowPrint("Whether you have the faith.");
        Printer.pause(400);
        Printer.slowPrint("You're not sure you do. But you're going back in regardless.");
        Printer.slowPrint("That's always been what faith actually looks like.");

        Printer.printDivider();
        Printer.printBox("ACT II COMPLETE — THE BROKEN ALTAR");
        Printer.printBox("Solia Ren prepares to face her mentor one final time.");
        if (story.getFlag("solia_has_allies"))
            Printer.printBox("★ Aldric and Vael are with you. You're not alone.");
        if (story.getFlag("solia_found_failsafe_notes"))
            Printer.printBox("★ You found the fail-safe sequence. Redirection may be possible.");
        if (story.getFlag("solia_planted_doubt_in_aldran"))
            Printer.printBox("★ You put doubt in Aldran's mind. That crack may matter.");
        if (story.getFlag("solia_found_keystone"))
            Printer.printBox("★ You know where the keystone is. You can end this quickly if needed.");
        Printer.printDivider();
        InputHandler.waitForEnter();
        story.advanceAct();
    }

    private void handleGameOver() {
        Printer.slowPrint("The altar breaks Solia Ren. The Conduit counts down in silence.");
        System.exit(0);
    }
}
