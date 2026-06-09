package story;

import characters.Mage;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class ErynAct2 {

    private Mage eryn;
    private StoryManager story;

    public ErynAct2(Mage eryn, StoryManager story) {
        this.eryn = eryn;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneWarTornRoads();
        sceneCampfire();
        sceneInfiltration();
        sceneDiscovery();
        sceneEscape();
        sceneActEnd();
    }

    // ─── SCENE 1: The Road to Valdenmere ──────────────────────────────────────
    private void sceneIntro() {
        Printer.printTitle("ACT II — THE CONSPIRACY  |  Eryn Voss");
        Printer.slowPrint("Two days on the road to Valdenmere.");
        Printer.slowPrint("The further you travel from Duskwall, the worse everything looks.");
        Printer.slowPrint("Villages half-empty. Fields grey and dead. Soldiers at every crossroads");
        Printer.slowPrint("who look more frightened than the people they're supposed to protect.");
        Printer.pause(500);
        Printer.slowPrint("The Greying didn't just kill people.");
        Printer.slowPrint("It killed the idea that things would ever go back to normal.");
        Printer.slowPrint("Valdros counted on that. You're certain of it now.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 2: War-Torn Roads ───────────────────────────────────────────────
    private void sceneWarTornRoads() {
        Printer.printDivider();
        Printer.slowPrint("On the second morning, the road splits.");
        Printer.slowPrint("Left: the main highway — faster, but Iron Vow checkpoints every mile.");
        Printer.slowPrint("Right: the forest path — slower, known Ashen territory.");
        System.out.println();
        System.out.println("  Which way do you go?");
        System.out.println("  1. Main highway. You can handle checkpoints.");
        System.out.println("  2. Forest path. Ashen you can fight. Soldiers asking questions are harder.");
        System.out.println("  3. Cut through abandoned Crestfall — make your own route.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("The first checkpoint waves you through.");
                Printer.slowPrint("The second one doesn't. \"Mage registry. Hands where I can see them.\"");
                System.out.println("  1. Bluff — cite a registry number from memory.");
                System.out.println("  2. Run.");
                System.out.println("  3. Bribe the guard.");
                int sub = InputHandler.getInt(1, 3);
                switch (sub) {
                    case 1 -> {
                        Printer.slowPrint("You cite a real format — you memorized hundreds at the Circle.");
                        Printer.slowPrint("The guard writes it down. Waves you through. Your hands stop shaking after a mile.");
                        story.setFlag("eryn_bluffed_checkpoint", true);
                    }
                    case 2 -> {
                        Printer.slowPrint("You bolt. Cut through a field. Lose them in a ditch.");
                        Printer.slowPrint("You arrive at Valdenmere muddy and breathless.");
                    }
                    case 3 -> {
                        Printer.slowPrint("Three copper. He pockets it without eye contact.");
                        Printer.slowPrint("Capitalism survives the apocalypse, apparently.");
                    }
                }
            }
            case 2 -> {
                Printer.slowPrint("The forest is quiet in the wrong way. No birds. No wind.");
                Printer.slowPrint("Ashen move between the trees. You fight through before they swarm.");
                boolean won = CombatSystem.startCombat(eryn, Enemy.ashenSoldier());
                if (!won) { handleGameOver(); return; }
                eryn.restoreMana(15);
                Printer.slowPrint("You emerge from the other side rattled but free.");
            }
            case 3 -> {
                Printer.slowPrint("Crestfall was a market town. Now it's a monument to the Greying.");
                Printer.slowPrint("Every building intact. Every person gone.");
                Printer.slowPrint("At the far edge — fresh wagon tracks. Someone's been here recently.");
                story.setFlag("eryn_found_crestfall_tracks", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 3: The Campfire — Meeting Brennan ────────────────────────────────
    private void sceneCampfire() {
        Printer.printDivider();
        Printer.slowPrint("Night falls before you reach the city gates.");
        Printer.slowPrint("You make camp at a roadside waystation — crumbling stone, old smoke.");
        Printer.slowPrint("Someone else is already there.");
        Printer.slowPrint("A big man. Hood up. Sword within reach but hands visible.");
        Printer.slowPrint("The posture of someone who doesn't want trouble and has had plenty of it.");
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Sit on the opposite side of the fire. Neutral.");
        System.out.println("  2. Ask if you can share the shelter.");
        System.out.println("  3. Keep walking. You don't need company.");

        int choice = InputHandler.getInt(1, 3);

        if (choice == 3) {
            Printer.slowPrint("You walk another hour. Make a worse camp in a ditch. Alone and cold.");
            Printer.slowPrint("You can hear the waystation fire crackling behind you.");
            Printer.slowPrint("You refuse to go back. That's just who you are.");
            InputHandler.waitForEnter();
            return;
        }

        if (choice == 1) {
            Printer.slowPrint("You sit. He doesn't move. A long minute passes.");
            Printer.slowPrint("\"Heading to Valdenmere?\" he asks, without looking up.");
            Printer.slowPrint("\"Aren't you?\" you reply. He almost smiles.");
        } else {
            Printer.slowPrint("He gestures at the fire without speaking. You take that as yes.");
        }

        Printer.pause(400);
        Printer.slowPrint("His name, eventually, is Brennan. He doesn't say what he used to be.");
        Printer.slowPrint("You don't say what you used to be either.");
        Printer.pause(300);
        Printer.slowPrint("\"There's something wrong in Valdenmere,\" he says.");
        Printer.slowPrint("\"More than usual,\" you agree.");
        System.out.println();
        System.out.println("  Do you share what you know?");
        System.out.println("  1. Tell him about the Architect and Valdros.");
        System.out.println("  2. Stay vague. You don't know this man.");
        System.out.println("  3. Ask what HE knows first.");

        int share = InputHandler.getInt(1, 3);
        switch (share) {
            case 1 -> {
                Printer.slowPrint("You tell him most of it. The journal. Sevik. The name Valdros.");
                Printer.slowPrint("He listens without interrupting. That's rarer than it sounds.");
                Printer.slowPrint("\"Someone inside the Iron Vow is funding the Ashen Hand,\" he says quietly.");
                Printer.slowPrint("\"Your Architect and my problem might be connected.\"");
                story.setFlag("eryn_shared_info_with_caden", true);
            }
            case 2 -> {
                Printer.slowPrint("\"Just following a lead,\" you say. He nods. You sit in silence.");
            }
            case 3 -> {
                Printer.slowPrint("He tells you about the Iron Vow. A Lord Marshal named Veyran.");
                Printer.slowPrint("Supply crates in a cult fortress. His old order, rotten at the top.");
                Printer.slowPrint("You listen. Then you tell him about Valdros.");
                Printer.slowPrint("Neither of you sleeps much after that.");
                story.setFlag("eryn_shared_info_with_caden", true);
            }
        }

        Printer.pause(400);
        Printer.slowPrint("In the morning, you both head for Valdenmere.");
        Printer.slowPrint("Not together. Just the same direction.");
        story.setFlag("eryn_met_caden", true);
        InputHandler.waitForEnter();
    }

    // ─── SCENE 4: Infiltrating the Circle HQ ─────────────────────────────────
    private void sceneInfiltration() {
        Printer.printDivider();
        Printer.slowPrint("The old Arcane Circle headquarters — the Spire — was declared condemned after the Greying.");
        Printer.slowPrint("Someone forgot to tell the people living in it.");
        Printer.slowPrint("Lights in the upper windows. Guards at the doors — Valdros's rogue arcanists.");
        System.out.println();
        System.out.println("  How do you get inside?");
        System.out.println("  1. Pose as a recruit. Walk in the front.");
        System.out.println("  2. Find the maintenance coal chute — you know this building.");
        System.out.println("  3. Create a distraction and slip in during the chaos.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You know the Circle's old recruitment phrases. You wrote some of them.");
                Printer.slowPrint("The guard asks for a sigil. You draw one from memory — three years out of date.");
                Printer.slowPrint("He doesn't notice. You're in.");
                story.setFlag("eryn_infiltrated_front", true);
            }
            case 2 -> {
                Printer.slowPrint("The east foundation has a coal chute. You spent enough time here to know.");
                Printer.slowPrint("You squeeze through. Undignified. It works.");
                Printer.slowPrint("You emerge in the basement covered in soot, which actually helps — everyone looks like that.");
            }
            case 3 -> {
                Printer.slowPrint("You light a trash pile across the street with a precise Arcane Bolt.");
                Printer.slowPrint("Guards scramble. You walk through the front door.");
                Printer.slowPrint("Simple. Reckless. You're fine with both.");
            }
        }

        Printer.pause(400);
        Printer.slowPrint("Inside, the grand lecture halls are now harvesting chambers.");
        Printer.slowPrint("Dozens of caged Ashen. Tubes drawing grey energy from them like blood from a wound.");
        Printer.slowPrint("They're farming the Greying. Concentrating it. Storing it for Stage Two.");
        InputHandler.waitForEnter();

        Printer.slowPrint("A rogue arcanist spots you!");
        boolean won = CombatSystem.startCombat(eryn, Enemy.rogueArcanist());
        if (!won) { handleGameOver(); return; }
        eryn.restoreMana(20);
        InputHandler.waitForEnter();
    }

    // ─── SCENE 5: The Discovery ───────────────────────────────────────────────
    private void sceneDiscovery() {
        Printer.printDivider();
        Printer.slowPrint("You find Valdros's personal study on the fourth floor. He's not there.");
        Printer.slowPrint("But everything else is. Plans. Timelines. A map of Aethermoor with red circles.");
        Printer.slowPrint("Every major city. Every population center. Dates — all within thirty days.");
        Printer.pause(600);
        Printer.slowPrint("Stage Two is a coordinated, simultaneous Greying wave across every remaining city.");
        Printer.slowPrint("Not to corrupt. To kill. He wants to wipe the slate and rebuild under his Circle.");
        Printer.pause(400);
        Printer.slowPrint("You think about the child in Duskwall who wanted the fire trick.");

        System.out.println();
        System.out.println("  You have the plans. What do you do?");
        System.out.println("  1. Take everything and run. Get the information out.");
        System.out.println("  2. Destroy the harvesting chambers first — set Stage Two back.");
        System.out.println("  3. Look for Valdros's current location in his notes.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You stuff the plans into your coat and move. Information out first.");
                story.setFlag("eryn_took_plans", true);
            }
            case 2 -> {
                Printer.slowPrint("You find the main conduit. One overloaded circuit. One Arcane Bolt.");
                Printer.slowPrint("The explosion is louder than intended. Ashen are freed. Chaos erupts.");
                Printer.slowPrint("Stage Two loses a month of stored energy.");
                story.setFlag("eryn_destroyed_chambers", true);
                Printer.slowPrint("The entire building is now awake and furious. Move.");
            }
            case 3 -> {
                Printer.slowPrint("His personal log, last entry dated yesterday:");
                Printer.printBox("\"Moving to the Sanctum. Final preparations begin.\"");
                Printer.slowPrint("The Sanctum. The Circle's oldest secret site. You know where it is.");
                story.setFlag("eryn_knows_sanctum_location", true);
            }
        }

        InputHandler.waitForEnter();
        Printer.slowPrint("Valdros's lieutenant cuts off your exit!");
        boolean won = CombatSystem.startCombat(eryn, Enemy.rogueArcanist());
        if (!won) { handleGameOver(); return; }
        eryn.restoreMana(10);
        InputHandler.waitForEnter();
    }

    // ─── SCENE 6: The Escape & The Figure in White ────────────────────────────
    private void sceneEscape() {
        Printer.printDivider();
        Printer.slowPrint("The Spire alarm is sounding. You run through corridors you memorized years ago.");
        Printer.slowPrint("Third floor. Window to the alley. Your old escape from boring lectures.");
        Printer.pause(400);
        Printer.slowPrint("A door opens in front of you. Someone in white robes. Perfectly calm.");
        Printer.slowPrint("They press a folded note into your hand and step aside.");
        Printer.slowPrint("\"The Sanctum is underground. Under the old cathedral. Go alone.\"");
        Printer.pause(300);
        Printer.slowPrint("You turn to ask who they are. The corridor is empty.");
        Printer.pause(500);

        System.out.println("  The note. Read it now or keep running?");
        System.out.println("  1. Read it now — ten seconds won't matter.");
        System.out.println("  2. Keep running. Read it when you're safe.");

        int choice = InputHandler.getInt(1, 2);
        if (choice == 1) {
            Printer.slowPrint("You unfold it mid-sprint.");
        } else {
            Printer.slowPrint("You pocket it. Jump. Read it an hour later, hands still shaking.");
        }
        Printer.printBox("\"He has your old spellbooks. All of them. Still intact.\"");
        Printer.printBox("\"He kept them because he feared what was written inside.\"");
        Printer.slowPrint("He kept your books.");
        story.setFlag("eryn_knows_about_spellbooks", true);

        Printer.pause(400);
        Printer.slowPrint("You make it out. Lose the pursuit in the merchant district.");
        Printer.slowPrint("You sit behind a bakery and breathe.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 7: Act End ─────────────────────────────────────────────────────
    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("Thirty days. Plans that prove everything. A location.");
        Printer.slowPrint("And your spellbooks waiting somewhere in the dark below the city.");
        Printer.pause(500);
        Printer.slowPrint("Three years of exile. Three years of card tricks and cold floors");
        Printer.slowPrint("and being told you imagined all of it.");
        Printer.pause(400);
        Printer.slowPrint("You were never wrong.");
        Printer.printDivider();
        Printer.printBox("ACT II COMPLETE — THE CONSPIRACY");
        Printer.printBox("Eryn Voss descends toward the Sanctum.");
        if (story.getFlag("eryn_destroyed_chambers"))
            Printer.printBox("★ You destroyed the harvesting chambers. Stage Two is delayed.");
        if (story.getFlag("eryn_knows_sanctum_location"))
            Printer.printBox("★ You know exactly where Valdros is hiding.");
        if (story.getFlag("eryn_met_caden"))
            Printer.printBox("★ A knight named Brennan is in this city. Your paths may cross again.");
        Printer.printDivider();
        InputHandler.waitForEnter();
        story.advanceAct();
    }

    private void handleGameOver() {
        Printer.slowPrint("The conspiracy swallows Eryn Voss whole. Aethermoor goes dark.");
        System.exit(0);
    }
}
