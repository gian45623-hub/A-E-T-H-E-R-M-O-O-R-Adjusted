package story;

import characters.Sera;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SeraAct3 {

    private Sera sera;
    private StoryManager story;

    public SeraAct3(Sera sera, StoryManager story) {
        this.sera = sera;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneDeepSite();
        sceneConfrontation();
        sceneBossFight();
        sceneChoice();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — THE WOUND IN THE WORLD  |  Sera Caldwell");
        Printer.slowPrint("You step into the deepest part of the scar. The territory is yours by familiarity, not possession.");
        Printer.slowPrint("The air here is thick, humming with a dormant, sickly magic. The Greying.");
        Printer.slowPrint("The trees are twisted, their leaves a pale, silvery ash.");
        Printer.pause(400);
        Printer.slowPrint("This is the wound in the world. And someone is trying to pry it open further.");
        InputHandler.waitForEnter();
    }

    private void sceneDeepSite() {
        Printer.printDivider();
        Printer.slowPrint("You navigate the corrupted woods until you reach the ruins of an ancient temple.");
        Printer.slowPrint("At the center, an artifact pulses — a jagged crystal connected to a massive underground structure.");
        Printer.slowPrint("The Pyre Conduit.");
        Printer.pause(400);
        Printer.slowPrint("You realize what Stage Two is. It's a deliberate reset.");
        Printer.slowPrint("A weapon designed to weaponize the Greying, to wipe the border kingdoms clean in a wave of arcane fire.");
        Printer.slowPrint("The war was just a distraction. A way to clear the civilians out of the blast radius.");
        Printer.pause(400);
        Printer.slowPrint("Guards patrol the perimeter. Ashen Soldiers, their eyes devoid of humanity, fused with their armor.");
        
        System.out.println("  How do you clear the perimeter?");
        System.out.println("  1. [Stealth] Snipe them from the shadows. Pick them off one by one.");
        System.out.println("  2. Trigger an explosion with black powder to distract the main force.");
        System.out.println("  3. Walk right in with your blades drawn.");
        int choice = InputHandler.getInt(1, 3);
        
        if (choice == 1) {
            if (util.Dice.performSkillCheck("Stealth", 15)) {
                Printer.slowPrint("Two fall before they even know they are under attack. But the rest swarm your position.");
            } else {
                Printer.slowPrint("You draw your bow, but a dry twig snaps under your boot.");
                Printer.slowPrint("They turn immediately and open fire.");
                sera.heal(-15);
            }
        } else if (choice == 2) {
            Printer.slowPrint("The explosion rocks the ruins. Most of the guards run toward the fire, leaving only a few.");
            story.setFlag("sera_used_explosives", true);
        } else {
            Printer.slowPrint("You face them head-on. A ranger's fury.");
        }
        
        if (!CombatSystem.startCombat(sera, Enemy.ashenSoldier())) { handleGameOver(); return; }
        sera.heal(20);
        Printer.slowPrint("A massive Brute breaks through the crumbling pillars.");
        if (!CombatSystem.startCombat(sera, Enemy.ashenBrute())) { handleGameOver(); return; }
        sera.heal(30);
        sera.restoreMana(20);
        
        Printer.slowPrint("The perimeter is clear, but the conduit is unstable. It's powering up.");
        InputHandler.waitForEnter();
    }

    private void sceneConfrontation() {
        Printer.printDivider();
        Printer.slowPrint("Heavy footsteps echo on the stone. Captain Thessaly arrives with a full squad of elite Royal Vanguard soldiers.");
        Printer.slowPrint("They aren't here to stop the conduit. They're here to secure it.");
        Printer.pause(400);
        Printer.slowPrint("\"The war bought us time,\" Thessaly says, drawing her gleaming broadsword.");
        Printer.slowPrint("\"You were always meant to open the road for us, Caldwell. You did your job perfectly.\"");
        Printer.slowPrint("\"Corvin wanted peace,\" you say, your voice cold. \"He died for this?\"");
        Printer.slowPrint("\"Peace makes us weak,\" Thessaly replies. \"This weapon will make Thaalisia untouchable. It will make us gods.\"");
        InputHandler.waitForEnter();
    }
    
    private void sceneBossFight() {
        Printer.printDivider();
        Printer.slowPrint("Thessaly signals her men to start the final ignition sequence.");
        Printer.slowPrint("\"Kill the ranger,\" she orders. Then she steps forward herself.");
        Printer.slowPrint("⚔️   SERA CALDWELL  vs  CAPTAIN THESSALY");
        InputHandler.waitForEnter();
        Enemy thessaly = new Enemy("Captain Thessaly", "Captain of the Royal Vanguard. Her armor gleams with deadly precision.", 230, 32, 16, "Vanguard Strike", 3);
        if (!CombatSystem.startCombat(sera, thessaly)) { handleGameOver(); }
    }

    private void sceneChoice() {
        Printer.printDivider();
        Printer.slowPrint("Thessaly falls, her armor pierced. The remaining soldiers flee into the woods.");
        Printer.slowPrint("The conduit hums dangerously, but you smash the control runes with the hilt of your sword.");
        Printer.slowPrint("The machine groans, sparks, and finally powers down. The immediate threat is over.");
        Printer.pause(400);
        Printer.slowPrint("You sit on the ruins, catching your breath. You hold the letters. You hold the truth.");
        Printer.slowPrint("The Crown murdered your friend. They started a war. They built a doomsday weapon.");
        
        System.out.println("  What do you do with the truth?");
        System.out.println("  1. Return to Valdenmere. Present the evidence publicly to the High Council and stop the engineered war.");
        System.out.println("  2. Leak the letters to the eastern settlements and Vrakkas. Let the Crown burn for its sins.");
        System.out.println("  3. Burn the letters, take the Vanguard's gold, and vanish into the wilderness.");
        
        int c = InputHandler.getInt(1, 3);
        Printer.printDivider();
        if (c == 1) {
            Printer.slowPrint("You choose the light. The conspiracy will be dragged into the sun.");
            Printer.slowPrint("It will be dangerous. They will try to silence you. But Corvin's death will not be in vain.");
            Printer.slowPrint("You head west, toward the capital, ready for a different kind of war.");
            story.setFlag("sera_public_testimony", true);
        } else if (c == 2) {
            Printer.slowPrint("The people deserve the truth, but the Crown deserves nothing but ash.");
            Printer.slowPrint("You send the letters to the border kings. When they realize they were manipulated, they will march on Valdenmere.");
            Printer.slowPrint("You walk away, leaving the kingdom to its fate.");
            story.setFlag("sera_lone_road", true);
        } else {
            Printer.slowPrint("It's not your fight anymore. You've done enough.");
            Printer.slowPrint("You toss the letters into the smoldering ruins of the Conduit.");
            Printer.slowPrint("You take Thessaly's heavy purse of gold and disappear into the trees, becoming just another ghost of the eastern road.");
            story.setFlag("sera_took_silence", true);
        }
        
        Printer.pause(500);
        Printer.printDivider();
        Printer.printBox("STORY COMPLETE — SERA CALDWELL");
        Printer.printDivider();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("You fall. The Greying consumes you, and the Conduit ignites, burning the east to ash.");
        System.exit(0);
    }
}
