package story;

import characters.Mira;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class MiraAct1 {

    private Mira mira;
    private StoryManager story;

    public MiraAct1(Mira mira, StoryManager story) {
        this.mira = mira;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneLowerCity();
        sceneGala();
        sceneDiscovery();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT I — ONE MORE JOB  |  Mira Cael");
        Printer.slowPrint("Caldenmere's lower city. The air tastes constantly of soot and copper.");
        Printer.slowPrint("You sit by the bed. Lena is eleven. The Blight-Cough has been eating her lungs for two years.");
        Printer.slowPrint("She breathes in ragged, wet gasps. Every breath sounds like it costs her.");
        Printer.pause(400);
        Printer.slowPrint("Treatment exists. Pure elixirs brewed by the high alchemists in the upper rings.");
        Printer.slowPrint("But the money does not exist. Not for people down here.");
        Printer.slowPrint("Unless tonight's job pays exactly what Davan promised.");
        Printer.pause(400);
        Printer.slowPrint("The Blight-Cough is Greying runoff. Those in power know. They manage it quietly.");
        Printer.slowPrint("You manage it by stealing from them.");
        InputHandler.waitForEnter();
    }

    private void sceneLowerCity() {
        Printer.printDivider();
        Printer.slowPrint("You walk through the damp alleys toward your meeting with Davan, the fence.");
        Printer.slowPrint("Two thugs block your path. They wear the colors of a local gang that Davan sometimes uses.");
        Printer.slowPrint("\"Toll for the road, little bird,\" the larger one grunts.");
        
        System.out.println("  1. Hand over a few coins. It's not worth the time.");
        System.out.println("  2. Intimidate them. Show them your blades.");
        System.out.println("  3. Attack first. You don't have time for this.");
        
        int choice = InputHandler.getInt(1, 3);
        if (choice == 1) {
            Printer.slowPrint("You toss a silver coin. They catch it and step aside, laughing.");
        } else if (choice == 2) {
            Printer.slowPrint("You draw a dagger, spinning it flawlessly across your knuckles.");
            Printer.slowPrint("\"Unless you want to bleed out in the mud, move,\" you whisper.");
            Printer.slowPrint("They back away nervously.");
            story.setFlag("mira_intimidated_thugs", true);
        } else {
            if (!CombatSystem.startCombat(mira, Enemy.ashenHandBandit())) { handleGameOver(); return; }
            Printer.slowPrint("The other thug runs. You wipe your blade and keep walking.");
        }
        
        Printer.pause(400);
        Printer.slowPrint("Davan is waiting in the shadows. He hands you a silk dress and an invitation.");
        Printer.slowPrint("\"Lord Cassian Vael's Autumn Gala. Target is a safe in the private study. Get the contents. Leave the gold.\"");
        InputHandler.waitForEnter();
    }

    private void sceneGala() {
        Printer.printDivider();
        Printer.slowPrint("You enter the grand hall as 'Lady Corren of Ashfield'.");
        Printer.slowPrint("The emerald silk dress hides the lockpicks strapped to your thigh. The painted fan hides your wandering, calculating eyes.");
        Printer.slowPrint("Three hundred guests. Decadent food. The smell of expensive perfumes trying to mask the scent of rot.");
        Printer.pause(400);
        Printer.slowPrint("The target is on the second floor. One safe. One window of noise when the orchestra plays the crescendo.");
        
        System.out.println("  How do you prepare?");
        System.out.println("  1. Sneak upstairs immediately. The faster, the better.");
        System.out.println("  2. Mingle with the nobles to gather information first.");
        System.out.println("  3. Flirt with a guard to learn the patrol routes.");
        int c = InputHandler.getInt(1, 3);
        
        if (c == 2) {
            Printer.slowPrint("You sip champagne and listen. You overhear guards discussing a new 'Arcanist' security system.");
            Printer.slowPrint("\"Cost Lord Vael a fortune. Magical wards,\" one snickers.");
            story.setFlag("mira_knows_arcanist", true);
        } else if (c == 3) {
            Printer.slowPrint("A few smiles, a light touch on the arm, and the guard happily tells you the shift timings.");
            Printer.slowPrint("You know exactly when the corridor will be empty.");
            story.setFlag("mira_knows_patrols", true);
        } else {
            Printer.slowPrint("You slip away from the crowd, relying entirely on your instincts.");
        }
        
        Printer.pause(400);
        Printer.slowPrint("You reach the second floor. You slip into the study. It's opulent. Books bound in rare leather.");
        Printer.slowPrint("The safe is behind a painting of a horrific battle. Typical noble taste.");
        
        System.out.println("  1. Crack the safe immediately. Time is ticking.");
        System.out.println("  2. Search the study desk first for any hidden clues.");
        int c2 = InputHandler.getInt(1, 2);
        
        if (c2 == 2) {
            Printer.slowPrint("In the desk drawer, you find a map of the eastern territory — marked for the kingdom's war with Vrakkas.");
            Printer.slowPrint("But there are strange annotations. 'Conduit Beta'. 'Ignition sequence'. 'Acceptable civilian casualties'.");
            story.setFlag("mira_found_eastern_map", true);
        }
        
        Printer.pause(400);
        Printer.slowPrint("You kneel by the safe and go to work. Three clicks. It opens.");
        Printer.slowPrint("Suddenly, a cold voice speaks from the doorway. A Rogue Arcanist steps in, hands glowing purple.");
        Printer.slowPrint("\"Lady Corren, I presume? Or should I just call you thief?\"");
        
        if (!CombatSystem.startCombat(mira, Enemy.rogueArcanist())) { handleGameOver(); return; }
        mira.heal(20);
        InputHandler.waitForEnter();
    }

    private void sceneDiscovery() {
        Printer.printDivider();
        Printer.slowPrint("The Arcanist falls silent, slumped against the bookshelf.");
        Printer.slowPrint("You turn back to the cracked safe.");
        Printer.slowPrint("It holds more than gold — resting on a velvet pillow is a crystal pulsing with raw, sickening Greying energy.");
        Printer.pause(400);
        Printer.slowPrint("Beneath it, a handwritten note: \"Conduit survey proceeds on schedule. Stage Two requires eastern access. Containment protocols failing in lower city.\"");
        Printer.slowPrint("You realize the horrible truth. This wasn't a standard robbery.");
        Printer.slowPrint("This crystal, this map... the Greying isn't just a natural disaster. It's a weapon.");
        Printer.slowPrint("And they are purposefully venting the toxic magic into the lower city. Poisoning Lena.");
        
        System.out.println("  What do you do with the map?");
        System.out.println("  1. Keep the map. Information is power, and this is explosive.");
        System.out.println("  2. Copy the coordinates and leave the original map to avoid suspicion.");
        
        if (InputHandler.getInt(1, 2) == 2) {
            Printer.slowPrint("You quickly sketch the key coordinates onto a piece of parchment and leave the map behind.");
            story.setFlag("mira_copied_map", true);
        } else {
            Printer.slowPrint("You tuck the map into your bodice. Let Lord Vael panic. He deserves it.");
            story.setFlag("mira_stole_map", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You escape through the window just as the orchestra finishes its crescendo.");
        Printer.slowPrint("You vanish into the foggy night of Caldenmere.");
        Printer.pause(400);
        Printer.slowPrint("You have the crystal. Davan will pay enough gold for Lena's passage out of the city.");
        Printer.slowPrint("But you also have a name: the Architect. And you know about the eastern route.");
        Printer.slowPrint("Running might save Lena today. But until the machine is stopped, no one is truly safe.");
        
        Printer.printDivider();
        Printer.printBox("ACT I COMPLETE — ONE MORE JOB");
        if (story.getFlag("mira_stole_map")) {
            Printer.printBox("★ You took the original map. The Crown will know a spy is among them.");
        }
        Printer.printDivider();
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The gala ends with you bleeding on the expensive carpets. Lena waits in the lower city, but you will never return.");
        System.exit(0);
    }
}
