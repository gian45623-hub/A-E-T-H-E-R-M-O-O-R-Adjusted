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
        sceneCourtyard();
        sceneFinalChoice();
        sceneBossFight();
        sceneEnding();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — REDEMPTION OR REVENGE  |  Brennan Ashvane");
        if (story.getFlag("brennan_was_arrested")) {
            Printer.slowPrint("The holding cells of the Iron Hall. The air is freezing, smelling of rust and damp earth.");
            Printer.slowPrint("Your execution is scheduled for dawn. The bells will toll in three hours.");
            Printer.slowPrint("But young knights still believe in something. And some are about to prove it.");
        } else {
            Printer.slowPrint("You patch your wounds in a filthy alleyway. Rain washes the blood into the gutters.");
            Printer.slowPrint("Veyran is moving rapidly to secure his power, purging anyone who questions him.");
            Printer.slowPrint("You have one night to stop him before his martial law becomes absolute.");
            Printer.slowPrint("You stare up at the looming towers of the Iron Hall. It's time to finish this.");
        }
        InputHandler.waitForEnter();
    }

    private void sceneBreakout() {
        Printer.printDivider();
        if (story.getFlag("brennan_was_arrested")) {
            Printer.slowPrint("The young knight, Ser Aldis, unlocks your cell with trembling hands.");
            Printer.slowPrint("\"I was at Millfield,\" he says, handing you your sword. \"I know what Veyran ordered us to do.\"");
            Printer.slowPrint("\"There are a dozen of us willing to fight. But we need a leader.\"");
            
            System.out.println("  1. [Leadership] Trust them fully and lead the mutiny.");
            System.out.println("  2. Use them as a distraction to reach Veyran quietly.");
            System.out.println("  3. Tell them to stand down. You won't risk their lives for your vengeance.");
            
            int c = InputHandler.getInt(1, 3);
            if (c == 1) {
                if (util.Dice.performSkillCheck("Leadership", 15)) {
                    Printer.slowPrint("\"Then draw steel,\" you tell him. \"We march on the Sanctum.\"");
                    Printer.slowPrint("Your words inspire them to fight like lions. The mutiny is an overwhelming success.");
                    story.setFlag("brennan_led_mutiny", true);
                } else {
                    Printer.slowPrint("\"We fight,\" you tell him. But your voice falters.");
                    Printer.slowPrint("They follow you, but they are terrified. Many will die.");
                    story.setFlag("brennan_led_mutiny", true);
                }
            } else if (c == 2) {
                Printer.slowPrint("\"Hit the armory. Make noise. I'll handle Veyran,\" you instruct.");
            } else {
                Printer.slowPrint("\"Stay here. Live to rebuild the Vow tomorrow,\" you tell them, walking out alone.");
            }
            Printer.pause(400);
            Printer.slowPrint("Alarms sound as you enter the lower courtyard. They know you're out.");
        } else {
            Printer.slowPrint("You scale the treacherous outer walls of the Iron Hall. Rain slicks the ancient stone.");
            Printer.slowPrint("You slip over the battlements and drop into the courtyard, right into a patrol of loyalist guards.");
        }
        
        Printer.slowPrint("A corrupted swordsman charges you, his armor fused to his skin by Greying magic.");
        if (!CombatSystem.startCombat(brennan, Enemy.ashenSoldier())) { handleGameOver(); return; }
        brennan.heal(30);
        InputHandler.waitForEnter();
    }

    private void sceneCourtyard() {
        Printer.printDivider();
        Printer.slowPrint("You push through the courtyard, cutting down anyone in Veyran's colors.");
        Printer.slowPrint("Lightning flashes, illuminating a massive figure blocking the stairs to the inner keep.");
        Printer.slowPrint("An Ashen Brute. Veyran has let the monsters directly into the castle.");
        Printer.slowPrint("The Brute swings a warhammer the size of an anvil.");
        
        if (!CombatSystem.startCombat(brennan, Enemy.ashenBrute())) { handleGameOver(); return; }
        brennan.heal(40);
        brennan.restoreMana(20);
        
        Printer.slowPrint("The brute collapses, crushing the stone steps beneath its weight.");
        Printer.slowPrint("You step over it. The doors to the Marshal's Sanctum are just ahead.");
        InputHandler.waitForEnter();
    }

    private void sceneFinalChoice() {
        Printer.printDivider();
        Printer.slowPrint("You kick open the heavy iron doors to the Marshal's Sanctum.");
        Printer.slowPrint("Veyran stands alone by the massive stained-glass window. Thunder shakes the glass.");
        Printer.slowPrint("His sword is already drawn. He looks completely serene. Certain.");
        Printer.pause(400);
        Printer.slowPrint("\"You always were stubborn, Brennan,\" he says softly. \"Too stubborn to see the big picture.\"");
        Printer.slowPrint("\"I am building a Thaalisia that can never be broken. But to forge iron, you need fire.\"");
        
        System.out.println("  How do you end this?");
        System.out.println("  1. Kill him yourself. No mercy. He deserves to die for Millhaven.");
        System.out.println("  2. Defeat him and bring him to public trial. Let the law judge him.");
        System.out.println("  3. Offer him one final chance to yield and confess his crimes.");
        
        int choice = InputHandler.getInt(1, 3);
        if (choice == 1) {
            story.setFlag("brennan_killed_veyran_personally", true);
            Printer.slowPrint("\"Then let it end in blood!\" Veyran roars, charging.");
        } else if (choice == 3) {
            Printer.slowPrint("\"I yield to no one! I AM THE LAW!\" Veyran lunges at you.");
        } else {
            Printer.slowPrint("\"I will never stand trial in a court of sheep!\" He brings his sword down in a vicious arc.");
        }
        InputHandler.waitForEnter();
    }

    private void sceneBossFight() {
        Printer.printDivider();
        Printer.slowPrint("⚔️   BRENNAN ASHVANE  vs  LORD MARSHAL VEYRAN");
        InputHandler.waitForEnter();
        if (!CombatSystem.startCombat(brennan, Enemy.bossVeyran())) { handleGameOver(); }
    }
    
    private void sceneEnding() {
        Printer.printDivider();
        Printer.slowPrint("Veyran falls to his knees, his sword clattering across the polished marble floor.");
        Printer.slowPrint("He coughs blood, staring up at you with defiance fading into shock.");
        Printer.slowPrint("\"You... haven't stopped it...\" he gasps. \"Stage Two... Valdros... is already...\"");
        Printer.pause(400);
        
        if (story.getFlag("brennan_killed_veyran_personally")) {
            Printer.slowPrint("You drive your blade straight through his heart. \"Burn in hell, Veyran.\"");
            Printer.slowPrint("You pull your sword free. It is done.");
        } else {
            Printer.slowPrint("You kick his sword away and press your boot to his chest.");
            Printer.slowPrint("\"Save your breath for the judge,\" you tell him coldly.");
        }
        
        Printer.pause(500);
        Printer.slowPrint("The sanctum doors burst open. Ser Aldis and dozens of loyalist guards pour in.");
        Printer.slowPrint("They see Veyran defeated. Slowly, one by one, they lower their weapons.");
        Printer.slowPrint("The truth will come out tomorrow. The Iron Vow is broken, shattered by its own leader.");
        Printer.slowPrint("But looking at the young knights surrounding you, you realize...");
        Printer.slowPrint("Perhaps it can be forged anew. Uncorrupted.");
        
        Printer.printDivider();
        Printer.printBox("STORY COMPLETE — BRENNAN ASHVANE");
        Printer.printDivider();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("Veyran's blade strikes true. You fall to the cold stone.");
        Printer.slowPrint("The corruption spreads, and Thaalisia falls into darkness.");
        System.exit(0);
    }
}
