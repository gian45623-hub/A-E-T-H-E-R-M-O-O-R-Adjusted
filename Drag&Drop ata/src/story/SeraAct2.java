package story;

import characters.Sera;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SeraAct2 {

    private Sera sera;
    private StoryManager story;

    public SeraAct2(Sera sera, StoryManager story) {
        this.sera = sera;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneCommission();
        sceneSafehouse();
        sceneEvidence();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT II — THE ROAD REMEMBERS  |  Sera Caldwell");
        Printer.slowPrint("Caldenmere's military quarter. High stone walls, polished armor, and secrets.");
        Printer.slowPrint("You were summoned here. A rare thing for an independent ranger.");
        Printer.slowPrint("They want a guide to the deep eastern site. The territory known as the 'Scar'.");
        Printer.pause(400);
        Printer.slowPrint("The pay is military. The client is the Crown itself. The timing is suspicious, given the escalating war with Vrakkas.");
        Printer.slowPrint("Everything about this smells like a trap. But it's also the only way to get answers.");
        InputHandler.waitForEnter();
    }

    private void sceneCommission() {
        Printer.printDivider();
        Printer.slowPrint("You sit in a sterile office. Captain Thessaly of the Royal Vanguard slides sealed orders across the heavy oak table.");
        Printer.slowPrint("\"Find the artifact in the deep scar,\" she says crisply. \"Secure the route for our mages. Don't ask why.\"");
        Printer.slowPrint("She won't meet your eyes. A bad sign.");
        
        System.out.println("  1. Accept immediately — you need answers about Corvin's death and the Iron Vow.");
        System.out.println("  2. Demand full disclosure before taking the coin.");
        System.out.println("  3. Refuse the job, but plan to follow their expedition anyway.");
        
        int c = InputHandler.getInt(1, 3);
        if (c == 2) {
            Printer.slowPrint("You push the coin purse back. \"I don't walk blind into the Greying. What is the artifact?\"");
            Printer.slowPrint("Thessaly hesitates, looking at the closed door. \"Peace would have made the east harder to access.\"");
            Printer.slowPrint("\"That's all I can say, Caldwell. Take the job or leave it.\"");
            story.setFlag("sera_heard_official_hint", true);
        } else if (c == 3) {
            Printer.slowPrint("\"Find someone else,\" you say, dropping the orders. \"I don't work for liars.\"");
            Printer.slowPrint("You leave the office, but you know exactly where they're heading.");
            story.setFlag("sera_went_alone", true);
        } else {
            Printer.slowPrint("You sweep the coin into your pouch. \"I'll find your path. But I work my way.\"");
        }
        InputHandler.waitForEnter();
    }

    private void sceneSafehouse() {
        Printer.printDivider();
        Printer.slowPrint("Before heading east, you visit an old safe house in the lower city, once used by Corvin's diplomatic network.");
        Printer.slowPrint("The door is ajar. The inside has been tossed. Books torn, floorboards pried up.");
        Printer.slowPrint("Whoever searched it was thorough. But they didn't know Corvin like you did.");
        Printer.pause(400);
        Printer.slowPrint("You check the false brick behind the hearth. It's untouched.");
        Printer.slowPrint("As your fingers brush the cold stone, a shadow detaches from the wall.");
        Printer.slowPrint("\"You shouldn't have come here, ranger,\" a voice whispers.");
        
        if (!CombatSystem.startCombat(sera, Enemy.rogueArcanist())) { handleGameOver(); return; }
        sera.heal(30);
        
        Printer.slowPrint("The assassin dissolves into ash as they die. The Ashen Hand.");
        Printer.slowPrint("The cultists are working in the capital. They're cleaning up loose ends.");
        InputHandler.waitForEnter();
    }

    private void sceneEvidence() {
        Printer.printDivider();
        Printer.slowPrint("You pry the brick loose and extract a wrapped bundle of documents.");
        Printer.slowPrint("Inside: a ledger and several letters linking border raids directly to Valdenmere aristocratic interests.");
        Printer.pause(400);
        Printer.slowPrint("The truth is laid bare in black ink.");
        Printer.slowPrint("It wasn't Vrakkas acting alone. High-ranking Thaalisian agents were staging attacks to prevent negotiation.");
        Printer.slowPrint("They were steering the kingdom toward total war.");
        Printer.slowPrint("Corvin found this out. That's why he was killed on the road.");
        Printer.pause(400);
        Printer.slowPrint("And the seal on the letters authorizing his assassination?");
        Printer.slowPrint("The crescent moon over a sword.");
        story.setFlag("sera_has_conspiracy_letters", true);
        
        System.out.println("  1. Read further into the ledgers.");
        System.out.println("  2. Pack them up immediately. It's not safe here.");
        
        if (InputHandler.getInt(1, 2) == 1) {
            Printer.slowPrint("You find a reference to 'Stage Two' and a location in the deepest part of the eastern scar.");
            Printer.slowPrint("They aren't just starting a war. They are building something.");
            story.setFlag("sera_knows_stage_two", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You leave the safehouse as the city watch approaches.");
        Printer.slowPrint("The truth is heavier than the gold in your pouch.");
        Printer.slowPrint("The Crown is complicit. The war is a lie to cover up a massive operation in the east.");
        Printer.pause(400);
        Printer.slowPrint("You pack your gear. You check your bowstring. You head east.");
        Printer.slowPrint("It's time to find out what 'Stage Two' really is.");
        
        Printer.printDivider();
        Printer.printBox("ACT II COMPLETE — THE ROAD REMEMBERS");
        if (story.getFlag("sera_knows_stage_two")) {
            Printer.printBox("★ You discovered the reference to 'Stage Two'. You know what you're looking for.");
        }
        Printer.printDivider();
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The assassin's blade finds its mark. Corvin's secrets die with you.");
        System.exit(0);
    }
}
