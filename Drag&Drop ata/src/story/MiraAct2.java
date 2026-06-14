package story;

import characters.Mira;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class MiraAct2 {

    private Mira mira;
    private StoryManager story;

    public MiraAct2(Mira mira, StoryManager story) {
        this.mira = mira;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneArchive();
        sceneVault();
        sceneTruth();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT II — THE COST OF AIR  |  Mira Cael");
        Printer.slowPrint("Davan pays you for the crystal. It's enough. Exactly enough.");
        Printer.slowPrint("That's what bothers you. The fee for Lena's treatment deposit matches the payout to the copper.");
        Printer.pause(400);
        Printer.slowPrint("Davan, your fence, sends you on a follow-up job immediately.");
        Printer.slowPrint("He calls it a \"routine retrieval\" from the Crown Archive.");
        Printer.slowPrint("But the pay is too high. The timing is wrong.");
        Printer.slowPrint("Someone designed this sequence of jobs specifically around what you would do for your sister.");
        InputHandler.waitForEnter();
    }

    private void sceneArchive() {
        Printer.printDivider();
        Printer.slowPrint("You scale the sheer stone wall of the Crown Archive well past midnight.");
        Printer.slowPrint("You slip through a third-story window into the grand, dusty stacks of forbidden history.");
        Printer.slowPrint("The target file: Greying medical assessments from the lower city wards.");
        Printer.pause(400);
        Printer.slowPrint("The silence of the massive library is broken by a scraping sound.");
        Printer.slowPrint("A heavy boot on stone. You aren't alone.");
        Printer.slowPrint("A cultist of the Ashen Hand steps out from behind a bookshelf, a jagged blade drawn.");
        Printer.slowPrint("What are they doing in the Crown Archive?");
        
        if (!CombatSystem.startCombat(mira, Enemy.ashenHandBandit())) { handleGameOver(); return; }
        mira.heal(25);
        
        Printer.slowPrint("You search the cultist's body. He carries a key ring that belongs to the Archive Master.");
        Printer.slowPrint("The Crown is letting the cultists in. They are working together.");
        InputHandler.waitForEnter();
    }
    
    private void sceneVault() {
        Printer.printDivider();
        Printer.slowPrint("You reach the restricted sub-basement. The vault door is massive steel, engraved with wards.");
        Printer.slowPrint("The lock is incredibly complex, a puzzle of interlocking gears and magical triggers.");
        
        System.out.println("  How do you crack the vault?");
        System.out.println("  1. Use your specialized acid to melt the internal tumblers.");
        System.out.println("  2. Pick it manually. It's risky, but leaves no trace.");
        System.out.println("  3. Try the keys you found on the dead cultist.");
        
        int choice = InputHandler.getInt(1, 3);
        if (choice == 1) {
            Printer.slowPrint("The acid hisses, eating through the metal. The door swings open with a groan.");
            story.setFlag("mira_melted_vault", true);
        } else if (choice == 2) {
            Printer.slowPrint("It takes ten agonizing minutes, but you hear the satisfying 'click'. You are a master.");
            story.setFlag("mira_picked_vault", true);
        } else {
            Printer.slowPrint("The key fits perfectly. They really did give the cultists full access.");
        }
        
        Printer.pause(400);
        Printer.slowPrint("Inside the vault, you find stacks of ledgers bound in black leather.");
        Printer.slowPrint("You open one. It's a logistical nightmare of pure evil.");
        Printer.slowPrint("They prove, unequivocally, that Crown officials knew the lower-city poison was a deliberate cover-up.");
        Printer.slowPrint("They intentionally vented the magical exhaust from their 'Conduit' engine directly into the slums.");
        Printer.slowPrint("It was cheaper than building proper containment. Lena's illness... it was a line item on a budget.");
        story.setFlag("mira_has_archive_proof", true);
        InputHandler.waitForEnter();
    }

    private void sceneTruth() {
        Printer.printDivider();
        Printer.slowPrint("You meet Davan in a filthy alleyway. Rain pours down, washing the filth into the drains.");
        Printer.slowPrint("\"Do you have the ledgers?\" he asks, pulling his collar up, looking around nervously.");
        Printer.slowPrint("You stare at him. You see him clearly for the first time. The expensive boots. The full purse.");
        Printer.pause(400);
        Printer.slowPrint("\"They needed the map moved. And they needed the ledgers destroyed,\" he admits, seeing the murder in your eyes.");
        Printer.slowPrint("\"You were useful, Mira. You were motivated. I'm sorry.\"");
        
        System.out.println("  1. Demand he help get Lena out immediately, or you kill him here.");
        System.out.println("  2. Threaten to expose his part in the conspiracy to the gangs.");
        System.out.println("  3. Walk away. He's a pawn. You need to kill the king.");
        
        int c = InputHandler.getInt(1, 3);
        if (c == 1) {
            Printer.slowPrint("\"Alright! Alright!\" he stammers, throwing a set of travel papers at your feet.");
            Printer.slowPrint("\"There's a ship leaving for the Summer Isles tomorrow. Take it and never come back.\"");
            story.setFlag("mira_extorted_davan", true);
        } else if (c == 2) {
            Printer.slowPrint("\"You'll burn us both!\" he hisses. But he hands over a heavy pouch of gold and safe passage documents.");
            story.setFlag("mira_blackmailed_davan", true);
        } else {
            Printer.slowPrint("You turn your back on him without a word. He's nothing. He doesn't matter.");
            story.setFlag("mira_cut_ties_davan", true);
        }
        
        Printer.pause(400);
        Printer.slowPrint("Either way, you know where this ends.");
        Printer.slowPrint("The eastern road. The scar in the world. It is the only place where the cure, the Conduit, and the truth intersect.");
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printDivider();
        Printer.printBox("ACT II COMPLETE — THE COST OF AIR");
        if (story.getFlag("mira_has_archive_proof")) {
            Printer.printBox("★ You hold the ledgers. You have the power to bring down the Crown.");
        }
        Printer.printDivider();
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The archives become your tomb. Lena will wait forever.");
        System.exit(0);
    }
}
