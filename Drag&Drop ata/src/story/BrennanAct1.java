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
        sceneAftermath();
        sceneBanditCamp();
        sceneWantedPoster();
        sceneFortGreyveil();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT I — THE WANDERER  |  Brennan Ashvane");
        Printer.slowPrint("Kingdom of Thaalisia, ten years after the Greying. The war with Vrakkas grinds on.");
        Printer.slowPrint("You've been walking for six days. No destination. That's the point.");
        Printer.slowPrint("The mud of the eastern roads clings to your boots, heavy as the memories.");
        Printer.pause(400);
        Printer.slowPrint("The Iron Vow's deserter bounty sits around your neck like a stone.");
        Printer.slowPrint("A fall you took for an order Lord Marshal Veyran never admitted giving.");
        Printer.slowPrint("But bounty hunters still need to know your face first.");
        Printer.slowPrint("You keep your hood up. You keep your sword hidden. You keep moving.");
        Printer.pause(500);
        Printer.slowPrint("The village of Millhaven appears through the treeline just as the rain starts.");
        Printer.slowPrint("Small. Quiet. The kind of place that should be safe from the war.");
        Printer.slowPrint("Then you hear the screaming. And you see the smoke.");
        InputHandler.waitForEnter();
    }

    private void sceneVillage() {
        Printer.printDivider();
        Printer.slowPrint("You break into a run. The rain hisses against the burning thatch of the outer cottages.");
        Printer.slowPrint("Three men in dark robes are dragging villagers into the muddy square.");
        Printer.slowPrint("Ashen Hand cultists. You recognize the symbol — a grey hand on black cloth.");
        Printer.slowPrint("They're demanding the village's stored grain. \"A tithe to the Greying!\" one shouts.");
        Printer.pause(300);
        Printer.slowPrint("An old farmer spits at their feet. \"We have nothing left to give!\"");
        Printer.slowPrint("One of the cultists raises a jagged blade, his eyes completely black with corruption.");
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Step in immediately — draw your sword and charge.");
        System.out.println("  2. [Stealth] Circle around behind the burning cottages to flank them.");
        System.out.println("  3. Call out from the treeline to distract them, drawing them away from the villagers.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("Your sword is out before the thought finishes forming.");
                Printer.slowPrint("You clear the distance in seconds, bringing your blade down hard.");
                story.setFlag("brennan_acts_on_instinct", true);
            }
            case 2 -> {
                if (util.Dice.performSkillCheck("Stealth", 12)) {
                    Printer.slowPrint("Old Iron Vow habits die hard. You sweep wide, using the smoke as cover.");
                    Printer.slowPrint("You emerge behind the leader, your sword at his back.");
                    story.setFlag("brennan_uses_tactics", true);
                } else {
                    Printer.slowPrint("You try to flank them, but a piece of burning wood cracks loudly under your boot.");
                    Printer.slowPrint("The cultists spin around and spot you immediately.");
                }
            }
            case 3 -> {
                Printer.slowPrint("\"HEY!\" Your voice carries over the crackling fire.");
                Printer.slowPrint("The cultists spin toward you. The old man uses the distraction to shove the nearest one into the mud.");
                Printer.slowPrint("You draw your blade as they charge you.");
            }
        }

        Printer.slowPrint("\nCOMBAT: Ashen Hand Cultists attack!");
        InputHandler.waitForEnter();

        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        brennan.restoreMana(10);
        Printer.slowPrint("One falls, but another immediately takes his place, swinging wildly.");
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        
        Printer.slowPrint("The last cultist drops his blade, staring at you in terror. He turns and runs into the forest.");
        Printer.slowPrint("You let him go. The village is safe, for now.");
        InputHandler.waitForEnter();
    }

    private void sceneAftermath() {
        Printer.printDivider();
        Printer.slowPrint("The villagers gather in the square. They offer you what little coin they have.");
        Printer.slowPrint("A loaf of bread. A warm blanket. You refuse all of it.");
        System.out.println();
        System.out.println("  The village elder steps forward. \"Will you stay? To protect us?\"");
        System.out.println("  1. \"I'll stay the night.\" (Rest and recover HP/Mana)");
        System.out.println("  2. \"I'm just passing through.\" (Search the dead cultists first)");
        System.out.println("  3. \"Who were they? Where did they come from?\"");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You accept a spot by the fire. You dream of a burning village from years ago.");
                Printer.slowPrint("A medal in your hand. The taste of ash.");
                brennan.heal(40);
                brennan.restoreMana(20);
            }
            case 2 -> {
                Printer.slowPrint("You kneel by the bodies. You find a crude map shoved into a boot.");
                Printer.slowPrint("It marks 'Fort Greyveil'. Two days north. A ruined outpost.");
                story.setFlag("brennan_found_map", true);
            }
            case 3 -> {
                Printer.slowPrint("\"They call themselves the Ashen Hand,\" the elder whispers, shivering.");
                Printer.slowPrint("\"They camp at Greyveil. No one who goes there returns.\"");
                story.setFlag("brennan_knows_about_greyveil", true);
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneBanditCamp() {
        Printer.printDivider();
        Printer.slowPrint("Before you leave, you inspect the cultists' belongings more closely.");
        Printer.slowPrint("Tucked inside a leather pouch is a heavy iron key, and a sealed letter.");
        Printer.slowPrint("The wax seal is broken, but the imprint remains: A crescent moon over a sword.");
        Printer.slowPrint("The seal of the Iron Vow.");
        Printer.pause(400);
        Printer.slowPrint("You unfold the parchment. The handwriting is sharp, unmistakable.");
        Printer.slowPrint("\"SUPPLIES DELIVERED AS ARRANGED. KEEP THE EASTERN ROAD CLEAR. — V\"");
        Printer.pause(400);
        Printer.slowPrint("Veyran.");
        Printer.slowPrint("The Lord Marshal of the Iron Vow is funding the Ashen Hand.");
        Printer.slowPrint("The very cult he publicly swore to eradicate.");
        story.setFlag("brennan_has_veyran_letter", true);
        InputHandler.waitForEnter();
    }

    private void sceneWantedPoster() {
        Printer.printDivider();
        Printer.slowPrint("You travel north for two days. The road is muddy and miserable.");
        Printer.slowPrint("You stop at a roadside tavern called The Broken Anvil to buy rations.");
        Printer.slowPrint("Nailed to the door is a fresh parchment.");
        Printer.pause(300);
        Printer.slowPrint("WANTED — BRENNAN ASHVANE. DESERTER. TRAITOR. REWARD: 500 GOLD.");
        Printer.slowPrint("The sketch is remarkably accurate.");
        
        System.out.println("  1. Pull your hood lower and walk away quietly.");
        System.out.println("  2. Tear the poster down and crush it in your fist.");
        System.out.println("  3. Talk to a merchant nearby to gauge public opinion.");
        int choice = InputHandler.getInt(1, 3);
        
        if (choice == 3) {
            Printer.slowPrint("You ask the merchant what this 'Ashvane' did.");
            Printer.slowPrint("\"They say he slaughtered an entire village on the border,\" the merchant spits.");
            Printer.slowPrint("\"But others say... the Vow did something to deserve losing their best knight.\"");
            story.setFlag("brennan_people_suspect_vow", true);
        } else if (choice == 2) {
            Printer.slowPrint("You rip it down. A passing guard narrows his eyes at you, but says nothing.");
        } else {
            Printer.slowPrint("You keep your head down. Survival first. Vengeance later.");
        }
        InputHandler.waitForEnter();
    }

    private void sceneFortGreyveil() {
        Printer.printDivider();
        Printer.slowPrint("Fort Greyveil looms ahead, rising from the fog like a decayed tooth.");
        Printer.slowPrint("Two Ashen Brutes guard the ruined iron gates. They are hulking monstrosities of flesh and grey magic.");
        InputHandler.waitForEnter();
        
        if (!CombatSystem.startCombat(brennan, Enemy.ashenBrute())) { handleGameOver(); return; }
        brennan.restoreMana(15);
        brennan.heal(20);
        
        Printer.slowPrint("You step over their massive corpses and enter the courtyard.");
        Printer.slowPrint("Inside the fort: Dozens of Iron Vow supply crates. Weapons. Rations.");
        Printer.slowPrint("And Greying-corrupted relics, piled high like treasure.");
        Printer.pause(400);
        Printer.slowPrint("An Ashen Hand lieutenant steps out of the keep, clapping slowly.");
        Printer.slowPrint("\"The rogue knight. Veyran said you might come sniffing around.\"");
        
        if (!CombatSystem.startCombat(brennan, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        
        Printer.slowPrint("The lieutenant falls to his knees, bleeding heavily.");
        System.out.println("  1. Leave him alive as a witness.");
        System.out.println("  2. Execute him. He chose his side.");
        if (InputHandler.getInt(1, 2) == 1) {
            Printer.slowPrint("\"You'll testify before the Crown,\" you tell him, tying his hands.");
            story.setFlag("brennan_kept_witness", true);
        } else {
            Printer.slowPrint("You swing your blade. The courtyard falls completely silent.");
        }
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You look at the crates. The proof is undeniable.");
        Printer.slowPrint("Veyran didn't just frame you. He's playing both sides of a war that's tearing the kingdom apart.");
        Printer.slowPrint("You start walking south. Toward Valdenmere.");
        Printer.slowPrint("Toward the Iron Vow Summit.");
        Printer.pause(500);
        Printer.slowPrint("Running ends now.");
        
        Printer.printDivider();
        Printer.printBox("ACT I COMPLETE — THE WANDERER");
        if (story.getFlag("brennan_kept_witness")) {
            Printer.printBox("★ You kept the cultist alive. A risky move, but a necessary witness.");
        }
        if (story.getFlag("brennan_people_suspect_vow")) {
            Printer.printBox("★ The public is beginning to doubt the Iron Vow. The seeds of truth are planted.");
        }
        Printer.printDivider();
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The road ends here for Brennan Ashvane. The truth dies with you.");
        System.exit(0);
    }
}
