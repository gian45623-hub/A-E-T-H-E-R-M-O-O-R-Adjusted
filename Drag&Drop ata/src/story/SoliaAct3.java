package story;

import characters.Priest;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SoliaAct3 {

    private Priest solia;
    private StoryManager story;

    public SoliaAct3(Priest solia, StoryManager story) {
        this.solia = solia;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneReturningToTheVault();
        sceneAlliesReady();
        sceneAldranWaiting();
        sceneFinalChoice();
        sceneBossFight();
        sceneEnding();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — THE LAST PRAYER  |  Solia Ren");
        Printer.slowPrint("The Pyre Conduit activates at midnight.");
        Printer.slowPrint("That gives you six hours.");
        Printer.slowPrint("Six hours to stop the person you loved most from committing");
        Printer.slowPrint("the largest act of faith-based genocide in history.");
        Printer.pause(500);
        Printer.slowPrint("You've spent your life believing faith should be gentle.");
        Printer.slowPrint("Tonight, you're going to need it to be fierce.");
        InputHandler.waitForEnter();
    }

    private void sceneReturningToTheVault() {
        Printer.printDivider();
        Printer.slowPrint("The Sacred Flame chapter house is in lockdown.");
        Printer.slowPrint("Aldran's loyalists guard every door.");
        Printer.slowPrint("But Aldric and Vael have prepared something.");
        System.out.println();
        System.out.println("  How do you get in?");
        System.out.println("  1. The front entrance — demand entry as an expelled priestess.");
        System.out.println("  2. The servant's passage Aldric kept unlocked for you.");
        System.out.println("  3. Create a distraction while Aldric causes chaos above.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("\"I am Sister Ren of the Third Circle. I demand audience with the High Priest.\"");
                Printer.slowPrint("Your voice is steady. The guards hesitate — old training runs deep.");
                Printer.slowPrint("They let you pass. Some part of you still belongs here.");
            }
            case 2 -> {
                Printer.slowPrint("The reliquary tunnel. Dark. Familiar. Unchanged.");
                Printer.slowPrint("You slip through like a ghost. Below the guard line in seconds.");
            }
            case 3 -> {
                if (story.getFlag("solia_has_allies")) {
                    Printer.slowPrint("Above, Aldric starts a fire in the ritual chamber — controlled. Deliberate.");
                    Printer.slowPrint("Every guard rushes upstairs. The path to the Vault is clear.");
                } else {
                    Printer.slowPrint("You're alone. You take the servant's passage instead.");
                }
            }
        }

        InputHandler.waitForEnter();

        // Combat in the Vault entrance
        Printer.slowPrint("Aldran's loyalist priests block your path to the Conduit!");
        boolean won = CombatSystem.startCombat(solia, Enemy.corruptedPriest());
        if (!won) {
            handleGameOver();
            return;
        }
        solia.restoreMana(20);
        InputHandler.waitForEnter();
    }

    private void sceneAlliesReady() {
        Printer.printDivider();
        Printer.slowPrint("Aldric meets you at the Vault entrance, breathless.");
        Printer.slowPrint("\"It's happening,\" he says. \"The activation sequence is running.\"");
        Printer.slowPrint("\"Thirty minutes until ignition.\"");
        Printer.pause(400);

        if (story.getFlag("solia_found_failsafe_notes")) {
            Printer.slowPrint("You still have Aldran's notes. The fail-safe sequence is here.");
            Printer.slowPrint("If you can get to the keystone and redirect the flow,");
            Printer.slowPrint("the Conduit becomes a healer instead of a weapon.");
            Printer.slowPrint("It's complex. It's dangerous. But it's possible.");
        }

        if (story.getFlag("solia_found_keystone")) {
            Printer.slowPrint("You know where the keystone is. You could destroy it entirely.");
            Printer.slowPrint("The Vault will collapse. Everything will be lost.");
            Printer.slowPrint("But the Conduit will never activate again.");
        }

        System.out.println();
        System.out.println("  What's your plan?");
        System.out.println("  1. Destroy the Conduit — the Vault with it. Final and permanent.");
        System.out.println("  2. Redirect it using the fail-safe. Save the Vault. Save the symbol.");
        System.out.println("  3. Confront Aldran first. Maybe he'll stop this himself.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> story.setFlag("solia_plans_to_destroy_conduit", true);
            case 2 -> story.setFlag("solia_plans_to_purify_conduit", true);
            case 3 -> story.setFlag("solia_will_appeal_to_aldran", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneAldranWaiting() {
        Printer.printDivider();
        Printer.slowPrint("You descend into the Vault proper.");
        Printer.slowPrint("The Pyre Conduit hums — louder than before. Almost awake.");
        Printer.pause(400);
        Printer.slowPrint("Aldran stands before it in full ceremonial robes.");
        Printer.slowPrint("His hands move through the activation sequence from memory.");
        Printer.slowPrint("He looks at you. No surprise. Like he's been expecting you all along.");
        Printer.pause(400);
        System.out.println();
        System.out.println("  What do you say?");
        System.out.println("  1. \"Stop. Please. I'm begging you.\"");
        System.out.println("  2. \"I won't let you do this.\" Flat. Final.");
        System.out.println("  3. \"Tell me why. Give me the real reason.\"");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("\"Solia,\" he says gently. \"You of all people understand.");
                Printer.slowPrint("The Greying cannot be fixed. It can only end.\"");
                Printer.slowPrint("\"Not like this,\" you whisper.");
            }
            case 2 -> {
                Printer.slowPrint("\"You've tried, Solia. I appreciate that. But you're too late.\"");
                Printer.slowPrint("He returns to the sequence. Fifteen minutes remain.");
            }
            case 3 -> {
                Printer.slowPrint("\"Because I'm afraid,\" he says simply. Honest. Broken.");
                Printer.slowPrint("\"I'm afraid of a world I can't heal. A wound that won't close.\"");
                Printer.slowPrint("\"So I'm choosing to heal it the only way I can.\"");
                story.setFlag("solia_heard_aldran_truth", true);
            }
        }

        Printer.pause(400);
        Printer.slowPrint("\"I love you,\" Aldran says. Not as explanation. As goodbye.");
        Printer.slowPrint("\"I love you too,\" you answer. Both things are true: love and opposition.");
        Printer.slowPrint("\"That's why I have to stop you.\"");
        InputHandler.waitForEnter();
    }

    private void sceneFinalChoice() {
        Printer.printDivider();
        Printer.slowPrint("Ten minutes until activation.");
        Printer.slowPrint("You can feel the Greying energy building inside the Conduit.");
        Printer.slowPrint("It wants to be released. It's been waiting years for this.");
        System.out.println();
        System.out.println("  How do you stop Aldran?");
        System.out.println("  1. Fight him. Disable him. Complete your plan.");
        System.out.println("  2. Appeal to him one final time during the fight.");
        System.out.println("  3. Take control of the Conduit yourself.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You prepare for combat. Aldran draws his staff — the symbol of his office.");
                Printer.slowPrint("This is where love and duty diverge completely.");
                story.setFlag("solia_fought_aldran_directly", true);
            }
            case 2 -> {
                Printer.slowPrint("You'll fight him, but you'll try to reach him.");
                Printer.slowPrint("Maybe in the moment of combat, something will break through.");
                story.setFlag("solia_fought_with_appeals", true);
            }
            case 3 -> {
                Printer.slowPrint("You move for the Conduit's controls. Aldran intercepts you.");
                Printer.slowPrint("\"I won't let you save a world that doesn't deserve saving,\" he says.");
                Printer.slowPrint("The fight begins over the controls themselves.");
                story.setFlag("solia_fought_for_conduit_control", true);
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneBossFight() {
        Printer.printDivider();
        Printer.slowPrint("⚔️   SOLIA REN  vs  HIGH PRIEST ALDRAN");
        Printer.slowPrint("    The man who taught you faith. The man who became a monster.");
        Printer.printDivider();
        InputHandler.waitForEnter();

        boolean won = CombatSystem.startCombat(solia, Enemy.bossAldran());
        if (!won) {
            handleGameOver();
            return;
        }
    }

    private void sceneEnding() {
        Printer.printDivider();
        Printer.slowPrint("Aldran falls to his knees. Defeated. Still breathing.");
        Printer.pause(500);

        if (story.getFlag("solia_plans_to_destroy_conduit")) {
            Printer.slowPrint("You move to the keystone. Your hands trembling.");
            Printer.slowPrint("One strike. The entire structure will collapse.");
            Printer.pause(400);
            Printer.slowPrint("\"Do it,\" Aldran says softly from the ground. \"If you must.\"");
            Printer.slowPrint("You do. The Vault screams. A thousand years of sacred history");
            Printer.slowPrint("crushed beneath falling stone.");
            Printer.pause(500);
            Printer.slowPrint("You escape with Aldran and Aldric. The chapter house seals itself behind you.");
            Printer.pause(400);
            Printer.slowPrint("Aldran is imprisoned for crimes against the realm.");
            Printer.slowPrint("But the Vault is gone. The Conduit is gone. The symbol is gone.");
            Printer.pause(500);
            Printer.slowPrint("You stand in the ashes and begin again. From nothing.");
            Printer.slowPrint("Your first convert is a child in the refugee camp.");
            Printer.slowPrint("You teach her: kindness is a form of faith.");
            Printer.slowPrint("It's harder than burning. It's worth so much more.");
            Printer.printDivider();
            Printer.printBox("FROM ASHES");
            Printer.printBox("Sometimes you have to burn down what you love to save what matters.");
        } else {
            Printer.slowPrint("You move to the Conduit's heart. Your hands on the controls.");
            Printer.slowPrint("Aldran's fail-safe sequence burns through your mind like prayer.");
            Printer.pause(400);
            Printer.slowPrint("The energy inverts. The Greying corruption stays locked inside the Conduit,");
            Printer.slowPrint("but the flow reverses. Instead of burning, it will heal.");
            Printer.pause(500);
            Printer.slowPrint("The Vault stands. The golden altar remains. The Conduit becomes");
            Printer.slowPrint("a monument to redemption instead of execution.");
            Printer.pause(400);
            Printer.slowPrint("Aldran is imprisoned. The order survives. And you are asked to lead it.");
            Printer.slowPrint("You accept. On one condition.");
            Printer.pause(300);
            Printer.slowPrint("On your first day as High Priestess, you rewrite the doctrine.");
            Printer.slowPrint("First line: \"Faith is not certainty. Faith is showing up anyway.\"");
            Printer.slowPrint("\"In the face of doubt. In the face of darkness. Even when the god");
            Printer.slowPrint("you're praying to doesn't answer. Show up anyway.\"");
            Printer.slowPrint("The younger priests listen. The older ones nod. Something heals.");
            Printer.printDivider();
            Printer.printBox("THE NEW FLAME");
            Printer.printBox("She didn't find her faith again. She built a better one.");
        }

        Printer.printDivider();
        Printer.slowPrint("═══════════════════════════════════════════════════════════════");
        Printer.slowPrint("               SOLIA REN — STORY COMPLETE");
        Printer.slowPrint("═══════════════════════════════════════════════════════════════");
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("Aldran's prayer completes. The Pyre Conduit ignites.");
        Printer.slowPrint("Solia Ren burns with her faith unfulfilled.");
        System.exit(0);
    }
}